package com.freeder.buclserver.domain.affiliate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class SellingDto {
    private String name;
    private String brandName;
    private List<String> imagePath;
    private float reward;
    private String affiliateUrl;
}
