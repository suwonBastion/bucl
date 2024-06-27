package com.freeder.buclserver.domain.consumerorder.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackingNumDto {
    @NotEmpty
    private String orderCode;
    @NotEmpty
    private String trakingNum;
    @NotEmpty
    private String shippingCoName;

}
