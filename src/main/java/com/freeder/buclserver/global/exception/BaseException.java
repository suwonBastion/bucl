package com.freeder.buclserver.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
	private final HttpStatus httpStatus;
	private final int errorCode;
	private final String errorMessage;
}
