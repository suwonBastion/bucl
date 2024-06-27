package com.freeder.buclserver.domain.rewardwithdrawal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawalRequestDto {
	private String bankCodeStd;
	private String bankName;
	private Integer withdrawalAmount;
	private String accountNum;
	private String accountHolderName;
}
