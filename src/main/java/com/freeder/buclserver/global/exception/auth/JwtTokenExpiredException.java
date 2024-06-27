package com.freeder.buclserver.global.exception.auth;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class JwtTokenExpiredException extends BaseException {

	public JwtTokenExpiredException() {
		super(UNAUTHORIZED, UNAUTHORIZED.value(), "유효기간이 만료된 JWT 토큰입니다.");
	}
}
