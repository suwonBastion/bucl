package com.freeder.buclserver.domain.productreview.dto;

import com.freeder.buclserver.domain.productreview.vo.StarRate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDTO {
	private String reviewContent;
	private StarRate starRate;
}
