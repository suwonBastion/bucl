package com.freeder.buclserver.app.orderexchanges.dto;

import com.freeder.buclserver.domain.consumerorder.vo.CsStatus;
import com.freeder.buclserver.domain.ordercancel.vo.OrderCancelExr;
import com.freeder.buclserver.domain.ordercancel.vo.OrderCancelStatus;

import java.time.LocalDateTime;
import java.util.List;

public record 주문취소목록Dto(
        Long orderCancelId,
        String orderCode,
        OrderCancelStatus orderCancelStatus,
        OrderCancelExr orderCancelExr,
        CsStatus csStatus,
        List<String> recipientName,
        List<String> contactNumber,
        LocalDateTime createdAt
) {
}
