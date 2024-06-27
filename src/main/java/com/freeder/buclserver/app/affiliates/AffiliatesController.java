package com.freeder.buclserver.app.affiliates;

import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.affiliate.dto.AffiliateDto;
import com.freeder.buclserver.domain.affiliate.dto.AffiliateReqDto;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/affiliates")
@Tag(name = "affiliates 관련 API", description = "판매 링크 관련 API")
@RequiredArgsConstructor
public class AffiliatesController {
    private final AffiliateService service;

    @PostMapping
    public BaseResponse<?> getSellingPage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody AffiliateReqDto affiliateReqDto
    ) throws Exception {
        return service.getSellingPage(userDetails, affiliateReqDto);
    }

    @GetMapping("")
    public BaseResponse<?> getAffiliateUrl(@RequestParam String affiliateEncrypt) throws Exception {
        return service.getAffiliateUrl(affiliateEncrypt);
    }
}
