package com.freeder.buclserver.app.products;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.freeder.buclserver.domain.product.dto.ProductDTO;
import com.freeder.buclserver.domain.product.dto.ProductDetailDTO;
import com.freeder.buclserver.domain.productoption.dto.ProductOptionDTO;
import com.freeder.buclserver.domain.productreview.dto.ReviewPhotoDTO;
import com.freeder.buclserver.domain.productreview.dto.ReviewRequestDTO;
import com.freeder.buclserver.global.response.BaseResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "products 관련 API", description = "상품 관련 API")
public class ProductsController {

	private final ProductsService productsService;
	private final ProductsReviewService productsReviewService;
	private final ProductsReviewPhotoService productsReviewPhotoService;

	@Autowired
	public ProductsController(
			ProductsService productsService,
			ProductsReviewService productsReviewService,
			ProductsReviewPhotoService productsReviewPhotoService
	) {
		this.productsService = productsService;
		this.productsReviewService = productsReviewService;
		this.productsReviewPhotoService = productsReviewPhotoService;
	}

	@GetMapping
	@Transactional(readOnly = true)
	public BaseResponse<List<ProductDTO>> getProducts(
			@RequestParam(defaultValue = "1") Long categoryId,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int pageSize
	) {
		Long userId = 1L;
		List<ProductDTO> products = productsService.getProducts(categoryId, page, pageSize, userId);
		return new BaseResponse<>(products, HttpStatus.OK, "요청 성공");
	}

	@GetMapping("/{product_code}")
	@Transactional(readOnly = true)
	public BaseResponse<ProductDetailDTO> getProductDetail(
			@PathVariable(name = "product_code", required = true) Long productCode
	) {
		Long userId = 1L;
		ProductDetailDTO productDetail = productsService.getProductDetail(productCode, userId);
		return new BaseResponse<>(productDetail, HttpStatus.OK, "요청 성공");
	}

	@GetMapping("/{product_code}/options")
	@Transactional(readOnly = true)
	public BaseResponse<List<ProductOptionDTO>> getProductOptions(
			@PathVariable(name = "product_code", required = true) Long productCode
	) {
		List<ProductOptionDTO> productOptions = productsService.getProductOptions(productCode);
		return new BaseResponse<>(productOptions, HttpStatus.OK, "옵션 요청 성공");
	}

	@GetMapping("/reviewstab")
	@Transactional(readOnly = true)
	public BaseResponse<?> getReviewTab(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int pageSize
	){
		return productsReviewService.getReviewTab(page,pageSize);
	}

	@GetMapping("/{product_code}/reviews")
	@Transactional(readOnly = true)
	public BaseResponse<ProductsReviewService.ProductReviewResult> getProductReviews(
			@PathVariable(name = "product_code", required = true) Long productCode,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int pageSize
	) {

		ProductsReviewService.ProductReviewResult result = productsReviewService.getProductReviews(productCode, page,
				pageSize);
		return new BaseResponse<>(result, HttpStatus.OK, "리뷰 조회 성공");
	}

	@GetMapping("/{product_code}/photo-reviews")
	@Transactional(readOnly = true)
	public BaseResponse<List<ReviewPhotoDTO>> getReviewPhotos(
			@PathVariable(name = "product_code", required = true) Long productCode,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int pageSize,
			@RequestParam(defaultValue = "preview") String display
	) {
		int adjustedPageSize = "fullview".equals(display) ? 20 : pageSize;
		int startItemIndex = page * adjustedPageSize;

		List<ReviewPhotoDTO> reviewPhotos = productsReviewPhotoService.getProductReviewPhotos(productCode,
				startItemIndex,
				adjustedPageSize);

		log.info("리뷰 사진 조회 성공 - productCode: {}, page: {}, pageSize: {}", productCode, page, pageSize);
		return new BaseResponse<>(reviewPhotos, HttpStatus.OK, "리뷰 사진 조회 성공");
	}

	@PostMapping("/{product_code}/review")
	@Transactional
	public BaseResponse<String> createOrUpdateReview(
			@PathVariable(name = "product_code", required = true) Long productCode,
			@RequestPart("reviewRequest") ReviewRequestDTO reviewRequestDTO,
			@RequestPart("images") List<MultipartFile> images
	) {
		Long userId = 1L;
		List<String> s3ImageUrls = new ArrayList<>();

		for (int i = 0; i < images.size(); i++) {
			s3ImageUrls.add("assets/images/reviews/" + UUID.randomUUID() + ".png");
		}

		productsReviewService.createOrUpdateReview(productCode, reviewRequestDTO, userId, s3ImageUrls);
		productsReviewService.uploadImagesToS3(images, s3ImageUrls);

		return new BaseResponse<>("리뷰 생성 또는 수정 성공", HttpStatus.OK, "요청 성공");
	}

	@DeleteMapping("/{product_code}/review")
	@Transactional
	public BaseResponse<String> deleteReview(
			@PathVariable(name = "product_code", required = true) Long productCode,
			@RequestParam Long reviewId
	) {
		Long userId = 1L;
		List<String> deleteS3Urls = productsReviewService.deleteReview(productCode, reviewId, userId);
		productsReviewService.deleteImagesToS3(deleteS3Urls);

		return new BaseResponse<>("리뷰 삭제 성공", HttpStatus.OK, "요청 성공");
	}
}
