package com.freeder.buclserver.domain.wish.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishDto {
    private String brandName;
    private String name;
    private List<String> imagePath;
    private long productCode;
    private int consumerPrice;
    private float starRate;
    private int consumerOrdersNumber;
    private Boolean isEnded;

    @Getter
    @Builder
    @ToString
    public static class WishCreateRes{
        private Long wishId;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WishCreateReq{
        private Long productId;
    }
}
