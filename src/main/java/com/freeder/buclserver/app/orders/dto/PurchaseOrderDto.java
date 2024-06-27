package com.freeder.buclserver.app.orders.dto;

import com.freeder.buclserver.domain.consumerpurchaseorder.entity.ConsumerPurchaseOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PurchaseOrderDto {

	private String productOrderCode; //상품 주문 코드
	private String productOptionValue; // 상품 옵션 명
	private int productAmount; //상품 금액
	private int productOrderQty; // 상품 주문 수량
	private int productOrderAmount; // 상품 주문 금액 : 상품금액 * 상품주문수량

	public static PurchaseOrderDto from(ConsumerPurchaseOrder consumerPurchaseOrder) {
		return new PurchaseOrderDto(
			consumerPurchaseOrder.getProductOrderCode(),
			consumerPurchaseOrder.getProductOptionValue(),
			consumerPurchaseOrder.getProductAmount(),
			consumerPurchaseOrder.getProductOrderQty(),
			consumerPurchaseOrder.getProductOrderAmount()
		);
	}
}
