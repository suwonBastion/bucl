package com.freeder.buclserver.global.exception.servererror;

import com.freeder.buclserver.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UnauthorizedErrorException extends BaseException {
	public UnauthorizedErrorException(String errorMessage) {
		super(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), errorMessage);
	}
}
