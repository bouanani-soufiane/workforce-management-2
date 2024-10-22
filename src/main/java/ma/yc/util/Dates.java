package ma.yc.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class Dates {
    private Dates () {
    }

    public static String formatLocalDateTime ( LocalDateTime localDateTime, String pattern ) {
        return formatDateTime(localDateTime, DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatDateTime ( LocalDateTime dateTime, DateTimeFormatter formatter ) {
        return dateTime.format(formatter);
    }

    public static long getDaysLeft ( LocalDateTime deadline ) {
        return getDaysLeftImpl(LocalDateTime::now, deadline);
    }

    public static long getDaysLeft ( LocalDate startDay, LocalDate endDay ) {
        return getDaysLeftImpl(LocalDate::now, endDay.minusDays(startDay.getDayOfYear() - 1));
    }

    private static long getDaysLeftImpl ( Supplier<LocalDate> currentDateSupplier, LocalDate endDate ) {
        return ChronoUnit.DAYS.between(currentDateSupplier.get(), endDate) + 1;
    }

    private static long getDaysLeftImpl ( Supplier<LocalDateTime> currentDateTimeSupplier, LocalDateTime endDateTime ) {
        Duration duration = Duration.between(currentDateTimeSupplier.get(), endDateTime);
        return duration.toDays() + 1;
    }

    @FunctionalInterface
    private interface Supplier<T> {
        T get ();
    }
}