package com.freeder.buclserver.domain.productoption.entity;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.productoption.vo.OptionKey;
import com.freeder.buclserver.global.mixin.TimestampMixin;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Builder
@DynamicUpdate
@Table(name = "product_option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductOption extends TimestampMixin {
	@Id
	@Column(name = "production_option_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Product product;

	@Column(name = "sku_code", unique = true)
	private String skuCode;

	@ManyToOne
	@JoinColumn(name = "product_code", referencedColumnName = "product_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Product productCode;

	@Column(name = "option_key")
	@Enumerated(EnumType.STRING)
	private OptionKey optionKey;

	@Column(name = "option_value")
	private String optionValue;

	@ColumnDefault("0")
	@Column(name = "option_sequence")
	private int optionSequence;

	@Column(name = "product_qty")
	private Integer productQty;

	@Column(name = "max_order_qty")
	private int maxOrderQty;

	@Column(name = "min_order_qty")
	private int minOrderQty;

	@Column(name = "option_extra_amount")
	private int optionExtraAmount;

	@ColumnDefault("true")
	@Column(name = "is_exposed")
	private boolean isExposed;

	public Long getProductCode() {
		return product.getProductCode();
	}
	public void setIsExposed(boolean isExposed){
		this.isExposed = isExposed;
	}
}
