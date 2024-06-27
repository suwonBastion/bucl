package com.freeder.buclserver.app.orderreturns.vo;

import com.freeder.buclserver.domain.orderreturn.vo.OrderReturnStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrdReturnRespDto {
	private Long orderReturnId;
	private OrderReturnStatus orderReturnStatus;
}
