package com.freeder.buclserver.app.ordercancels.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderCancelResponseDto {
	private Long orderCancelId;
	private int refundAmount;
	private int rewardUseAmount;
}
