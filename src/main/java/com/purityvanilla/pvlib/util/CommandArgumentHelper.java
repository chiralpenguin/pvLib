package com.purityvanilla.pvlib.util;

public class CommandArgumentHelper {

    public static long parseTimeSeconds(String timeString) {
        if (timeString == null || timeString.length() < 2) {
            return -1;
        }

        int length = timeString.length();
        char unit = timeString.toLowerCase().charAt(length - 1);
        String time = timeString.substring(0, length - 1);

        try {
            long value = Long.parseLong(time);

            return switch (unit) {
                case 's' -> value;
                case 'm' -> value * 60;
                case 'h' -> value * 60 * 60;
                case 'd' -> value * 60 * 60 * 24;
                default -> -1;
            };
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
