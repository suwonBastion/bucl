package com.freeder.buclserver.domain.product.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.freeder.buclserver.domain.productai.entity.ProductAi;
import com.freeder.buclserver.domain.productreview.dto.ReviewPreviewDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ProductDetailDTO {
	private Long productCode;
	private String name;
	private String brandName;
	private int salePrice;
	private int consumerPrice;
	private float discountRate;
	private float averageRating;
	private LocalDateTime createdAt;
	private int totalReviewCount;
	private List<String> imagePaths;
	private List<String> detailImagePaths;
	private List<ReviewPreviewDTO> reviewPreviews;
	private boolean wished;
	private ProductAiDto productAiDatas;

	@Getter
	@AllArgsConstructor
	@Setter
	@NoArgsConstructor
	public static class ProductAiDto {
		private Long productAiId;
		private Float average;
		private String mdComment;
		private String summary;
		private Long totalCnt;
	}

	public static ProductAiDto convertDto(Optional<ProductAi> productAi){
		return productAi.map(ai -> new ProductAiDto(
				null,
				ai.getAverage(),
				ai.getMdComment(),
				ai.getSummary(),
				ai.getTotalCnt()
		)).orElseGet(ProductAiDto::new);
	}
}
