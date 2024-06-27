package com.freeder.buclserver.app.products;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.freeder.buclserver.global.response.BaseResponse;
import com.freeder.buclserver.global.util.PageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.product.repository.ProductRepository;
import com.freeder.buclserver.domain.productreview.dto.ReviewDTO;
import com.freeder.buclserver.domain.productreview.dto.ReviewRequestDTO;
import com.freeder.buclserver.domain.productreview.entity.ProductReview;
import com.freeder.buclserver.domain.productreview.repository.ProductReviewRepository;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.user.repository.UserRepository;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.util.ImageParsing;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Service
public class ProductsReviewService {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	private final ProductReviewRepository productReviewRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final S3Client s3Client;
	private final ImageParsing imageParsing;
	private final ProductsCategoryService productsCategoryService;

	public ProductsReviewService(ProductReviewRepository productReviewRepository,
								 UserRepository userRepository,
								 ProductRepository productRepository,
								 S3Client s3Client, ImageParsing imageParsing,
								 ProductsCategoryService productsCategoryService) {
		this.productReviewRepository = productReviewRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.s3Client = s3Client;
		this.imageParsing = imageParsing;
		this.productsCategoryService = productsCategoryService;
	}

	@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
	@Transactional(readOnly = true)
	public ProductReviewResult getProductReviews(Long productCode, int page, int pageSize) {
		try {
			Pageable pageable = PageRequest.of(page, pageSize);
			Page<ProductReview> reviewPage = productReviewRepository.findByProductProductCodeWithConditions(
							productCode, pageable)
					.orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, 404, "해당 리뷰를 찾을 수 없음"));

			long reviewCount = productReviewRepository.countByProductCodeFkWithConditions(productCode);
			float averageRating = productsCategoryService.calculateAverageRating(reviewPage.getContent());

			List<ReviewDTO> reviewDTOs = reviewPage.getContent().stream()
					.map(this::convertToReviewDTO)
					.sorted(Comparator
							.comparingDouble(ReviewDTO::getStarRate)
							.thenComparing(ReviewDTO::getCreatedAt).reversed())
					.collect(Collectors.toList());

			log.info("상품 리뷰 조회 성공 - productCode: {}, page: {}, pageSize: {}", productCode, page, pageSize);
			return new ProductReviewResult(reviewCount, averageRating, reviewDTOs);
		} catch (BaseException e) {
			throw new BaseException(e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage());
		} catch (DataAccessException e) {
			log.error("데이터베이스 조회 실패 - productCode: {}, page: {}, pageSize: {}", productCode, page, pageSize, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "리뷰 조회 - 데이터베이스 에러");
		} catch (Exception e) {
			log.error("상품 리뷰 조회 실패 - productCode: {}, page: {}, pageSize: {}", productCode, page, pageSize, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상품 리뷰 조회 - 서버 에러");
		}
	}

	public BaseResponse<?> getReviewTab(int page, int pageSize) {

		List<ReviewDTO> list =
				productReviewRepository.findAll(PageUtil.paging(page, pageSize)).getContent().stream()
				.map(this::convertToReviewDTO)
				.toList();

		return new BaseResponse<>(list,HttpStatus.OK,"리뷰탭 조회 완료");
	}


	private ReviewDTO convertToReviewDTO(ProductReview review) {
		try {
			List<String> reviewUrls = imageParsing.getReviewUrl(review.getImagePath());
			return new ReviewDTO(
					review.getProductCode(),
					review.getUser().getProfilePath(),
					review.getUser().getNickname(),
					review.getCreatedAt(),
					review.getStarRate().getValue(),
					review.getSelectedOption(),
					reviewUrls,
					review.getContent()
			);
		} catch (Exception e) {
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상품 리뷰 변환 - 서버 에러");
		}
	}



	public static class ProductReviewResult {
		private final long reviewCount;
		private final float averageRating;
		private final List<ReviewDTO> reviews;

		public ProductReviewResult(long reviewCount, float averageRating, List<ReviewDTO> reviews) {
			this.reviewCount = reviewCount;
			this.averageRating = averageRating;
			this.reviews = reviews;
		}

		public List<ReviewDTO> getReviews() {
			return reviews;
		}

		public long getReviewCount() {
			return reviewCount;
		}

		public float getAverageRating() {
			return averageRating;
		}
	}

	@Transactional
	public void createOrUpdateReview(Long productCode, ReviewRequestDTO reviewRequestDTO, Long userId,
									 List<String> s3ImageUrls) {
		try {
			Optional<ProductReview> existingReviewOptional = productReviewRepository.findFirstByUserIdAndProductCode(
					userId,
					productCode
			);

			if (existingReviewOptional.isPresent()) {
				ProductReview existingReview = existingReviewOptional.get();
				existingReview.setContent(reviewRequestDTO.getReviewContent());
				existingReview.setStarRate(reviewRequestDTO.getStarRate());
				existingReview.setUpdatedAt(LocalDateTime.now());
				List<String> prevS3Urls = imageParsing.getImageList(existingReview.getImagePath());

				existingReview.setImagePath(String.join(" ", s3ImageUrls));
				productReviewRepository.save(existingReview);

				deleteImagesToS3(prevS3Urls);

				log.info("리뷰 수정 성공 - productCode: {}, userId: {}, reviewId: {}", productCode, userId,
						existingReview.getId());
			} else {
				User user = userRepository.findById(userId)
						.orElseThrow(() -> {
							log.error("사용자를 찾을 수 없습니다.");
							return new BaseException(HttpStatus.NOT_FOUND, 404, "사용자를 찾을 수 없습니다.");
						});
				Product product = productRepository.findAvailableProductByCode(productCode)
						.orElseThrow(() -> {
							log.error("상품을 찾을 수 없습니다.");
							return new BaseException(HttpStatus.NOT_FOUND, 404, "상품을 찾을 수 없습니다.");
						});
				ProductReview newReview = new ProductReview();
				newReview.setUser(user);
				newReview.setProduct(product);
				newReview.setContent(reviewRequestDTO.getReviewContent());
				newReview.setStarRate(reviewRequestDTO.getStarRate());
				newReview.setCreatedAt(LocalDateTime.now());
				newReview.setImagePath(String.join(" ", s3ImageUrls));
				newReview.setProductCode(productCode);

				productReviewRepository.save(newReview);
				log.info("리뷰 생성 성공 - productCode: {}, userId: {}, reviewId: {}", productCode, userId,
						newReview.getId());
			}
		} catch (BaseException e) {
			throw new BaseException(e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage());
		} catch (DataAccessException e) {
			log.error("데이터베이스 조회 또는 조작 실패 - productCode: {}, userId: {}", productCode, userId, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "리뷰 생성 또는 수정 - 데이터베이스 에러");
		} catch (Exception e) {
			log.error("리뷰 생성 또는 수정 실패 - productCode: {}, userId: {}", productCode, userId, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "리뷰 생성 또는 수정 - 서버 에러");
		}
	}

	@Transactional
	public List<String> deleteReview(Long productCode, Long reviewId, Long userId) {
		try {
			ProductReview reviewToDelete = productReviewRepository.findByIdAndProduct_ProductCodeAndUser_Id(reviewId,
							productCode, userId)
					.orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, 404, "해당 리뷰를 찾을 수 없음"));

			if (!reviewToDelete.getUser().getId().equals(userId)) {
				throw new BaseException(HttpStatus.FORBIDDEN, 403, "해당 리뷰를 삭제할 권한이 없음");
			}

			reviewToDelete.setDeletedAt(LocalDateTime.now());
			List<String> deleteS3Urls = imageParsing.getImageList(reviewToDelete.getImagePath());
			productReviewRepository.save(reviewToDelete);
			log.info("리뷰 삭제 성공 - productCode: {}, userId: {}, reviewId: {}", productCode, userId, reviewId);
			return deleteS3Urls;
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			log.error("리뷰 삭제 실패 - productCode: {}, userId: {}, reviewId: {}", productCode, userId, reviewId, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "리뷰 삭제 - 서버 에러");
		}
	}

	@Transactional
	public void cleanupOldReviews(LocalDateTime date) {
		try {
			List<ProductReview> productReviews = productReviewRepository.findByDeletedAtBefore(date);
			productReviewRepository.deleteAllInBatch(productReviews);
			List<String> s3Urls = productReviews.stream()
					.map(this::convertToS3Url).toList().stream()
					.flatMap(List::stream)
					.toList();
			deleteImagesToS3(s3Urls);

			log.info("3개월 이상된 리뷰 데이터 삭제 완료");
		} catch (Exception e) {
			log.error("리뷰 데이터 삭제 중 오류 발생", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "리뷰 데이터 삭제 - 서버 에러");
		}
	}

	@Transactional
	public void uploadImagesToS3(List<MultipartFile> images, List<String> s3ImageUrls) {
		try {
			for (int i = 0; i < images.size(); i++) {
				uploadImageToS3(images.get(i), s3ImageUrls.get(i));
			}
		} catch (IOException e) {
			log.error("이미지 업로드 실패", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "이미지 업로드 - 서버 에러");
		} catch (BaseException e) {
			throw new BaseException(e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage());
		} catch (Exception e) {
			log.error("서버 오류로 인한 리뷰 생성 및 수정 실패", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 에러");
		}

	}

	private void uploadImageToS3(MultipartFile image, String s3ImageUrl) throws IOException {
		try {
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
					.bucket(bucket)
					.key(s3ImageUrl)
					.build();

			s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(image.getInputStream(), image.getSize()));
		} catch (SdkException | IOException e) {
			log.error("S3에 이미지 업로드 실패", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "S3 이미지 업로드 - 서버 에러");
		} catch (Exception e) {
			log.error("서버 오류로 인한 리뷰 생성 및 수정 실패", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 에러");
		}
	}

	private List<String> convertToS3Url(ProductReview review) {
		return imageParsing.getImageList(review.getImagePath());
	}

	@Transactional
	public void deleteImagesToS3(List<String> s3ImageUrls) {

		try {
			for (String s3ImageUrl : s3ImageUrls) {
				deleteImageToS3(s3ImageUrl);
			}
		} catch (BaseException e) {
			throw new BaseException(e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage());
		} catch (Exception e) {
			log.error("서버 오류로 인한 리뷰 생성 및 수정 실패", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 에러");
		}

	}

	private void deleteImageToS3(String s3ImageUrl) {
		try {
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
					.bucket(bucket)
					.key(s3ImageUrl)
					.build();

			s3Client.deleteObject(deleteObjectRequest);
		} catch (SdkException e) {
			log.error("S3에 이미지 업로드 실패", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "S3 이미지 업로드 - 서버 에러");
		} catch (Exception e) {
			log.error("서버 오류로 인한 리뷰 생성 및 수정 실패", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 에러");
		}
	}

}
