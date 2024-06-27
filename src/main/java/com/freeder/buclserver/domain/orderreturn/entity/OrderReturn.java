package com.freeder.buclserver.domain.orderreturn.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.orderrefund.entity.OrderRefund;
import com.freeder.buclserver.domain.orderreturn.vo.OrderReturnExr;
import com.freeder.buclserver.domain.orderreturn.vo.OrderReturnStatus;
import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "order_return")
public class OrderReturn extends TimestampMixin {
//	@Id
//	@Column(name = "order_return_code", unique = true, nullable = false)
//	private String orderReturnCode;

	@Id
	@Column(name = "order_return_id", unique = true, nullable = false)
	private Long orderReturnId;

	@ManyToOne
	@JoinColumn(name = "consumer_order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConsumerOrder consumerOrder;

	@OneToOne
	@JoinColumn(name = "order_refund_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrderRefund orderRefund;

	@Column(name = "order_return_fee")
	private int orderReturnFee;

	@Column(name = "order_return_exr")
	@Enumerated(EnumType.STRING)
	private OrderReturnExr orderReturnExr;

	@Column(name = "order_return_status")
	@Enumerated(EnumType.STRING)
	private OrderReturnStatus orderReturnStatus;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;
}
