package com.freeder.buclserver.domain.consumerorder.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import com.freeder.buclserver.domain.consumerorder.vo.CsStatus;
import com.freeder.buclserver.domain.consumerorder.vo.OrderStatus;
import com.freeder.buclserver.domain.consumerpayment.entity.ConsumerPayment;
import com.freeder.buclserver.domain.consumerpurchaseorder.entity.ConsumerPurchaseOrder;
import com.freeder.buclserver.domain.grouporder.entity.GroupOrder;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.global.mixin.TimestampMixin;

@Entity
@Table(name = "consumer_order")
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@ToString
public class ConsumerOrder extends TimestampMixin {
	@Id
	@Column(name = "consumer_order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consumer_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private User consumer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "business_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private User business;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private GroupOrder groupOrder;

	// spring boot 3.2.4 updateCode
	@Builder.Default
	@OneToMany(mappedBy = "consumerOrder")
	@ToString.Exclude
	private List<Shipping> shippings = new ArrayList<>();

	// spring boot 3.2.4 updateCode
	@Builder.Default
	@OneToMany(mappedBy = "consumerOrder")
	@ToString.Exclude
	private List<ConsumerPayment> consumerPayments = new ArrayList<>();

	// spring boot 3.2.4 updateCode
	@Builder.Default
	@OneToMany(mappedBy = "consumerOrder")
	@ToString.Exclude
	private List<ConsumerPurchaseOrder> consumerPurchaseOrders = new ArrayList<>();

	@Column(name = "order_code", unique = true)
	private String orderCode;

	@Column(name = "order_num")
	private int orderNum;

	@Column(name = "shipping_fee")
	private int shippingFee;

	@Column(name = "total_order_amount")
	private int totalOrderAmount;

	@Column(name = "reward_use_amount")
	private int rewardUseAmount;

	@Column(name = "spent_amount")
	private int spentAmount;

	@ColumnDefault("false")
	@Column(name = "is_rewarded", nullable = false)
	private boolean isRewarded;

	@ColumnDefault("false")
	@Column(name = "is_confirmed")
	private boolean isConfirmed;

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status")
	private OrderStatus orderStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "cs_status")
	private CsStatus csStatus;

	public void setConsumerPurchaseOrders(List<ConsumerPurchaseOrder> consumerPurchaseOrders) {
		this.consumerPurchaseOrders = consumerPurchaseOrders;
	}

	public void setConsumerPayments(List<ConsumerPayment> consumerPayments) {
		this.consumerPayments = consumerPayments;
	}

	public void setShippings(List<Shipping> shippings) {
		this.shippings = shippings;
	}

	public void setGroupOrder(GroupOrder groupOrder) {
		this.groupOrder = groupOrder;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setBusiness(User business) {
		this.business = business;
	}

	public void setConsumer(User consumer) {
		this.consumer = consumer;
	}

	public void setCsStatus(CsStatus csStatus) {
		this.csStatus = csStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setConfirmed() {
		this.isConfirmed = true;
	}
}
