package com.freeder.buclserver.admin.발주.dto;

import com.freeder.buclserver.domain.shipping.vo.ShippingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class 주문상태Dto {

    private List<엑셀업로드> data;
    @NotBlank
    private ShippingStatus shippingStatus;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class 엑셀업로드 {
        @NotEmpty
        private Long shippingId;
        private String shippingCoName;
        private String trackingNum;
    }
}
