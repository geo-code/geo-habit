package geo.habit;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static geo.habit.Calendar.parse;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

class ContinuesDaysTest {

    @Test
    void getContinuesWeeks() {
        ContinuesDays obj = new ContinuesDays(null);
        List<LocalDate> history = toLocalDate("2020-03-30", "2020-03-23", "2020-03-16", "2020-03-09", "2020-02-24", "2020-02-17", "2020-01-27");
        assertEquals(0, obj.getContinuesWeeks(parse("2020-04-13"), history));
        assertEquals(4, obj.getContinuesWeeks(parse("2020-04-06"), history));
        assertEquals(4, obj.getContinuesWeeks(parse("2020-03-30"), history));
        history = toLocalDate("2020-02-24", "2020-02-17", "2020-01-27");
        assertEquals(2, obj.getContinuesWeeks(parse("2020-03-02"), history));
        assertEquals(2, obj.getContinuesWeeks(parse("2020-02-24"), history));
    }

    private List<LocalDate> toLocalDate(String... days) {
        return stream(days).map(d -> parse(d)).collect(Collectors.toList());
    }
}