package geo.habit;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_DATE;

public class Calendar {
    public static String today() {
        return format(LocalDate.now());
    }

    public static String startOfWeek(String day) {
        return format(startOfWeek(parse(day)));
    }

    public static LocalDate startOfWeek(LocalDate date) {
        return date.with(DayOfWeek.MONDAY);
    }

    public static String previous(String day) {
        return format(parse(day).minusDays(1));
    }

    public static String next(String day) {
        return format(parse(day).plusDays(1));
    }

    public static int indexOfWeek() {
        return indexOfWeek(today());
    }

    public static int indexOfWeek(String day) {
        return parse(day).getDayOfWeek().getValue() - 1;
    }

    public static LocalDate parse(String day) {
        return LocalDate.parse(day, ISO_DATE);
    }

    public static String format(LocalDate localDate) {
        return localDate.format(ISO_DATE);
    }

    public static String day2month(String day) {
        return parse(day).format(DateTimeFormatter.ofPattern("yyyy.M"));
    }

    public static String day2quarter(String day) {
        LocalDate date = parse(day);
        return date.getYear() + "\\" + (((date.getMonth().getValue() - 1) / 3) + 1);
    }
}
