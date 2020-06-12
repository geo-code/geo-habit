package geo.habit.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ApiController {
    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @GetMapping("/items/{day}")
    public List<HabitItem> getHabitItem(@PathVariable String day) {
        return Arrays.asList(example1(), example2(), example3());
    }

    private HabitItem example1() {
        HabitItem item = new HabitItem();
        item.setHabitId("4kR");
        item.setName("4km Running");
        item.setContinuousDays(65);
        item.setActivities(new Boolean[]{false, false, false, false, true, true, null});
        item.setWeekGoal(3);
        item.setWeekAchieved(2);
        item.setChecked(false);
        item.setIcon("runner");
        return item;
    }

    private HabitItem example2() {
        HabitItem item = new HabitItem();
        item.setHabitId("8mM");
        item.setName("8min Mindfulness");
        item.setContinuousDays(312);
        item.setActivities(new Boolean[]{false, false, false, true, true, true, true});
        item.setWeekGoal(5);
        item.setWeekAchieved(4);
        item.setChecked(true);
        item.setIcon("meditate");
        return item;
    }

    private HabitItem example3() {
        HabitItem item = new HabitItem();
        item.setHabitId("20mO");
        item.setName("20min Original novel");
        item.setContinuousDays(312);
        item.setActivities(new Boolean[]{false, true, false, true, null, null, null});
        item.setWeekGoal(3);
        item.setWeekAchieved(1);
        item.setChecked(false);
        item.setIcon("catalog");
        return item;
    }
}
