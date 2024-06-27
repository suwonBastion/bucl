package com.freeder.buclserver.global.exception.usershippingaddress;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class SingleAddressDefaultRegisterException extends BaseException {

	public SingleAddressDefaultRegisterException() {
		super(BAD_REQUEST, BAD_REQUEST.value(), "배송지가 하나일 때는 기본 배송지의 유무를 false로 설정할 수 없습니다.");
	}
}
