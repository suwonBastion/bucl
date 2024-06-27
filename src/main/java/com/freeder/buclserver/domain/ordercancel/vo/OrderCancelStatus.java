package com.freeder.buclserver.domain.ordercancel.vo;

public enum OrderCancelStatus {
	RECEIVED, // 주문 취소 접수
	IN_PROCESS, // 주문 취소중
	COMPLETED, // 주문 취소 완료
	ON_HOLD // 주문 취소 보류
}
