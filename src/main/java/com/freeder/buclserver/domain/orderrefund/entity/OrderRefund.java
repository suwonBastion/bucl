package com.freeder.buclserver.domain.orderrefund.entity;

import java.time.LocalDateTime;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import jakarta.persistence.*;

import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Setter
@Table(name = "order_refund")
public class OrderRefund extends TimestampMixin {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_refund_id")
	private Long id;

	@Column(name = "refund_amount")
	private int refundAmount;

	@Column(name = "reward_use_amount")
	private int rewardUseAmount;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consumser_order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConsumerOrder consumerOrder;
}
