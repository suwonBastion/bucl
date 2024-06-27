package com.freeder.buclserver.global.exception;

import java.io.PrintWriter;
import java.io.StringWriter;



import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.freeder.buclserver.global.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> handleRestApiException(BaseException ex) {
		log.error("[Base_Exception]: {}", convertExceptionStackTraceToString(ex));

		return ResponseEntity
			.status(ex.getHttpStatus())
			.body(new ErrorResponse(ex.getHttpStatus(), ex.getErrorMessage()));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
		log.error("[Constraint_Violation_Exception]: {}", convertExceptionStackTraceToString(ex));

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}

	// spring boot 3.2.4 updateCode
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
	) {
		log.error("[MethodArgument_NotValid_Exception]: {}", convertExceptionStackTraceToString(ex));

		return ResponseEntity
			.status(status.value())
			.body(new ErrorResponse(HttpStatus.valueOf(status.value()), ex.getMessage()));
	}

	// spring boot 3.2.4 updateCode
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
		Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request
	) {
		log.error("[Spring_MVC_Standard_Exception]: {}", convertExceptionStackTraceToString(ex));

		return ResponseEntity
			.status(status)
				.body(new ErrorResponse(HttpStatus.valueOf(status.value()), ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.error("[Exception]: {}", convertExceptionStackTraceToString(ex));

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
	}

	private static String convertExceptionStackTraceToString(Exception ex) {
		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));
		return String.valueOf(stringWriter);
	}
}
