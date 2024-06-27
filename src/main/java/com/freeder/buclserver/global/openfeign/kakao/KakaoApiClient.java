package com.freeder.buclserver.global.openfeign.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.freeder.buclserver.app.auth.dto.response.KakaoUserInfoResponse;

@FeignClient(
	name = "kakaouserinfo",
	url = "https://kapi.kakao.com",
	configuration = FeignClientConfiguration.class
)
public interface KakaoApiClient {

	@GetMapping("/v2/user/me")
	KakaoUserInfoResponse getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);
}
