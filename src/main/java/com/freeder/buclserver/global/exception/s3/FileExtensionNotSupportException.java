package com.freeder.buclserver.global.exception.s3;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class FileExtensionNotSupportException extends BaseException {

	public FileExtensionNotSupportException() {
		super(BAD_REQUEST, BAD_REQUEST.value(), "지원하지 않는 파일 확장자입니다.");
	}
}
