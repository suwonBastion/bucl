package com.freeder.buclserver.app.my.controller;

import com.freeder.buclserver.app.my.service.AccountService;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/account")
@Tag(name = "유저계좌 관련 API", description = "유저계좌 관련 API")
public class AccountController {
    private final AccountService service;

    @GetMapping("")
    public BaseResponse<?> getUserAccount(@AuthenticationPrincipal CustomUserDetails userDetails){
        return service.getUserAccount(userDetails);
    }
}
