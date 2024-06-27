package com.freeder.buclserver.admin.상품.service;

import com.freeder.buclserver.admin.상품.dto.상품등록및수정Dto;
import com.freeder.buclserver.admin.상품.dto.상품조회및수정시작화면Dto;
import com.freeder.buclserver.app.products.ProductsReviewService;
import com.freeder.buclserver.app.products.ProductsService;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.product.dto.ProductDTO;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.product.repository.ProductRepository;
import com.freeder.buclserver.domain.product.vo.ProductStatus;
import com.freeder.buclserver.domain.productai.entity.ProductAi;
import com.freeder.buclserver.domain.productai.repository.ProductAiRepository;
import com.freeder.buclserver.domain.productcategory.entity.ProductCategory;
import com.freeder.buclserver.domain.productcomment.entity.ProductComment;
import com.freeder.buclserver.domain.productoption.entity.ProductOption;
import com.freeder.buclserver.domain.productoption.repository.ProductOptionRepository;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import com.freeder.buclserver.global.util.ImageParsing;

import com.freeder.buclserver.global.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.freeder.buclserver.admin.상품.dto.상품등록및수정Dto.*;
import static com.freeder.buclserver.domain.product.dto.ProductDetailDTO.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class 상품Service {
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductAiRepository productAiRepository;
    private final ProductsReviewService productsReviewService;
    private final ImageParsing imageParsing;

    @Transactional(readOnly = true)
    public BaseResponse<?> 상품조회및수정시작화면(CustomUserDetails userDetails, int page, int pageSize) {
        권한검증(userDetails);

        Page<Product> all = productRepository.findAll(PageUtil.paging(page, pageSize));
        List<상품조회및수정시작화면Dto> list = all.getContent().stream()
                .map(this::Dto변환)
                .toList();

        return new BaseResponse<>(list, HttpStatus.OK, "상품조회및수정시작화면조회성공");
    }


    @Transactional
    public BaseResponse<?> 상품등록(CustomUserDetails userDetails, 상품등록및수정Dto productdata, List<MultipartFile> imagepath, List<MultipartFile> detailimage, List<String> imagepathlist, List<String> detailimagelist) {
        권한검증(userDetails);

        Product 새로운상품 = productRepository.save(상품엔티티로변환(productdata, imagepathlist, detailimagelist));
        List<상품옵션Dto> 상품옵션들 = productdata.productOptions();
        List<ProductOption> list = 상품옵션들.stream().map(productOption -> 상품옵션엔티티로변환(productOption, 새로운상품)).toList();
        ProductAiDto productAiDto = productdata.productAiData();

        productOptionRepository.saveAll(list);
        productAiRepository.save(상품AI엔티티로변환(productAiDto, 새로운상품));

        //S3에 이미지저장
        if (!Objects.requireNonNull(imagepath.get(0).getOriginalFilename()).equals("")) {
//        productsReviewService.uploadImagesToS3(imagepath,imagepathlist);
        }

        if (!Objects.requireNonNull(detailimage.get(0).getOriginalFilename()).equals("")) {
//        productsReviewService.uploadImagesToS3(detailimage,detailimagelist);
        }

        return new BaseResponse<>(null, HttpStatus.OK, "새상품등록완료");
    }

    @Transactional(readOnly = true)
    public BaseResponse<?> 상품수정화면(Long productId, CustomUserDetails userDetails) {
        권한검증(userDetails);

        Product 검색한상품 = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, 400, "없는상품ID"));

        List<ProductOption> 검색한옵션들 = 검색한상품.getProductOptions();

        ProductAi 검색한AI리뷰 = productAiRepository.findByProductId(productId)
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, 400, "없는AI리뷰"));

        상품등록및수정Dto 상품등록및수정Dto = 상품수정Dto로변환(검색한상품, 검색한옵션들, 검색한AI리뷰);

        return new BaseResponse<>(상품등록및수정Dto, HttpStatus.OK, "상품수정화면조회완료");
    }

    @Transactional
    public BaseResponse<?> 상품수정(CustomUserDetails userDetails, 상품등록및수정Dto productdata, List<MultipartFile> imagepath, List<MultipartFile> detailimage, List<String> imagepathlist, List<String> detailimagelist) {
        권한검증(userDetails);
        상품ID체크(productdata);

        Product 수정할상품 = productRepository.findById(productdata.productId())
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된상품ID"));

        상품수정(수정할상품, productdata, imagepathlist, detailimagelist);

        //S3에 이미지저장
        if (!Objects.requireNonNull(imagepath.get(0).getOriginalFilename()).equals("")) {
            /*List<String> 이미지주소들 = imageParsing.getImageList(수정할상품.getImagePath());
            productsReviewService.deleteImagesToS3(이미지주소들);
            productsReviewService.uploadImagesToS3(imagepath, imagepathlist);*/
        }

        if (!Objects.requireNonNull(detailimage.get(0).getOriginalFilename()).equals("")) {
            /*List<String> 이미지주소들 = imageParsing.getImageList(수정할상품.getDetailImagePath());
            productsReviewService.deleteImagesToS3(이미지주소들);
            productsReviewService.uploadImagesToS3(detailimage, detailimagelist);*/
        }

        return new BaseResponse<>("null", HttpStatus.OK, "상품수정완료");
    }

    @Transactional
    public BaseResponse<?> 상품삭제(Long productId, CustomUserDetails userDetails) {
        권한검증(userDetails);

        Product 삭제할상품 = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된상품ID"));

        삭제할상품.setDeletedAt(LocalDateTime.now());
        삭제할상품.setProductStatus(ProductStatus.DISCONTINUED);
        삭제할상품.setIsExposed(false);

        return new BaseResponse<>(null, HttpStatus.OK, "상품삭제완료");
    }

    @Transactional(readOnly = true)
    public BaseResponse<?> 삭제된상품들(CustomUserDetails userDetails, int page, int pageSize) {
        권한검증(userDetails);

        Page<Product> 삭제된상품들 = productRepository.findByDeletedAtIsNotNull(PageUtil.paging(page, pageSize))
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, 400, "삭제된상품이 없거나 잘못된ID"));

        List<ProductDTO> 삭제된상품들Dto =
                삭제된상품들.getContent().stream()
                        .map(this::상품DTO로변환)
                        .toList();

        return new BaseResponse<>(삭제된상품들Dto,HttpStatus.OK,"삭제된상품들조회성공");
    }

    @Transactional
    public BaseResponse<?> 삭제된상품복구(Long productId, CustomUserDetails userDetails) {
        권한검증(userDetails);

        Product 복구할상품 = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된상품ID"));

        복구할상품.setDeletedAt(null);
        복구할상품.setProductStatus(ProductStatus.ACTIVE);

        return new BaseResponse<>(null,HttpStatus.OK,"삭제된상품복구완료");
    }


    ///////////////////////////////private/////////////////////////////

    private 상품조회및수정시작화면Dto Dto변환(Product product) {
        List<ProductComment> productComments = product.getProductComments();
        int count = (int) productComments.stream().filter(ProductComment::isSuggestion).count();
        return new 상품조회및수정시작화면Dto(
                product.getId(),
                product.getName(),
                productComments.size(),
                count,
                productComments.size() - count
        );
    }

    private Product 상품엔티티로변환(상품등록및수정Dto productdata, List<String> imagepathlist, List<String> detailimagelist) {
        return Product.builder()
                .productCategory(ProductCategory.builder().id(productdata.categoryId()).build())
                .name(productdata.name())
                .productCode(productdata.productCode())
                .brandName(productdata.brandName())
                .manufacturerName(productdata.manufacturerName())
                .supplierName(productdata.supplierName())
                .supplyPrice(productdata.supplyPrice())
                .consumerPrice(productdata.consumerPrice())
                .salePrice(productdata.salePrice())
                .taxStatus(productdata.taxStatus())
                .marginRate(productdata.marginRate())
                .taxRate(productdata.taxRate())
                .discountRate(productdata.discountRate())
                .consumerRewardRate(productdata.consumerRewardRate())
                .businessRewardRate(productdata.businessRewardRate())
                .imagePath(imagepathlist.isEmpty() ? null : String.join(" ", imagepathlist))
                .detailImagePath(detailimagelist.isEmpty() ? null : String.join(" ", detailimagelist))
                .productStatus(productdata.productStatus())
                .productPriority(productdata.productPriority())
                .isExposed(productdata.isExposed())
                .isAvailableMultipleOption(productdata.isAvailableMultipleOption())
                .saleAlternatives(productdata.saleAlternatives())
                .commentReward(productdata.commentReward())
                .deadline(productdata.deadline())
                .otherLowestPrice(productdata.otherLowestPrice())
                .build();
    }

    private ProductOption 상품옵션엔티티로변환(상품옵션Dto productOption, Product product) {
        return ProductOption.builder()
                .product(product)
                .skuCode(productOption.skuCode())
                .optionKey(productOption.optionKey())
                .optionValue(productOption.optionValue())
                .optionSequence(productOption.optionSequence())
                .productQty(productOption.productQty())
                .maxOrderQty(productOption.maxOrderQty())
                .minOrderQty(productOption.minOrderQty())
                .optionExtraAmount(productOption.optionExtraAmount())
                .isExposed(productOption.isExposed())
                .build();
    }

    private ProductAi 상품AI엔티티로변환(ProductAiDto productAiDto, Product product) {
        return ProductAi.builder()
                .product(product)
                .average(productAiDto.getAverage())
                .mdComment(productAiDto.getMdComment())
                .summary(productAiDto.getSummary())
                .totalCnt(productAiDto.getTotalCnt())
                .build();
    }

    private 상품등록및수정Dto 상품수정Dto로변환(Product product, List<ProductOption> productOptions, ProductAi productAi) {

        List<상품옵션Dto> 변환된상품옵션리스트 = productOptions.stream().map(상품등록및수정Dto::상품옵션Dto로변환).toList();

        return new 상품등록및수정Dto(
                product.getProductCategory().getId(),
                product.getId(),
                product.getName(),
                product.getProductCode(),
                product.getBrandName(),
                product.getManufacturerName(),
                product.getSupplierName(),
                product.getSupplyPrice(),
                product.getConsumerPrice(),
                product.getSalePrice(),
                product.getTaxStatus(),
                product.getMarginRate(),
                product.getTaxRate(),
                product.getDiscountRate(),
                product.getConsumerRewardRate(),
                product.getBusinessRewardRate(),
                product.getProductStatus(),
                product.getProductPriority(),
                product.isExposed(),
                product.isAvailableMultipleOption(),
                product.getSaleAlternatives(),
                product.getCommentReward(),
                product.getDeadline(),
                product.getOtherLowestPrice(),
                변환된상품옵션리스트,
                상품등록및수정Dto.ProductAiDto로변환(productAi)
        );

    }

    private void 권한검증(CustomUserDetails userDetails) {
        if (!userDetails.getRole().equals(Role.ROLE_ADMIN.toString())) {
            throw new BaseException(HttpStatus.FORBIDDEN, 403, "권한이 없습니다.");
        }
    }

    private void 상품ID체크(상품등록및수정Dto productdata) {
        if (productdata.productId() == null) {
            throw new BaseException(HttpStatus.BAD_REQUEST, 400, "상품ID가 없습니다");
        }
    }

    private void 상품수정(Product product, 상품등록및수정Dto productdata, List<String> imagepathlist, List<String> detailimagelist) {
        if (productdata.categoryId() != null) {
            product.setProductCategory(ProductCategory.builder().id(product.getId()).build());
        }
        if (!StringUtils.isEmpty(productdata.name())) {
            product.setName(productdata.name());
        }
        if (productdata.productCode() != null) {
            product.setProductCode(productdata.productCode());
        }
        if (!StringUtils.isEmpty(productdata.brandName())) {
            product.setBrandName(productdata.brandName());
        }
        if (!StringUtils.isEmpty(productdata.manufacturerName())) {
            product.setManufacturerName(productdata.manufacturerName());
        }
        if (!StringUtils.isEmpty(productdata.supplierName())) {
            product.setSupplierName(productdata.supplierName());
        }
        if (productdata.supplyPrice() != null) {
            product.setSalePrice(productdata.supplyPrice());
        }
        if (!imagepathlist.isEmpty()) {
            product.setImagePath(String.join(" ", imagepathlist));
        }
        if (!detailimagelist.isEmpty()) {
            product.setDetailImagePath(String.join(" ", detailimagelist));
        }
        if (productdata.consumerPrice() != null) {
            product.setConsumerPrice(productdata.consumerPrice());
        }
        if (productdata.salePrice() != null) {
            product.setSalePrice(productdata.salePrice());
        }
        if (productdata.taxStatus() != null) {
            product.setTaxStatus(productdata.taxStatus());
        }
        if (productdata.marginRate() != null) {
            product.setMarginRate(productdata.marginRate());
        }
        if (productdata.taxRate() != null) {
            product.setTaxRate(productdata.taxRate());
        }
        if (productdata.discountRate() != null) {
            product.setDiscountRate(productdata.discountRate());
        }
        if (productdata.consumerRewardRate() != null) {
            product.setConsumerRewardRate(productdata.consumerRewardRate());
        }
        if (productdata.businessRewardRate() != null) {
            product.setBusinessRewardRate(productdata.businessRewardRate());
        }
        if (productdata.productStatus() != null) {
            product.setProductStatus(productdata.productStatus());
        }
        if (productdata.productPriority() != null) {
            product.setProductPriority(productdata.productPriority());
        }
        if (productdata.isExposed() != null) {
            product.setIsExposed(productdata.isExposed());
        }
        if (productdata.isAvailableMultipleOption() != null) {
            product.setIsAvailableMultipleOption(productdata.isAvailableMultipleOption());
        }
        if (!StringUtils.isEmpty(productdata.saleAlternatives())) {
            product.setSaleAlternatives(productdata.saleAlternatives());
        }
        if (productdata.commentReward() != null) {
            product.setCommentReward(productdata.commentReward());
        }
        if (productdata.deadline() != null) {
            product.setDeadline(productdata.deadline());
        }
        if (productdata.otherLowestPrice() != null) {
            product.setOtherLowestPrice(productdata.otherLowestPrice());
        }
        if (!productdata.productOptions().isEmpty()) {

            productdata.productOptions().forEach(상품옵션Dto -> {
                if (상품옵션Dto.ProductOptionId() == null) {
                    throw new BaseException(HttpStatus.BAD_REQUEST, 400, "상품옵션ID가없습니다");
                }
            });
            List<Long> ID들 = productdata.productOptions().stream().map(상품옵션Dto::ProductOptionId).toList();
            List<ProductOption> 수정할상품옵션들 = productOptionRepository.findAllById(ID들);

            productdata.productOptions().forEach(상품옵션Dto -> {
                수정할상품옵션들.forEach(productOption -> {
                    if (!StringUtils.isEmpty(상품옵션Dto.skuCode())) {
                        productOption.setSkuCode(상품옵션Dto.skuCode());
                    }
                    if (상품옵션Dto.optionKey() != null) {
                        productOption.setOptionKey(상품옵션Dto.optionKey());
                    }
                    if (!StringUtils.isEmpty(상품옵션Dto.optionValue())) {
                        productOption.setOptionValue(상품옵션Dto.optionValue());
                    }
                    if (상품옵션Dto.optionSequence() != null) {
                        productOption.setOptionSequence(상품옵션Dto.optionSequence());
                    }
                    if (상품옵션Dto.productQty() != null) {
                        productOption.setProductQty(상품옵션Dto.productQty());
                    }
                    if (상품옵션Dto.maxOrderQty() != null) {
                        productOption.setMaxOrderQty(상품옵션Dto.maxOrderQty());
                    }
                    if (상품옵션Dto.minOrderQty() != null) {
                        productOption.setMinOrderQty(상품옵션Dto.minOrderQty());
                    }
                    if (상품옵션Dto.optionExtraAmount() != null) {
                        productOption.setOptionExtraAmount(상품옵션Dto.optionExtraAmount());
                    }
                    if (상품옵션Dto.isExposed() != null) {
                        productOption.setIsExposed(상품옵션Dto.isExposed());
                    }
                });
            });
        }
        if (!Objects.isNull(productdata.productAiData())) {
            ProductAiDto productAiDto = productdata.productAiData();

            if (productAiDto.getProductAiId() == null) {
                throw new BaseException(HttpStatus.BAD_REQUEST, 400, "AI리뷰ID가없습니다.");
            }

            ProductAi 수정할AI리뷰 = productAiRepository.findById(productAiDto.getProductAiId())
                    .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된AI리뷰ID"));

            if (productAiDto.getAverage() != null) {
                수정할AI리뷰.setAverage(productAiDto.getAverage());
            }
            if (!StringUtils.isEmpty(productAiDto.getMdComment())) {
                수정할AI리뷰.setMdComment(productAiDto.getMdComment());
            }
            if (!StringUtils.isEmpty(productAiDto.getSummary())) {
                수정할AI리뷰.setSummary(productAiDto.getSummary());
            }
            if (productAiDto.getTotalCnt() != null) {
                수정할AI리뷰.setTotalCnt(productAiDto.getTotalCnt());
            }
        }
    }
    private ProductDTO 상품DTO로변환(Product product){

        float calculatedReward = (product.getSalePrice() * product.getConsumerRewardRate());
        float roundedReward = Math.round(calculatedReward);

        return new ProductDTO(
                product.getId(),
                product.getProductCode(),
                product.getName(),
                product.getBrandName(),
                imageParsing.getThumbnailUrl(product.getImagePath()),
                product.getSalePrice(),
                product.getConsumerPrice(),
                roundedReward,
                false,
                null,
                null
        );
    }


}
