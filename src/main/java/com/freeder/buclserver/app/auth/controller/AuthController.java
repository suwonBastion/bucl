package com.freeder.buclserver.app.auth.controller;



import com.freeder.buclserver.app.auth.dto.request.AppleLoginRequest;
import com.freeder.buclserver.app.auth.dto.response.AppleUserInfoResponse;
import com.freeder.buclserver.global.util.TokenDecoder;
import com.freeder.buclserver.global.webclient.KakaoApiClient;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.freeder.buclserver.app.auth.dto.request.KakaoLoginRequest;
import com.freeder.buclserver.app.auth.dto.request.RefreshTokenRequest;
import com.freeder.buclserver.app.auth.dto.response.KakaoUserInfoResponse;
import com.freeder.buclserver.app.auth.dto.response.TokenResponse;
import com.freeder.buclserver.app.auth.service.JwtTokenService;
import com.freeder.buclserver.app.my.service.MyService;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.user.dto.UserDto;
import com.freeder.buclserver.global.response.BaseResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@RestController
@Tag(name = "oauth2 API", description = "소셜 관련 API")
public class AuthController {

	private final JwtTokenService jwtTokenService;
	private final KakaoApiClient kakaoApiClient;
	private final MyService myService;

	@Value("${bucl.service.auth.COOKIE-MAX-AGE-ACCESS-TOKEN}")
	private int COOKIE_MAX_AGE_ACCESS_TOKEN;

	@Value("${bucl.service.auth.COOKIE-MAX-AGE-REFRESH_TOKEN}")
	private int COOKIE_MAX_AGE_REFRESH_TOKEN;

	@PostMapping("/login/kakao")
	public BaseResponse kakaoLogin(@Valid @RequestBody KakaoLoginRequest request, HttpServletResponse response) {
		KakaoUserInfoResponse userInfo = kakaoApiClient.kakaoGetUser(request.kakaoAccessToken()).block();

		UserDto userDto = myService.findBySocialIdAndDeletedAtIsNull(userInfo.getId())
				.orElseGet(() -> myService.join(userInfo.toUserDto()));

		TokenResponse tokens = jwtTokenService.createJwtTokens(userDto.id(), userDto.role());

		response.addCookie(createCookie("access-token", tokens.accessToken(), COOKIE_MAX_AGE_ACCESS_TOKEN));
		response.addCookie(createCookie("refresh-token", tokens.refreshToken(), COOKIE_MAX_AGE_REFRESH_TOKEN));

		return new BaseResponse(null, HttpStatus.OK, "요청 성공");
	}

	@PostMapping("/login/apple")
	public BaseResponse<?> appleLogin(@RequestBody AppleLoginRequest appleLoginRequest, HttpServletResponse response){

		AppleUserInfoResponse appleUserDto = TokenDecoder.decodePayload(appleLoginRequest.token(), AppleUserInfoResponse.class);

		UserDto userDto = myService.findBySocialIdAndDeletedAtIsNull(appleUserDto.getSub())
				.orElseGet(() -> myService.join(appleUserDto.toUserDto()));

		TokenResponse tokens = jwtTokenService.createJwtTokens(userDto.id(), userDto.role());

		response.addCookie(createCookie("access-token", tokens.accessToken(), COOKIE_MAX_AGE_ACCESS_TOKEN));
		response.addCookie(createCookie("refresh-token", tokens.refreshToken(), COOKIE_MAX_AGE_REFRESH_TOKEN));

		return new BaseResponse(null, HttpStatus.OK, "요청 성공");
	}

	@PostMapping("/renewal/tokens")
	public BaseResponse renewTokens(@Valid @RequestBody RefreshTokenRequest request, HttpServletResponse response) {
		TokenResponse tokens = jwtTokenService.renewTokens(request.refreshToken());

		response.addCookie(createCookie("access-token", tokens.accessToken(), COOKIE_MAX_AGE_ACCESS_TOKEN));
	    response.addCookie(createCookie("refresh-token", tokens.refreshToken(), COOKIE_MAX_AGE_REFRESH_TOKEN));

		return new BaseResponse(null, HttpStatus.OK, "요청 성공");
	}

	@PostMapping("/logout")
	public BaseResponse logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
		String userId = userDetails.getUserId();
		myService.deleteRefreshToken(Long.valueOf(userId));
		return new BaseResponse(userId, HttpStatus.OK, "요청 성공");
	}

	@PostMapping("/member-withdrawal")
	public BaseResponse withdrawal(@AuthenticationPrincipal CustomUserDetails userDetails) {
		String userId = userDetails.getUserId();
		myService.withdrawal(Long.valueOf(userId));
		return new BaseResponse(userId, HttpStatus.OK, "요청 성공");
	}

	private Cookie createCookie(String cookieName, String token, int maxAge) {
		Cookie cookie = new Cookie(cookieName, token);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		return cookie;
	}
}
