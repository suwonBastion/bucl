package com.freeder.buclserver.app.orderreturns.dto;

import com.freeder.buclserver.domain.consumerorder.vo.CsStatus;
import com.freeder.buclserver.domain.orderreturn.vo.OrderReturnExr;
import com.freeder.buclserver.domain.orderreturn.vo.OrderReturnStatus;

import java.time.LocalDateTime;
import java.util.List;

public record 주문반품Dto(
        Long orderReturnId,
        int orderReturnFee,
        OrderReturnExr orderReturnExr,
        OrderReturnStatus orderReturnStatus,
        String orderCode,
        CsStatus csStatus,
        List<String> recipientName,
        List<String> contactNumber,
        LocalDateTime createdAt

) {
}
