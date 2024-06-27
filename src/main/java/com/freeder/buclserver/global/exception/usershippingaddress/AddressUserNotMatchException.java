package com.freeder.buclserver.global.exception.usershippingaddress;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class AddressUserNotMatchException extends BaseException {

	public AddressUserNotMatchException() {
		super(BAD_REQUEST, BAD_REQUEST.value(), "요청을 보낸 사용자가 등록한 주소가 아닙니다.");
	}
}
