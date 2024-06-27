package com.freeder.buclserver.domain.consumerpayment.vo;

public enum PaymentStatus {
	READY, //결제대기: 브라우저 창 이탈, 가상계좌 발급 완료 등 미결제 상태
	PAID, //결제완료
	CANCELLED, //결제취소
	FAILED //신용카드 한도 초과
}
