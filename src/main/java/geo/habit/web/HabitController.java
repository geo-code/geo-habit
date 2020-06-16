package geo.habit.web;

import geo.habit.ContinuesDays;
import geo.habit.GeoHabitApplication;
import geo.habit.databse.Habit;
import geo.habit.databse.HabitDao;
import geo.habit.databse.HabitWeek;
import geo.habit.databse.HabitWeekDao;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static geo.habit.Calendar.indexOfWeek;
import static geo.habit.Calendar.startOfWeek;

@RestController
@AllArgsConstructor
public class HabitController {
    private final HabitDao habitDao;
    private final HabitWeekDao habitWeekDao;
    private final ContinuesDays continuesDays;

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
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/icon.txt")));
        List<String> icons = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) icons.add(line);
        return icons;
    }
}
