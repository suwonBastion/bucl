package com.freeder.buclserver.app.rewards;

import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.openbanking.dto.OpenBankingAccessTokenDto;
import com.freeder.buclserver.domain.openbanking.dto.ReqApiDto;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/openapi")
@Tag(name = "account API", description = "금융결제원 Open API")
public class RewardsAccountController {

	private final OpenBankingService openBankingService;

	public RewardsAccountController(OpenBankingService openBankingService) {
		this.openBankingService = openBankingService;
	}

	@GetMapping("/certification")
	public BaseResponse<?> requestOpenApiUserCertification() {
		try {
			openBankingService.requestOpenApiUserCertification();
			return new BaseResponse<>(null, HttpStatus.OK, "Success");
		} catch (Exception e) {
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, e.getMessage());
		}
	}

	@PostMapping("/accountvalid")
	public BaseResponse<?> requestOpenApiAccessToken(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@Valid @RequestBody ReqApiDto reqApiDto
	) {
		try {
			boolean result = openBankingService.requestOpenApiAccessToken(userDetails, reqApiDto);
			return new BaseResponse<>(result, HttpStatus.OK, "계좌본인인증성공");
		} catch (Exception e) {
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, e.getMessage());
		}
	}

}
