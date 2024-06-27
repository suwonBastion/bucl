package com.freeder.buclserver.domain.orderexchange.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.orderexchange.vo.OrderExchangeExr;
import com.freeder.buclserver.domain.orderexchange.vo.OrderExchangeStatus;
import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "order_exchange")
public class OrderExchange extends TimestampMixin {
	//창민에서는 없어짐
	//	@Id
	//	@Column(name = "order_exchange_code", unique = true, nullable = false)
	//	private String orderExchangeCode;

	@Id
	@Column(name = "order_exchange_id", unique = true, nullable = false)
	private Long orderExchangeId;

	@OneToOne
	@JoinColumn(name = "consumer_order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConsumerOrder consumerOrder;

	@OneToOne
	@JoinColumn(name = "order_exchange_shipping_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Shipping orderExchangeShipping;

	@Column(name = "orderExchange_fee")
	private int orderExchangeFee;

	@Column(name = "order_exchange_exr")
	@Enumerated(EnumType.STRING)
	private OrderExchangeExr orderExchangeExr;

	@Column(name = "order_exchange_status")
	@Enumerated(EnumType.STRING)
	private OrderExchangeStatus orderExchangeStatus;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;
}
