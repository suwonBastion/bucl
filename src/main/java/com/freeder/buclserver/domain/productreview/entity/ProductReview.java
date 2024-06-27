package com.freeder.buclserver.domain.productreview.entity;

import jakarta.persistence.*;

import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.productreview.vo.StarRate;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product_review")
public class ProductReview extends TimestampMixin {
	@Id
	@Column(name = "product_review_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Product product;

	@Column(length = 300)
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(name = "star_rate")
	private StarRate starRate;

	@Column(name = "image_path")
	private String imagePath;

	@Column(name = "selected_option")
	private String selectedOption;

	@Column(name = "product_code")
	private Long productCode;

	public Product getProduct() {
		return product;
	}

	public Long getProductCode() {
		return product.getProductCode();
	}
}
