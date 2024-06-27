package com.freeder.buclserver.domain.rewardwithdrawal.dto;

import com.freeder.buclserver.domain.rewardwithdrawal.vo.WithdrawalStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WithdrawalHistoryDto {
	private Integer rewardWithdrawalAmount;
	private WithdrawalStatus withdrawalStatus;
	private LocalDateTime lastUsedDate;
}
