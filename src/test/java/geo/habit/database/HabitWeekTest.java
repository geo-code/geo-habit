package geo.habit.database;

import geo.habit.databse.HabitWeek;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HabitWeekTest {
    @Test
    public void test() {
        HabitWeek habitWeek = new HabitWeek(null);
        assertArrayEquals(new boolean[]{false, false, false, false, false, false, false}, habitWeek.getActivities());
        habitWeek.setActivity(0, true);
        habitWeek.setActivity(3, true);
        habitWeek.setActivity(6, true);
        assertArrayEquals(new boolean[]{true, false, false, true, false, false, true}, habitWeek.getActivities());
        habitWeek.setActivity(3, false);
        habitWeek.setActivity(4, false);
        habitWeek.setActivity(5, true);
        assertArrayEquals(new boolean[]{true, false, false, false, false, true, true}, habitWeek.getActivities());
    }
}