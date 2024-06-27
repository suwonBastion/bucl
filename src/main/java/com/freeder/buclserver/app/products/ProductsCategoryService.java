package com.freeder.buclserver.app.products;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.productcategory.dto.ProductCategoryDTO;
import com.freeder.buclserver.domain.productcategory.repository.ProductCategoryRepository;
import com.freeder.buclserver.domain.productreview.entity.ProductReview;
import com.freeder.buclserver.domain.wish.repository.WishRepository;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.util.ImageParsing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductsCategoryService {
	private final ProductCategoryRepository productCategoryRepository;
	private final WishRepository wishRepository;
	private final ImageParsing imageParsing;

	public ProductsCategoryService(ProductCategoryRepository productCategoryRepository, WishRepository wishRepository,
								   ImageParsing imageParsing) {
		this.productCategoryRepository = productCategoryRepository;
		this.wishRepository = wishRepository;
		this.imageParsing = imageParsing;
	}

	@Transactional(readOnly = true)
	public List<ProductCategoryDTO> getCategoryProducts(Long categoryId, int page, int pageSize, Long userId) {
		try {
			Pageable pageable = PageRequest.of(page, pageSize);
			Page<Product> categoryProductsPage = productCategoryRepository.findProductsByCategory(categoryId, pageable)
					.orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, 404, "해당 카테고리를 찾을 수 없음"));

			if (categoryProductsPage == null) {
				log.error("카테고리 목록이 존재하지 않습니다.");
				throw new BaseException(HttpStatus.NOT_FOUND, 404, "카테고리 목록이 존재하지 않습니다.");
			}
			List<ProductCategoryDTO> categoryProducts = categoryProductsPage.getContent().stream()
					.map(product -> convertToCategoryDTO(product, userId))
					.collect(Collectors.toList());

			log.info("카테고리 제품 조회 성공 - categoryId: {}, page: {}, pageSize: {}", categoryId, page, pageSize);
			return categoryProducts;
		} catch (BaseException e) {
			throw new BaseException(e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage());
		} catch (NullPointerException e) {
			log.error("Null Point Access 에러 발생", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Null Point Access 에러 발생");
		} catch (IllegalArgumentException e) {
			log.error("IllegalArgumentException", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "IllegalArgumentException 에러 발생");
		} catch (Exception e) {
			log.error("카테고리 제품 조회 실패 - categoryId: {}, page: {}, pageSize: {}", categoryId, page, pageSize, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "카테고리 제품 조회 - 서버 에러");
		}
	}

	public ProductCategoryDTO convertToCategoryDTO(Product product, Long userId) {
		try {
			List<ProductReview> reviews = product.getReviews();
			int reviewCount = reviews.size();
			float averageRating = calculateAverageRating(reviews);
			String thumbnailUrl = imageParsing.getThumbnailUrl(product.getImagePath());
			averageRating = Math.round(averageRating * 10.0f) / 10.0f;

			boolean wished = false;

			if (userId != null) {
				wished = wishRepository.existsByUser_IdAndProduct_IdAndDeletedAtIsNull(userId, product.getId());
			}
			return new ProductCategoryDTO(
					product.getProductCode(),
					product.getName(),
					thumbnailUrl,
					product.getSalePrice(),
					product.getConsumerPrice(),
					Math.round(product.getConsumerPrice() * product.getConsumerRewardRate()),
					product.getDiscountRate(),
					reviewCount,
					averageRating,
					wished
			);
		} catch (Exception e) {
			log.error("카테고리 DTO 변환 실패", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "카테고리 DTO 변환 실패");
		}
	}

	public float calculateAverageRating(List<ProductReview> reviews) {
		try {
			if (reviews.isEmpty()) {
				return 0.0f;
			}

			float totalRating = 0.0f;
			for (ProductReview review : reviews) {
				totalRating += review.getStarRate().getValue();
			}

			if (reviews.size() == 0) {
				throw new ArithmeticException("별점 평균을 계산할 리뷰가 없습니다.");
			}

			float averageRating = totalRating / reviews.size();

			return Math.round(averageRating * 10.0f) / 10.0f;
		} catch (ArithmeticException e) {
			log.error("별점 평균 계산 중 산술 연산 오류 발생", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "별점 평균 계산 - 산술 연산 오류");
		} catch (Exception e) {
			log.error("별점 평균 계산 실패", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "별점 평균 계산 - 서버 에러");
		}
	}
}
