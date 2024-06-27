package com.freeder.buclserver.domain.openbanking.dto;

import com.freeder.buclserver.domain.openbanking.vo.BANK_CODE;
import jakarta.validation.constraints.NotBlank;

public record ReqApiDto(
        @NotBlank String name,
        @NotBlank String birth,
        @NotBlank String account,
        @NotBlank BANK_CODE bankNm
) {
}
