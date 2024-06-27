package com.freeder.buclserver.domain.reward.entity;

import com.freeder.buclserver.domain.rewardwithdrawal.entity.RewardWithdrawal;
import jakarta.persistence.*;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.orderrefund.entity.OrderRefund;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.reward.vo.RewardType;
import com.freeder.buclserver.domain.rewardwithdrawalaccount.entity.RewardWithdrawalAccount;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reward")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reward extends TimestampMixin {

	@Id
	@Column(name = "reward_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Product product;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consumer_order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConsumerOrder consumerOrder;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_refund_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrderRefund orderRefund;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reward_withdrawal_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RewardWithdrawal rewardWithdrawal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reward_withdrawal_account", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RewardWithdrawalAccount rewardWithdrawalAccount;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_brand_name")
	private String productBrandName;

	@Column(name = "reward_type")
	@Enumerated(EnumType.STRING)
	private RewardType rewardType;

	@Column(name = "received_reward_amount")
	private Integer receivedRewardAmount;

	@Column(name = "spent_reward_amount")
	private Integer spentRewardAmount;

	@Column(name = "previous_reward_sum")
	private Integer previousRewardSum;

	@Column(name = "reward_sum")
	private Integer rewardSum;

}
