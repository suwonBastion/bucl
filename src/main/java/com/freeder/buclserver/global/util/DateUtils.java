package com.freeder.buclserver.global.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public static long nowDate() {
        return System.currentTimeMillis() / 1000;
    }

    public static String nowDateString() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String convertDate(LocalDateTime time){
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static boolean isOneWeekPassed(long parameterTime, Long expireTime) {
        return nowDate() - parameterTime <= (expireTime == -1 ? (60 * 60 * 24 * 7) : expireTime);
    }
}
