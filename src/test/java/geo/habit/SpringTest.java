package geo.habit;

import geo.habit.databse.HabitDao;
import geo.habit.databse.HabitWeek;
import geo.habit.databse.HabitWeekDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
public class SpringTest {
    @Autowired
    private HabitDao habitDao;
    @Autowired
    private HabitWeekDao habitWeekDao;

    @Test
    public void test() throws Exception {
        HabitWeek habitWeek = new HabitWeek(Calendar.startOfWeek(Calendar.today()));
        habitWeek.setActivity("4kR", 2, true);
        habitWeek.setActivity("4kR", 5, true);
        habitWeek.setActivity("6mM", 0, true);
        habitWeek.setActivity("6mM", 6, true);
        habitWeekDao.save(habitWeek);

        HabitWeek habitWeek1 = habitWeekDao.findById(Calendar.startOfWeek("2020-06-10")).get();
        System.out.println(Arrays.toString(habitWeek1.getActivities("4kR")));
        System.out.println(Arrays.toString(habitWeek1.getActivities("6mM")));

//        Habit habit = new Habit();
//        habit.setId("4KR");
//        habit.setName("4km Running");
//        habit.setWeekGoal(3);
//        habitDao.save(habit);

//        HabitWeek habitWeek = new HabitWeek();
//        habitWeek.setId("20200608");
//        Map map = new HashMap();
//        map.put("6mM", 15);
//        habitWeek.setActivities(map);
//        habitWeekDao.save(habitWeek);

//        HabitWeek habitWeek = habitWeekDao.findById("20200601").get();
//        habitWeek.getActivities().put("4kR", (byte) 10);
//        habitWeekDao.save(habitWeek);

    }
}
