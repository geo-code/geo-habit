package geo.habit.web;

import geo.habit.databse.Habit;
import lombok.Getter;
import lombok.Setter;

@Getter
public class HabitItem {
    private String habitId;
    private String name;
    private int weekGoal;
    private String icon;
    private Boolean[] activities = new Boolean[7];
    @Setter
    private int weekAchieved;
    @Setter
    private int continuousDays;
    @Setter
    private boolean checked = false;
    private boolean show;

    public HabitItem(Habit habit) {
        habitId = habit.getId();
        name = habit.getName();
        weekGoal = habit.getWeekGoal();
        icon = habit.getIcon();
        show = habit.isShow();
    }

    public void setActivity(int index, boolean activity) {
        activities[index] = activity;
    }
}
