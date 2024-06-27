package com.freeder.buclserver.domain.consumerorder.vo;

public enum OrderStatus {
	ORDERED, //주문
	ORDER_CANCELING, //주문 취소 처리중
	ORDER_CANCELED, //주문 취소됨
	ORDER_EXCHANGING, //교환 처리중
	ORDER_EXCHANGED, //교환됨
	ORDER_RETURNING, // 반품 처리 중
	ORDER_RETURNED // 반품됨
}
