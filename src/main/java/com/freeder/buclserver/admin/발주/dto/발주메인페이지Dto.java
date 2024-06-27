package com.freeder.buclserver.admin.발주.dto;

import java.math.BigDecimal;

public record 발주메인페이지Dto(
    Object NOT_PROCESSING,
    Object PROCESSING,
    Object IN_DELIVERY,
    Object DELIVERED
) {
}
