package com.freeder.buclserver.admin.발주.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class 엑셀다운가공전Dto {
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
    private Long shippingId;
    private String shippingCoName;
    private String trackingNum;
    private LocalDateTime createAt;
}
