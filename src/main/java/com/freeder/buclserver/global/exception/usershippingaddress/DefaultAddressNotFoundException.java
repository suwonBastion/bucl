package com.freeder.buclserver.global.exception.usershippingaddress;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class DefaultAddressNotFoundException extends BaseException {

	public DefaultAddressNotFoundException() {
		super(NOT_FOUND, NOT_FOUND.value(), "기본 주소지가 존재하지 않습니다.");
	}
}