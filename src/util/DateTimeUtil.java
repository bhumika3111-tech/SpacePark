
package util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    // GET CURRENT DATE AND TIME
    public static LocalDateTime getCurrentDateTime() {

        return LocalDateTime.now();
    }

    // FORMAT DATE AND TIME
    public static String formatDateTime(LocalDateTime dateTime) {

        if (dateTime == null) {
            return "Not Available";
        }

        return dateTime.format(FORMATTER);
    }

    // PARSE DATE AND TIME
    public static LocalDateTime parseDateTime(String dateTime) {

        if (dateTime == null || dateTime.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Date and time cannot be empty."
            );
        }

        try {

            return LocalDateTime.parse(
                    dateTime.trim(),
                    FORMATTER
            );

        } catch (DateTimeParseException e) {

            throw new IllegalArgumentException(
                    "Invalid date format. Use dd-MM-yyyy HH:mm:ss."
            );
        }
    }

    // CALCULATE DURATION IN MINUTES
    public static long calculateDurationInMinutes(
            LocalDateTime startTime,
            LocalDateTime endTime) {

        if (startTime == null || endTime == null) {
            return 0;
        }

        return Duration.between(
                startTime,
                endTime
        ).toMinutes();
    }

    // CALCULATE DURATION IN HOURS
    public static long calculateDurationInHours(
            LocalDateTime startTime,
            LocalDateTime endTime) {

        if (startTime == null || endTime == null) {
            return 0;
        }

        return Duration.between(
                startTime,
                endTime
        ).toHours();
    }

    // DISPLAY CURRENT DATE AND TIME
    public static void displayCurrentDateTime() {

        System.out.println(
                "Current Date and Time: "
                + formatDateTime(getCurrentDateTime())
        );
    }
}
