package geo.habit.databse;

import org.springframework.data.repository.CrudRepository;

public interface HabitDao extends CrudRepository<Habit, String> {
}
