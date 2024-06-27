package com.freeder.buclserver.domain.orderreturn.vo;

public enum OrderReturnStatus {
	RECEIVED, // 반품 접수
	IN_PROCESS, // 반품 처리중
	COMPLETED, // 반품 완료
	ON_HOLD // 반품 보류
}
