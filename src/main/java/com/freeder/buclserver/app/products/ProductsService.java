package com.freeder.buclserver.app.products;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.freeder.buclserver.domain.productai.entity.ProductAi;
import com.freeder.buclserver.domain.productai.repository.ProductAiRepository;
import com.freeder.buclserver.domain.productcomment.repository.ProductCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.freeder.buclserver.domain.product.dto.ProductDTO;
import com.freeder.buclserver.domain.product.dto.ProductDetailDTO;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.product.repository.ProductRepository;
import com.freeder.buclserver.domain.productoption.dto.ProductOptionDTO;
import com.freeder.buclserver.domain.productoption.entity.ProductOption;
import com.freeder.buclserver.domain.productoption.repository.ProductOptionRepository;
import com.freeder.buclserver.domain.productreview.dto.ReviewPreviewDTO;
import com.freeder.buclserver.domain.productreview.entity.ProductReview;
import com.freeder.buclserver.domain.wish.repository.WishRepository;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.util.ImageParsing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsCategoryService productsCategoryService;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final WishRepository wishRepository;
    private final ImageParsing imageParsing;
    private final ProductCommentRepository productCommentRepository;
    private final ProductAiRepository productAiRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> getProducts(Long categoryId, int page, int pageSize, Long userId) {
        try {
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            Page<Product> productsPage = productRepository.findProductsByConditions(categoryId, pageable)
                    .orElseThrow(() -> {
                        log.error("해당 상품을 찾을 수 없음");
                        return new BaseException(HttpStatus.NOT_FOUND, 404, "해당 상품을 찾을 수 없음");
                    });

            List<ProductDTO> products = productsPage.getContent().stream()
                    .map(product -> convertToDTO(product, userId))
                    .collect(Collectors.toList());


            log.info("상품 목록 조회 성공 - categoryId: {}, page: {}, pageSize: {}", categoryId, page - 1, pageSize);
            return products;
        } catch (BaseException e) {
            throw new BaseException(e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage());
        } catch (NullPointerException e) {
            log.error("Null Point Access 에러 발생", e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Null Point Access 에러 발생");
        } catch (DataAccessException e) {
            log.error("데이터 액세스 오류 발생", e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "데이터 액세스 오류");
        } catch (Exception e) {
            log.error("상품 목록 조회 실패 - categoryId: {}, page: {}, pageSize: {}", categoryId, page, pageSize, e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상품 목록 조회 - 서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public ProductDetailDTO getProductDetail(Long productCode, Long userId) {
        try {
            Product product = productRepository.findAvailableProductByCode(productCode)
                    .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, 404, "해당 상품을 찾을 수 없음"));

            List<ProductReview> reviews = product.getReviews().stream()
                    .limit(3)
                    .collect(Collectors.toList());

            float averageRating = productsCategoryService.calculateAverageRating(reviews);
            int reviewCount = reviews.size();

            List<ReviewPreviewDTO> reviewPreviews = reviews.stream()
                    .map(this::convertToReviewPreviewDTO)
                    .collect(Collectors.toList());

            List<String> imageUrls = imageParsing.getImageList(product.getImagePath());
            List<String> firstFiveImages = imageUrls.stream().limit(5).collect(Collectors.toList());
            List<String> detailImages = imageParsing.getImageList(product.getDetailImagePath());
            boolean wished = false;

            if (userId != null) {
                wished = wishRepository.existsByUser_IdAndProduct_IdAndDeletedAtIsNull(userId, product.getId());
            }

            Optional<ProductAi> byProductId = productAiRepository.findByProductId(product.getId());

            log.info("상품 상세 정보 조회 성공 - productCode: {}", productCode);
            return new ProductDetailDTO(
                    product.getProductCode(),
                    product.getName(),
                    product.getBrandName(),
                    product.getSalePrice(),
                    product.getConsumerPrice(),
                    product.getDiscountRate(),
                    averageRating,
                    product.getCreatedAt(),
                    reviewCount,
                    firstFiveImages,
                    detailImages,
                    reviewPreviews,
                    wished,
                    ProductDetailDTO.convertDto(byProductId)
            );
        } catch (BaseException e) {
            throw new BaseException(e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage());
        } catch (NullPointerException e) {
            log.error("Null Point Access 에러 발생", e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Null Point Access 에러 발생");
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException", e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "IllegalArgumentException 에러 발생");
        } catch (DataAccessException e) {
            log.error("데이터 액세스 오류 발생", e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "데이터 액세스 오류");
        } catch (Exception e) {
            log.error("상품 상세 정보 조회 실패 - productCode: {}", productCode, e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상품 상세 정보 조회 - 서버 에러");
        }
    }

    public ReviewPreviewDTO convertToReviewPreviewDTO(ProductReview review) {
        try {
            String thumbnailUrl = imageParsing.getThumbnailUrl(review.getImagePath());
            return new ReviewPreviewDTO(
                    review.getUser().getProfilePath(),
                    review.getUser().getNickname(),
                    review.getCreatedAt(),
                    review.getProduct().getName(),
                    review.getContent(),
                    review.getStarRate().getValue(),
                    thumbnailUrl
            );
        } catch (Exception e) {
            log.error("상품 리뷰 미리보기 변환 실패", e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상품 리뷰 미리보기 변환 - 서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public List<ProductOptionDTO> getProductOptions(Long productCode) {
        try {
            List<ProductOption> productOptions = productOptionRepository.findByProductProductCodeWithConditions(
                            productCode)
                    .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, 404, "해당 옵션을 찾을 수 없음"));

            log.info("상품 옵션 조회 성공 - productCode: {}", productCode);
            return productOptions.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (BaseException e) {
            throw new BaseException(e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage());
        } catch (EmptyResultDataAccessException e) {
            log.error("상품 옵션 조회 실패 - productCode: {}", productCode, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "상품 옵션을 찾을 수 없습니다.");
        } catch (Exception e) {
            // 그 외 예외 처리
            log.error("상품 옵션 조회 실패 - productCode: {}", productCode, e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상품 옵션 조회 - 서버 에러");
        }
    }

    public ProductOptionDTO convertToDTO(ProductOption productOption) {
        try {
            return new ProductOptionDTO(productOption.getOptionValue(), productOption.getOptionExtraAmount());
        } catch (Exception e) {
            log.error("상품 옵션 DTO 변환 실패", e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상품 옵션 DTO 변환 실패");
        }
    }

    private ProductDTO convertToDTO(Product product, Long userId) {
        try {
            String thumbnailUrl = imageParsing.getThumbnailUrl(product.getImagePath());
            float calculatedReward = (product.getSalePrice() * product.getConsumerRewardRate());
            float roundedReward = Math.round(calculatedReward);

            boolean wished = false;

            if (userId != null) {
                wished = wishRepository.existsByUser_IdAndProduct_IdAndDeletedAtIsNull(userId, product.getId());
            }

            Object[] counts = productCommentRepository.getCounts(product.getId()).get(0);

            return new ProductDTO(
                    product.getId(),
                    product.getProductCode(),
                    product.getName(),
                    product.getBrandName(),
                    thumbnailUrl,
                    product.getSalePrice(),
                    product.getConsumerPrice(),
                    roundedReward,
                    wished,
                    counts[0],
                    counts[1]
            );
        } catch (IllegalArgumentException e) {
            log.error("DTO 변환 중 잘못된 인자가 전달되었습니다.", e);
            throw new BaseException(HttpStatus.BAD_REQUEST, 400, "DTO 변환 중 잘못된 인자가 전달되었습니다.");
        } catch (Exception e) {
            log.error("상품 정보 DTO 변환 실패", e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상품 정보 DTO 변환 - 서버 에러");
        }
    }
}
