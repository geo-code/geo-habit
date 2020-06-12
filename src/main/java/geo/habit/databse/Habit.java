package geo.habit.databse;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Habit {
    @Id
    private String id;
    private String name;
    private int weekGoal;
    private int continuousWeeks;
}
