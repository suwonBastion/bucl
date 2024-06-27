package com.freeder.buclserver.global.exception.usershippingaddress;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class AddressIdNotFoundException extends BaseException {

	public AddressIdNotFoundException(Long addressId) {
		super(NOT_FOUND, NOT_FOUND.value(), "해당 아이디(PK)를 가진 주소가 존재하지 않습니다. PK = " + addressId);
	}
}
