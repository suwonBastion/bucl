package com.freeder.buclserver.global.exception.servererror;

import com.freeder.buclserver.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends BaseException {
	public InternalServerErrorException(String errorMessage) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
	}
}
