package com.freeder.buclserver.global.exception.s3;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class FileUploadException extends BaseException {

	public FileUploadException() {
		super(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.value(), "s3에 파일 업로드 중 에러가 발생해 업로드에 실패했습니다.");
	}
}
