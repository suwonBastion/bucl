package com.freeder.buclserver.domain.openbanking.dto;

import com.freeder.buclserver.global.util.DateUtils;

import java.util.Random;

public record SendApiDto(
        String bank_tran_id,
        String bank_code_std,
        String account_num,
        String account_holder_info_type,
        String account_holder_info,
        String tran_dtime
) {
    public static SendApiDto merge(OpenBankingAccessTokenDto dto, ReqApiDto reqApiDto) {
        Random random = new Random();
        int randomNumber = random.nextInt(900000000) + 100000000;

        return new SendApiDto(
                dto.getClient_use_code() + "U" + randomNumber,
                reqApiDto.bankNm().getBankCode(),
                reqApiDto.account(),
                "",
                reqApiDto.birth(),
                DateUtils.nowDateString()
        );
    }
}
