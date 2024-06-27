package com.freeder.buclserver.app.payment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentPrepareDto {
	private String merchantUid;
	private int amount;
	private Long productCode;

	private ProductOptionDto productOption;
}
