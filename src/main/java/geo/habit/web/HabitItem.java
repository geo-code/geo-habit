package geo.habit.web;

import lombok.Data;

@Data
public class HabitItem {
    private String habitId;
    private String name;
    private int continuousDays;
    private Boolean[] activities;
    private int weekGoal;
    private int weekAchieved;
    private boolean checked;
    private String icon;
}
