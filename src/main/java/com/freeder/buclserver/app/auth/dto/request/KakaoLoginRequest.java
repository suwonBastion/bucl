package com.freeder.buclserver.app.auth.dto.request;


import jakarta.validation.constraints.NotBlank;

public record KakaoLoginRequest(@NotBlank String kakaoAccessToken) {
}
