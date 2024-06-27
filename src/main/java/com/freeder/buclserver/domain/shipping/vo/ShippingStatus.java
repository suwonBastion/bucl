package com.freeder.buclserver.domain.shipping.vo;

public enum ShippingStatus {
	NOT_PROCESSING, // 아직 처리 안됨 : 발주 안 넣음
	PROCESSING, //상품 준비중 : 발주 넣음
	IN_DELIVERY, //배송 중 : 운송장 번호 넣음
	DELIVERED, //배송 완료

	DELAY, //배송 지연
	PICK_UP //수거
}
