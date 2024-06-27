package com.freeder.buclserver.domain.rewardwithdrawal.entity;

import java.time.LocalDateTime;

import com.freeder.buclserver.domain.rewardwithdrawal.vo.WithdrawalStatus;
import jakarta.persistence.*;

import com.freeder.buclserver.domain.user.entity.User;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "reward_withdrawal")
public class RewardWithdrawal {
	@Id
	@Column(name = "reward_withdrawal_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@Column(name = "bank_code_std")
	private String bankCodeStd;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "reward_withdrawal_amount")
	private Integer rewardWithdrawalAmount;

	@Column(name = "account_num")
	private String accountNum;

	@Column(name = "account_holder_name")
	private String accountHolderName;

	@Column(name = "is_withdrawn")
	private boolean isWithdrawn;

	@Column(name = "withdrawal_status")
	@Enumerated(EnumType.STRING)
	private WithdrawalStatus withdrawalStatus;

	@Column(name = "last_used_date")
	private LocalDateTime lastUsedDate;
}
