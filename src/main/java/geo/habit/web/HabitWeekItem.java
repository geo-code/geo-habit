package geo.habit.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import geo.habit.databse.HabitWeek;
import lombok.Getter;

@Getter
public class HabitWeekItem {
    private String day;
    @JsonIgnore
    private boolean[] activities = new boolean[7];
    private boolean success;

    public HabitWeekItem(String day) {
        this.day = day;
    }

    public HabitWeekItem(HabitWeek habitWeek) {
        day = habitWeek.getId().getDay();
        activities = habitWeek.getActivities();
        success = habitWeek.isSuccess();
    }
}
