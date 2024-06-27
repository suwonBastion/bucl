package com.freeder.buclserver.global.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ImageParsing {
	public String getThumbnailUrl(String imagePath) {
		List<String> imageList = getImageList(imagePath);
		return imageList.isEmpty() ? "" : imageList.get(0);
	}

	public List<String> getReviewUrl(String imagePath) {
		List<String> imageList = getImageList(imagePath);
		return imageList.stream().limit(3).collect(Collectors.toList());
	}

	public List<String> getImageList(String imagePath) {
		if (imagePath == null || imagePath.trim().isEmpty()) {
			return Collections.emptyList(); // 이미지 경로가 null이거나 공백이면 빈 리스트 반환
		}
		return List.of(imagePath.split("\\s+"));
	}
}