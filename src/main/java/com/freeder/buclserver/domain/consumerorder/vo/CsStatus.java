package com.freeder.buclserver.domain.consumerorder.vo;

public enum CsStatus {
	NONE, // 없음
	ORDER_CANCEL, //주문 취소 요청
	ORDER_EXCHANGE, //교환 요청
	ORDER_RETURN //반품 요청
}
