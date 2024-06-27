package com.freeder.buclserver.domain.productcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductCategoryDTO {
	private Long productCode;
	private String name;
	private String imagePath;
	private int salePrice;
	private int consumerPrice;
	private int reward;
	private float discountRate;
	private int totalReviewCount;
	private float averageRating;
	private boolean wished;
}
