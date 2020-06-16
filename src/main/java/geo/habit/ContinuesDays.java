package geo.habit;

import geo.habit.databse.HabitWeekDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static geo.habit.Calendar.parse;
import static geo.habit.Calendar.startOfWeek;

@Service
@AllArgsConstructor
public class ContinuesDays {
    private final HabitWeekDao habitWeekDao;

    public int calc(String habitId, String startDay) {
        List<LocalDate> history = habitWeekDao.findBySuccessTrueAndIdHabitIdAndIdDayIsLessThanEqualOrderByIdDayDesc(habitId, startDay).stream()
                .map(hw -> parse(hw.getId().getDay())).collect(Collectors.toList());
        return getContinuesWeeks(startOfWeek(parse(startDay)), history) * 7;
    }

    int getContinuesWeeks(LocalDate startDate, List<LocalDate> history) {
        int continuesWeeks = 0;
        LocalDate date = startDate;
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).equals(date)) {
                continuesWeeks++;
            } else if (i == 0) {
                date = date.minusWeeks(1);
                if (history.get(i).equals(date)) continuesWeeks++;
            } else {
                break;
            }
            date = date.minusWeeks(1);
        }
        return continuesWeeks;
    }
}
