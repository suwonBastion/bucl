package com.freeder.buclserver.admin.발주.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class 엑셀다운Dto {

    private Long consumerOrderId;
    private Long productId;
    private String productName;
    private String productOptionValue;
    private int productOrderQty;
    private int rewardUseAmount;
    private int totalOrderAmount;
    private String consumerName;
    private String consumerAddress;
    private String consumerCellphone;
    private List<엑셀다운배송Dto> shipping;
    private LocalDateTime createAt;

    @Setter
    @Getter
    @AllArgsConstructor
    @ToString
    public static class 엑셀다운배송Dto{
        private Long shippingId;
        private String shippingCoName;
        private String trackingNum;
    }
}
