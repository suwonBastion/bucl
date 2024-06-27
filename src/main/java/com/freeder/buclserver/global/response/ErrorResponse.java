package com.freeder.buclserver.global.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	private final HttpStatus statusCode;
	private final String message;
}
