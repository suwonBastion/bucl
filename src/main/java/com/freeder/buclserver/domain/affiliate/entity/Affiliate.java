package com.freeder.buclserver.domain.affiliate.entity;



import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.global.mixin.TimestampMixin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "affiliate")
public class Affiliate extends TimestampMixin {
	@Id
	@Column(name = "affiliate_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@ManyToOne
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Product product;

	@Column(name = "affiliate_url")
	private String affiliateUrl;
}
