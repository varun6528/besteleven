package com.verteil.besteleven.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class TimeConversionUtil {

    private TimeConversionUtil() {

    }

    public static boolean checkDatePassed(LocalDate matchDate, String time) {
        LocalDateTime timeToCheck = LocalDateTime.now();
        LocalDateTime matchTime = LocalDateTime.of(matchDate, LocalTime.parse(time));
        return matchTime.isBefore(timeToCheck);
    }
}
