package com.freeder.buclserver.global.exception.servererror;

import com.freeder.buclserver.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class BadRequestErrorException extends BaseException {
	public BadRequestErrorException(String errorMessage) {
		super(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errorMessage);
	}
}
