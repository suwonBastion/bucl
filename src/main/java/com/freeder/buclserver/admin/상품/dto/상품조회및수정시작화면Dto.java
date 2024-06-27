package com.freeder.buclserver.admin.상품.dto;

public record 상품조회및수정시작화면Dto(
        Long productId,
        String productName,
        int commentCnt,
        int goodCnt,
        int badCnt
) {
}
