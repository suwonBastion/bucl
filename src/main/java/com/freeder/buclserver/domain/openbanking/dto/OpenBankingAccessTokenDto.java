package com.freeder.buclserver.domain.openbanking.dto;

import com.freeder.buclserver.domain.openbanking.entity.OpenBankingAccessToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@AllArgsConstructor
public class OpenBankingAccessTokenDto {
	String access_token;
	String token_type;
	String expires_in;
	String scope;
	String client_use_code;

	public OpenBankingAccessToken toEntity() {
		LocalDateTime expireDate = LocalDateTime
			.now(ZoneId.of("Asia/Seoul"))
			.plusSeconds(Integer.parseInt(expires_in));

		return OpenBankingAccessToken.builder()
			.accessToken(access_token)
			.tokenType(token_type)
			.expireDate(expireDate.toString())
			.scope(scope)
			.clientUseCode(client_use_code)
			.build();
	}

	public OpenBankingAccessTokenDto() {
	}
}
