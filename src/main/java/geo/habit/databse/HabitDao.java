package geo.habit.databse;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HabitDao extends CrudRepository<Habit, String> {
    List<Habit> findByOrderById();
}
