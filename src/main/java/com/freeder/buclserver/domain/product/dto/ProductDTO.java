package com.freeder.buclserver.domain.product.dto;

import com.freeder.buclserver.domain.productai.entity.ProductAi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class ProductDTO {
	private Long productId;
	private Long productCode;
	private String name;
	private String brandName;
	private String imagePath;
	private int salePrice;
	private int consumerPrice;
	private float reward;
	private boolean wished;
	private Object listCount;
	private Object suggestionCount;

}

