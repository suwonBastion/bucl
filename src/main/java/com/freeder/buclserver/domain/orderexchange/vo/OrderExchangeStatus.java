package com.freeder.buclserver.domain.orderexchange.vo;

public enum OrderExchangeStatus {
	RECEIVED, // 교환 신청
	IN_PROCESS, // 교환 처리중
	COMPLETED, // 교환 완료
	ON_HOLD // 교환 보류
}
