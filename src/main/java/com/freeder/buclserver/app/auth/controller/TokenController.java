package com.freeder.buclserver.app.auth.controller;

import com.freeder.buclserver.core.security.JwtTokenProvider;
import com.freeder.buclserver.domain.user.vo.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
@RestController
@Tag(name = "JWT토큰발급(개발용) API")
public class TokenController {
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("")
    public String getToken(){
        return jwtTokenProvider.createAccessToken(1L, Role.ROLE_ADMIN);
    }
}
