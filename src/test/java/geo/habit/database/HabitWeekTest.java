package geo.habit.database;

import geo.habit.databse.HabitWeek;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HabitWeekTest {
    @Test
    public void test() {
        HabitWeek habitWeek = new HabitWeek("");
        assertArrayEquals(new boolean[]{false, false, false, false, false, false, false}, habitWeek.getActivities("xxx"));
        habitWeek.setActivity("aaa", 0, true);
        habitWeek.setActivity("aaa", 3, true);
        habitWeek.setActivity("aaa", 6, true);
        assertArrayEquals(new boolean[]{true, false, false, true, false, false, true}, habitWeek.getActivities("aaa"));
        habitWeek.setActivity("aaa", 3, false);
        habitWeek.setActivity("aaa", 4, false);
        habitWeek.setActivity("aaa", 5, true);
        assertArrayEquals(new boolean[]{true, false, false, false, false, true, true}, habitWeek.getActivities("aaa"));
    }
}