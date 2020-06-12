package geo.habit;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_DATE;

public class Calendar {
    public static String today() {
        return format(LocalDate.now());
    }

    public static String startOfWeek(String day) {
        return format(parse(day).with(DayOfWeek.MONDAY));
    }

    public static String previous(String day) {
        return format(parse(day).minusDays(1));
    }

    public static String next(String day) {
        return format(parse(day).plusDays(1));
    }

    public static int indexOfWeek(String day) {
        return parse(day).getDayOfWeek().getValue() - 1;
    }

    private static LocalDate parse(String day) {
        return LocalDate.parse(day, ISO_DATE);
    }

    private static String format(LocalDate localDate) {
        return localDate.format(ISO_DATE);
    }
}
