package com.freeder.buclserver.global.exception.consumerorder;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class OrderIdNotFoundException extends BaseException {

	public OrderIdNotFoundException(Long consumerOrderId) {
		super(NOT_FOUND, NOT_FOUND.value(), "해당 아이디(PK)를 가진 주문 내역은 존재하지 않습니다. PK = " + consumerOrderId);
	}
}
