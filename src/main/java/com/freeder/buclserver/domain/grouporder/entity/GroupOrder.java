package com.freeder.buclserver.domain.grouporder.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "GROUP_ORDER")
public class GroupOrder extends TimestampMixin {
	@Id
	@Column(name = "group_order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Product product;

	@OneToMany(mappedBy = "groupOrder")
	private List<ConsumerOrder> consumerOrders = new ArrayList<>();

	@ColumnDefault("false")
	@Column(name = "is_ended")
	private boolean isEnded;
}
