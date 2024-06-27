package com.freeder.buclserver.global.exception.user;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class UserIdNotFoundException extends BaseException {

	public UserIdNotFoundException(Long memberId) {
		super(NOT_FOUND, NOT_FOUND.value(), "해당 아이디(PK)를 가진 사용자는 존재하지 않습니다. PK = " + memberId);
	}
}
