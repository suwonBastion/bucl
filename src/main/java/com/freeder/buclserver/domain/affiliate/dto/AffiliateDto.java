package com.freeder.buclserver.domain.affiliate.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AffiliateDto {
    private Long productId;
    private Long userId;
}
