package com.freeder.buclserver.global.exception.auth;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class JwtTokenValidException extends BaseException {

	public JwtTokenValidException() {
		super(UNAUTHORIZED, UNAUTHORIZED.value(), "유효하지 않는 JWT 토큰입니다.");
	}
}
