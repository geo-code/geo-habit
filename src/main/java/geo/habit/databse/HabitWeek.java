package geo.habit.databse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import static java.lang.Integer.bitCount;

@Getter
public class HabitWeek {
    @Id
    private ID id;
    private int activities;
    @Setter
    private boolean success;

    public HabitWeek(ID id) {
        this.id = id;
    }

    public boolean[] getActivities() {
        boolean[] array = new boolean[7];
        for (int i = 0; i < array.length; i++) array[i] = (activities & 1 << i) > 0;
        return array;
    }

    public boolean getActivity(int weekIndex) {
        return (activities & 1 << weekIndex) > 0;
    }

    public void setActivity(int weekIndex, boolean done) {
        int bit = 1 << weekIndex;
        activities = done ? activities | bit : activities & ~(bit);
    }

    public int getWeekAchieved() {
        return bitCount(activities);
    }

    @Getter
    @AllArgsConstructor
    public static class ID {
        private String day;
        private String habitId;
    }
}
