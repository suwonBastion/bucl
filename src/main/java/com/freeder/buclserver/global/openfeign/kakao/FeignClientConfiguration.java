package com.freeder.buclserver.global.openfeign.kakao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignClientConfiguration {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return template -> template.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new KakaoFeignError();
	}
}
