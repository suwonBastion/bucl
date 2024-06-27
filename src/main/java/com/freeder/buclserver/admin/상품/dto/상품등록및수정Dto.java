package com.freeder.buclserver.admin.상품.dto;

import com.freeder.buclserver.domain.product.dto.ProductDetailDTO;
import com.freeder.buclserver.domain.product.vo.ProductStatus;
import com.freeder.buclserver.domain.product.vo.TaxStatus;
import com.freeder.buclserver.domain.productai.entity.ProductAi;
import com.freeder.buclserver.domain.productoption.entity.ProductOption;
import com.freeder.buclserver.domain.productoption.vo.OptionKey;

import java.time.LocalDateTime;
import java.util.List;

import static com.freeder.buclserver.domain.product.dto.ProductDetailDTO.*;


public record 상품등록및수정Dto(
        Long categoryId,
        Long productId,
        String name,
        Long productCode,
        String brandName,
        String manufacturerName,
        String supplierName,
        Integer supplyPrice,
        Integer consumerPrice,
        Integer salePrice,
        TaxStatus taxStatus,
        Float marginRate,
        Float taxRate,
        Float discountRate,
        Float consumerRewardRate,
        Float businessRewardRate,
        ProductStatus productStatus,
        Integer productPriority,
        Boolean isExposed,
        Boolean isAvailableMultipleOption,
        String saleAlternatives,
        Integer commentReward,
        LocalDateTime deadline,
        Long otherLowestPrice,
        List<상품옵션Dto> productOptions,
        ProductAiDto productAiData
) {
    public record 상품옵션Dto(
            Long ProductOptionId,
            String skuCode,
            OptionKey optionKey,
            String optionValue,
            Integer optionSequence,
            Integer productQty,
            Integer maxOrderQty,
            Integer minOrderQty,
            Integer optionExtraAmount,
            Boolean isExposed
    ){}

    public static 상품옵션Dto 상품옵션Dto로변환(ProductOption productOptions){
        return new 상품옵션Dto(
                productOptions.getId(),
                productOptions.getSkuCode(),
                productOptions.getOptionKey(),
                productOptions.getOptionValue(),
                productOptions.getOptionSequence(),
                productOptions.getProductQty(),
                productOptions.getMaxOrderQty(),
                productOptions.getMinOrderQty(),
                productOptions.getOptionExtraAmount(),
                productOptions.isExposed()
        );
    }

    public static ProductAiDto ProductAiDto로변환(ProductAi productAi){
        return new ProductAiDto(
                productAi.getId(),
                productAi.getAverage(),
                productAi.getMdComment(),
                productAi.getSummary(),
                productAi.getTotalCnt()

        );
    }
}
