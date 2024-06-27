package com.freeder.buclserver.app.orders.dto;

import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.global.util.ProductUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProductDto {
	private String productName; // 제품 이름
	private String productBrandName; // 브랜드 명
	private List<String> productImagePathList; // 제품 이미지 url;

	public static ProductDto from(Product product) {
		return new ProductDto(
			product.getName(),
			product.getBrandName(),
			ProductUtil.getImageList(product.getImagePath())
		);
	}
}
