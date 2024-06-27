package com.freeder.buclserver.app.orderexchanges.dto;

import com.freeder.buclserver.domain.orderexchange.vo.OrderExchangeStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrdExchRespDto {
	private Long orderExchId;
	private OrderExchangeStatus orderExchangeStatus;
}
