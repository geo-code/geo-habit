package geo.habit.databse;

import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.bitCount;

public class HabitWeek {
    @Id
    private String id;
    private Map<String, Integer> activities;

    public HabitWeek(String id) {
        this.id = id;
    }

    public boolean[] getActivities(String habitId) {
        boolean[] array = new boolean[7];
        if (activities != null && activities.containsKey(habitId)) {
            int bits = activities.get(habitId);
            for (int i = 0; i < array.length; i++) array[i] = (bits & 1 << i) > 0;
        }
        return array;
    }

    public void setActivity(String habitId, int weekIndex, boolean done) {
        if (activities == null) activities = new HashMap<>();
        if (!activities.containsKey(habitId)) activities.put(habitId, 0);
        int bits = activities.get(habitId);
        int bit = 1 << weekIndex;
        activities.put(habitId, done ? bits | bit : bits & ~(bit));
    }

    public int getCount(String habitId) {
        if (activities == null || !activities.containsKey(habitId)) return 0;
        return bitCount(activities.get(habitId));

    }
}
