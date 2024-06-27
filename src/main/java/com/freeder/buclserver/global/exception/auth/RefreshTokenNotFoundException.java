package com.freeder.buclserver.global.exception.auth;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class RefreshTokenNotFoundException extends BaseException {

	public RefreshTokenNotFoundException() {
		super(NOT_FOUND, NOT_FOUND.value(), "해당 refresh 토큰을 가진 사용자가 없습니다.");
	}
}
