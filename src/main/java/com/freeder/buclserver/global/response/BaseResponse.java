package com.freeder.buclserver.global.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
	// ResponseEntity 정적 메소드 안에 넣어서 사용
	// ex) ResponseEntity.ok(new BaseResponse(data,HttpStatus.OK, "요청 성공")
	private T data;
	private final HttpStatus statusCode;
	private final String message;

}
