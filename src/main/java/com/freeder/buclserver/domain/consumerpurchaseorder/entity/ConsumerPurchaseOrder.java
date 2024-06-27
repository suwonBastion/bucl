package com.freeder.buclserver.domain.consumerpurchaseorder.entity;

import jakarta.persistence.*;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.productoption.entity.ProductOption;
import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.*;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "consumer_purchase_order")
public class ConsumerPurchaseOrder extends TimestampMixin {
	@Id
	@Column(name = "consumer_purchase_order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "consumer_order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConsumerOrder consumerOrder;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ProductOption productOption;

	@Column(name = "product_order_code", unique = true)
	private String productOrderCode;

	@Column(name = "product_option_value")
	private String productOptionValue;

	@Column(name = "product_amount")
	private int productAmount;

	@Column(name = "product_order_qty")
	private int productOrderQty;

	@Column(name = "product_order_amount")
	private int productOrderAmount;
}
