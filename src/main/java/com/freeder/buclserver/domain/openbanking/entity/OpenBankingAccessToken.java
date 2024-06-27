package com.freeder.buclserver.domain.openbanking.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity
@Builder
@Getter
@Setter
@Table(name = "openbanking")
public class OpenBankingAccessToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq", nullable = false)
	private Long seq;

	@Column(nullable = false, length = 400)
	private String accessToken;

	@Column(nullable = false)
	private String tokenType;

	@Column(nullable = false)
	private String expireDate;

	@Column(nullable = false)
	private String scope;

	@Column(nullable = false)
	private String clientUseCode;

	public OpenBankingAccessToken(Long seq, String accessToken, String tokenType, String expireDate, String scope,
		String clientUseCode) {
		this.seq = seq;
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.expireDate = expireDate;
		this.scope = scope;
		this.clientUseCode = clientUseCode;
	}

	public OpenBankingAccessToken() {

	}
}
