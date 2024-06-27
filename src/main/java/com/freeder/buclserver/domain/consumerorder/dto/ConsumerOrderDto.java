package com.freeder.buclserver.domain.consumerorder.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumerOrderDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductOption{
        private String productOptionValue;
        private Integer productOptionQty;
    }

    private String orderCode;            //consumer_order
    private String name;                 //product
    private List<ProductOption> productOptions;     //consumer_purchase_order
    private String recipientName;       //shipping_address
    private String zipCode;             //shipping_address
    private String address;             //shipping_address
    private String addressDetail;       //shipping_address
    private String contactNumber;       //shipping_address
    private String memoContent;         //shipping_address
    private Integer shippingFee;         //shipping_info
    private String shippingFeePhrase;   //shipping_info
}
