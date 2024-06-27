package com.freeder.buclserver.domain.productreview.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDTO {
	private Long productCode;
	private String profilePath;
	private String nickname;
	private LocalDateTime createdAt;
	private double starRate;
	private String selectedOption;
	private List<String> reviewImages;
	private String reviewText;
}
