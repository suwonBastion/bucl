package com.freeder.buclserver.app.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionDto {
	private String skuCode;
	private int productOrderAmt;
	private int productOrderQty;
	private String productOptVal;
}
