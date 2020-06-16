package geo.habit.web;

import geo.habit.Calendar;
import geo.habit.ContinuesDays;
import geo.habit.databse.Habit;
import geo.habit.databse.HabitDao;
import geo.habit.databse.HabitWeek;
import geo.habit.databse.HabitWeekDao;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static geo.habit.Calendar.*;

@RestController
@AllArgsConstructor
public class ApiController {
    private final HabitDao habitDao;
    private final HabitWeekDao habitWeekDao;
    private final ContinuesDays continuesDays;

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @GetMapping("/habit")
    public List<Habit> getHabits() {
        return habitDao.findByOrderById();
    }

    @GetMapping("/habit/{id}/exist")
    public Boolean getHabit(@PathVariable String id) {
        Optional<Habit> habitFind = habitDao.findById(id);
        if (habitFind.isPresent()) return true;
        else return false;
    }

    @PostMapping("/habit")
    public void postHabit(@RequestBody Habit habit) {
        habitDao.save(habit);
    }

    @DeleteMapping("/habit/{id}")
    public void deleteHabit(@PathVariable String id) {
        habitDao.deleteById(id);
    }

    @GetMapping("/habitWeek/{habitId}")
    public List<HabitWeekItem> getHabitWeeks(@PathVariable String habitId) {
        List<HabitWeek> habitWeeks = habitWeekDao.findByIdHabitIdOrderByIdDayDesc(habitId);
        if (habitWeeks.size() == 0) return Collections.EMPTY_LIST;
        Map<String, HabitWeek> habitWeekMap = habitWeeks.stream().collect(Collectors.toMap(hw -> hw.getId().getDay(), Function.identity()));
        LocalDate date = parse(habitWeeks.get(0).getId().getDay());
        List<HabitWeekItem> items = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String day = format(date.minusWeeks(i));
            if (habitWeekMap.containsKey(day)) items.add(new HabitWeekItem(habitWeekMap.get(day)));
            else items.add(new HabitWeekItem(day));
            if (day.equals(habitWeeks.get(habitWeeks.size() -1).getId().getDay())) break;
        }
        return items;
    }

    @GetMapping("/item/{day}")
    public List<HabitItem> getHabitItem(@PathVariable String day) {
        List<HabitItem> items = new ArrayList<>();
        habitDao.findByOrderById().forEach(habit -> {
            HabitItem item = new HabitItem(habit);
            Optional<HabitWeek> habitWeekFind = habitWeekDao.findById(new HabitWeek.ID(startOfWeek(day), habit.getId()));
            if (habitWeekFind.isPresent()) {
                HabitWeek habitWeek = habitWeekFind.get();
                boolean[] activities = habitWeek.getActivities();
                for (int i = 0; i < 7; i++) {
                    if (activities[i]) {
                        item.setActivity(i, true);
                    } else if (i < indexOfWeek(day)) {
                        item.setActivity(i, false);
                    }
                }
                item.setWeekAchieved(habitWeek.getWeekAchieved());
                if (activities[indexOfWeek(day)]) item.setChecked(true);
            }
            item.setContinuousDays(continuesDays.calc(habit.getId(), day));
            items.add(item);
        });
        return items;
    }

    @PostMapping("/item/{day}/{habitId}")
    public void toggleHabitItem(@PathVariable String day, @PathVariable String habitId) {
        Habit habit = habitDao.findById(habitId).get();
        HabitWeek habitWeek;
        Optional<HabitWeek> habitWeekFind = habitWeekDao.findById(new HabitWeek.ID(startOfWeek(day), habitId));
        if (habitWeekFind.isPresent()) habitWeek = habitWeekFind.get();
        else habitWeek = new HabitWeek(new HabitWeek.ID(startOfWeek(day), habitId));
        int weekIndex = indexOfWeek(day);
        habitWeek.setActivity(weekIndex, !habitWeek.getActivity(weekIndex));
        habitWeek.setSuccess(habitWeek.getWeekAchieved() >= habit.getWeekGoal());
        habitWeekDao.save(habitWeek);
    }

    @GetMapping("/icon")
    public List<String> getIcons() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/static/adioma/black")));
        List<String> icons = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) icons.add(line);
        return icons;
    }
}
