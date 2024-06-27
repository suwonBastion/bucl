package com.freeder.buclserver.domain.ordercancel.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.ordercancel.vo.OrderCancelExr;
import com.freeder.buclserver.domain.ordercancel.vo.OrderCancelStatus;
import com.freeder.buclserver.domain.orderrefund.entity.OrderRefund;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "order_cancel")
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderCancel extends TimestampMixin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_cancel_id")
	private Long orderCancelId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consumer_order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConsumerOrder consumerOrder;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_refund_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrderRefund orderRefund;

	@Column(name = "order_cancel_status")
	@Enumerated(EnumType.STRING)
	private OrderCancelStatus orderCancelStatus;

	@Column(name = "order_cancel_exr")
	@Enumerated(EnumType.STRING)
	private OrderCancelExr orderCancelExr;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;

	public void setOrderCancelStatus(OrderCancelStatus orderCancelStatus) {
		this.orderCancelStatus = orderCancelStatus;
	}

	public void setCompletedAt(LocalDateTime completedAt) {
		this.completedAt = completedAt;
	}
}
