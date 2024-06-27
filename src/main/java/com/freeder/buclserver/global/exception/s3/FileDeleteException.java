package com.freeder.buclserver.global.exception.s3;

import static org.springframework.http.HttpStatus.*;

import com.freeder.buclserver.global.exception.BaseException;

public class FileDeleteException extends BaseException {

	public FileDeleteException() {
		super(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.value(), "s3 파일 삭제 과정에서 에러가 발생해 파일 삭제에 실패했습니다.");
	}
}
