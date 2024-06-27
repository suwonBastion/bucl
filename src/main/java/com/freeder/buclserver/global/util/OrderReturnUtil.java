package com.freeder.buclserver.global.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

public class OrderReturnUtil {
	public static Long getReturnId() {
		LocalDateTime currentLocalDateTime = LocalDateTime.now();
		Random random = new Random();

		long currentTimeStamp = Timestamp.valueOf(currentLocalDateTime).getTime();
		long randomNum = random.nextLong(10000);

		return Long.valueOf(String.valueOf(currentTimeStamp) + String.valueOf(randomNum));
	}

	public static LocalDateTime getCompletedAt() {
		return LocalDateTime.now();
	}
}
