package geo.habit.web;

import geo.habit.Calendar;
import geo.habit.databse.Habit;
import geo.habit.databse.HabitDao;
import geo.habit.databse.HabitWeek;
import geo.habit.databse.HabitWeekDao;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static geo.habit.Calendar.format;
import static geo.habit.Calendar.parse;

@RestController
@RequestMapping("/m")
@AllArgsConstructor
public class MangeController {
    private final HabitDao habitDao;
    private final HabitWeekDao habitWeekDao;

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @GetMapping("/habitWeek/{habitId}")
    public List<HabitWeekItem> getHabitWeeks(@PathVariable String habitId) {
        List<HabitWeek> habitWeeks = habitWeekDao.findByIdHabitIdOrderByIdDay(habitId);
        if (habitWeeks.size() == 0) return Collections.EMPTY_LIST;
        Map<String, HabitWeek> habitWeekMap = habitWeeks.stream().collect(Collectors.toMap(hw -> hw.getId().getDay(), Function.identity()));
        LocalDate date = parse(habitWeeks.get(0).getId().getDay());
        List<HabitWeekItem> items = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String day = format(date.plusWeeks(i));
            if (habitWeekMap.containsKey(day)) items.add(new HabitWeekItem(habitWeekMap.get(day)));
            else items.add(new HabitWeekItem(day));
            if (day.equals(habitWeeks.get(habitWeeks.size() -1).getId().getDay())) break;
        }
        return items;
    }

    @GetMapping("/history/{habitId}")
    public Map<String, List<List<Boolean>>> getHistory(@PathVariable String habitId) {
        Map<String, List<List<Boolean>>> map = new LinkedHashMap<>();
        List<HabitWeekItem> habitWeeks = getHabitWeeks(habitId);
        habitWeeks.forEach(hw -> {
            String monthKey = Calendar.day2quarter(hw.getDay());
            if (!map.containsKey(monthKey)) map.put(monthKey, new ArrayList<>());
            List<List<Boolean>> grid = map.get(monthKey);
            for (boolean a : hw.getActivities()) {
                if (grid.size() == 0) grid.add(new ArrayList<>());
                List<Boolean> lastRow = grid.get(grid.size() - 1);
                if (lastRow.size() >= 14) {
                    lastRow = new ArrayList<>();
                    grid.add(lastRow);
                }
                lastRow.add(a);
            }
        });
        return map;
    }

    @PostMapping("/manual")
    public void postManual(@RequestBody Manual manual) {
        Habit habit = habitDao.findById(manual.getHabitId()).get();
        LocalDate date = parse(manual.getDayFrom());
        LocalDate dateTo = parse(manual.getDayTo());
        while (date.equals(dateTo) || date.isBefore(dateTo)) {
            HabitWeek habitWeek = new HabitWeek(new HabitWeek.ID(Calendar.format(date), manual.getHabitId()));
            for (int j = 0; j < habit.getWeekGoal(); j++) habitWeek.setActivity(j, true);
            habitWeek.setSuccess(true);
            habitWeekDao.save(habitWeek);
            date = date.plusWeeks(1);
        }
    }
}
