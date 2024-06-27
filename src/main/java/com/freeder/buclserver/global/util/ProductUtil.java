package com.freeder.buclserver.global.util;

import java.util.Arrays;
import java.util.List;

public class ProductUtil {
	public static List<String> getImageList(String imagePath) {
		return Arrays.asList(imagePath.split("\\s+/"));
	}
}
