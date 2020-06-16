package geo.habit;

import geo.habit.databse.HabitDao;
import geo.habit.databse.HabitWeekDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringTest {
    @Autowired
    private HabitDao habitDao;
    @Autowired
    private HabitWeekDao habitWeekDao;
    @Autowired
    private ContinuesDays continuesDays;

    @Test
    public void test() throws Exception {
    }
}
