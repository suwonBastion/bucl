package com.freeder.buclserver.app.products;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freeder.buclserver.domain.productreview.dto.ReviewPhotoDTO;
import com.freeder.buclserver.domain.productreview.entity.ProductReview;
import com.freeder.buclserver.domain.productreview.repository.ProductReviewRepository;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.util.ImageParsing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductsReviewPhotoService {

	private final ProductReviewRepository productReviewRepository;
	private final ImageParsing imageParsing;

	public ProductsReviewPhotoService(ProductReviewRepository productReviewRepository, ImageParsing imageParsing) {
		this.productReviewRepository = productReviewRepository;
		this.imageParsing = imageParsing;
	}

	@Transactional(readOnly = true)
	public List<ReviewPhotoDTO> getProductReviewPhotos(Long productCode, int page, int pageSize) {
		try {
			Pageable pageable = PageRequest.of(page, pageSize);
			Page<ProductReview> reviewPage = productReviewRepository.findByProductProductCodeWithConditions(productCode,
							pageable)
					.orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, 404, "해당 리뷰사진을 찾을 수 없음"));

			List<List<ReviewPhotoDTO>> reviewPhotosList = reviewPage.getContent().stream()
					.map(this::convertToPhotoDTO)
					.toList();

			log.info("상품 리뷰 사진 조회 성공 - productCode: {}, page: {}, pageSize: {}", productCode, page, pageSize);

			return reviewPhotosList.stream()
					.flatMap(List::stream)
					.collect(Collectors.toList());
		} catch (BaseException e) {
			log.error("리뷰 사진 조회 실패 - productCode: {}, page: {}, pageSize: {}", productCode, page, pageSize, e);
			throw new BaseException(e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage());
		} catch (NullPointerException e) {
			log.error("Null Point Access 에러 발생", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Null Point Access 에러 발생");
		} catch (IllegalArgumentException e) {
			log.error("IllegalArgumentException", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "IllegalArgumentException 에러 발생");
		} catch (DataAccessException e) {
			log.error("데이터베이스 조회 실패 - productCode: {}, page: {}, pageSize: {}", productCode, page, pageSize, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "리뷰 사진 조회 - 데이터베이스 에러");
		} catch (Exception e) {
			log.error("상품 리뷰 사진 조회 실패 - productCode: {}, page: {}, pageSize: {}", productCode, page, pageSize, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상품 리뷰 사진 조회 - 서버 에러");
		}
	}

	private List<ReviewPhotoDTO> convertToPhotoDTO(ProductReview review) {
		try {
			List<String> imageList = imageParsing.getImageList(review.getImagePath());
			List<ReviewPhotoDTO> result = new ArrayList<>();

			for (String imagePath : imageList) {
				log.info("상품 리뷰 사진 DTO 변환 성공 - reviewId: {}", review.getId());
				result.add(new ReviewPhotoDTO(imagePath));
			}

			return result;
		} catch (Exception e) {
			log.error("상품 리뷰 사진 DTO 변환 실패 - reviewId: {}", review.getId(), e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상품 리뷰 사진 DTO 변환 - 서버 에러");
		}
	}
}