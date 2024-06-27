package com.freeder.buclserver.global.openfeign.kakao;

import com.freeder.buclserver.global.exception.kakao.KakaoServerException;
import com.freeder.buclserver.global.exception.kakao.KakaoTokenValidException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class KakaoFeignError implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() == 401) {
			throw new KakaoTokenValidException();
		}
		throw new KakaoServerException();
	}
}
