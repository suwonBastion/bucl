package com.freeder.buclserver.domain.productreview.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewPreviewDTO {
	private String profilePath;
	private String nickname;
	private LocalDateTime reviewDate;
	private String productName;
	private String content;
	private float starRate; // doublce
	private String reviewImage;

}
