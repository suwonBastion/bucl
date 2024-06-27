package com.freeder.buclserver.app.wishes;

import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.wish.dto.WishDto;
import com.freeder.buclserver.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/wishes")
@RequiredArgsConstructor
@Tag(name = "wishes 관련 API", description = "찜 관련 API")
public class WishesController {
    private final WishesService service;

    @GetMapping()
    public BaseResponse<?> getWishesList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return service.getWishesList(userDetails, page, pageSize);
    }

    @PostMapping()
    public BaseResponse<?> saveWish(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody WishDto.WishCreateReq wishCreateReq
    ) {
        return service.saveWish(userDetails, wishCreateReq);
    }

    @DeleteMapping("/{product_code}")
    public BaseResponse<?> deleteWish(
            @PathVariable(name = "product_code") Long productCode,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return service.deleteWish(userDetails, productCode);
    }
}
