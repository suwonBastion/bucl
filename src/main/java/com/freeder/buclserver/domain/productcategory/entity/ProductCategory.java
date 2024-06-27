package com.freeder.buclserver.domain.productcategory.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "product_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductCategory extends TimestampMixin {
	@Id
	@Column(name = "product_category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "product_desc")
	private String productDesc;

	@OneToMany(mappedBy = "productCategory")
	private List<Product> products = new ArrayList<>();
}
