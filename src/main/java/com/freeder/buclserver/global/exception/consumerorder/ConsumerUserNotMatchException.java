package com.freeder.buclserver.global.exception.consumerorder;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class ConsumerUserNotMatchException extends BaseException {

	public ConsumerUserNotMatchException() {
		super(UNAUTHORIZED, UNAUTHORIZED.value(), "해당 상품을 주문한 사용자가 아닙니다.");
	}
}
