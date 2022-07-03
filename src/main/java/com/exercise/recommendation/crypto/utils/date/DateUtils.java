package com.exercise.recommendation.crypto.utils.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateUtils {

    public static LocalDateTime parseDateFromTimeStamp(Long timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC);
        }
    }
}
