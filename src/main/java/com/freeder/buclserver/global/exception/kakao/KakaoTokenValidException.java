package com.freeder.buclserver.global.exception.kakao;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class KakaoTokenValidException extends BaseException {

	public KakaoTokenValidException() {
		super(UNAUTHORIZED, UNAUTHORIZED.value(), "유효하지 않는 KAKAO 토큰입니다.");
	}
}
