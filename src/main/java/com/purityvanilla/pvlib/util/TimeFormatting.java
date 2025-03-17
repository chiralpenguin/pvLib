package com.purityvanilla.pvlib.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TimeFormatting {

    public static String PrettyDurationString(long durationSeconds) {
        Duration duration = Duration.ofSeconds(durationSeconds);
        List<String> parts = getDurationStrings(duration);

        if (parts.isEmpty()) {
            return "0 seconds";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) {
                result.append(i == parts.size() - 1 ? " and " : ", ");
            }
            result.append(parts.get(i));
        }

        return result.toString();
    }

    private static List<String> getDurationStrings(Duration duration) {
        List<String> parts = new ArrayList<>();

        long days = duration.toDays();
        if (days > 0) {
            parts.add(days + (days == 1 ? " day" : " days"));
        }

        long hours = duration.toHoursPart();
        if (hours > 0) {
            parts.add(hours + (hours == 1 ? " hour" : " hours"));
        }

        long minutes = duration.toMinutesPart();
        if (minutes > 0) {
            parts.add(minutes + (minutes == 1 ? " minute" : " minutes"));
        }

        long seconds = duration.toSecondsPart();
        if (seconds > 0) {
            parts.add(seconds + (seconds == 1 ? " second" : " seconds"));
        }
        return parts;
    }
}
