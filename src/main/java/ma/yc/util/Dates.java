package ma.yc.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class Dates {
    private Dates() {}

    public static String formatLocalDateTime( LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
    public static long getDaysLeft(LocalDateTime deadline) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, deadline);
        return duration.toDays()+1;
    }
    public static long getDaysLeft(LocalDate startDay, LocalDate endDay) {
        return ChronoUnit.DAYS.between(startDay, endDay) + 1;
    }
}
