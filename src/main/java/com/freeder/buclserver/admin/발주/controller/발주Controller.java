package com.freeder.buclserver.admin.발주.controller;

import com.freeder.buclserver.admin.발주.dto.주문상태Dto;
import com.freeder.buclserver.admin.발주.service.발주Service;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.shipping.vo.ShippingStatus;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/admin")
@Tag(name = "관리자 발주 관련 API", description = "관리자 발주 관련 API")
public class 발주Controller {
    private final 발주Service service;

    @GetMapping("/dashboard")
    public BaseResponse<?> 발주페이지조회(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return service.발주페이지조회(userDetails);
    }

    @GetMapping("/excel")
    public BaseResponse<?> 주문수조회(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam ShippingStatus shippingStatus
    ) {
        return service.주문수조회(userDetails, shippingStatus);
    }

    @PutMapping("/excel")
    public BaseResponse<?> 주문상태업데이트(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody 주문상태Dto 주문상태Dto
            ) {
        return service.주문상태업데이트(userDetails, 주문상태Dto);
    }

}
