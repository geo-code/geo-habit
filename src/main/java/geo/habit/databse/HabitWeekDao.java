package geo.habit.databse;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HabitWeekDao extends CrudRepository<HabitWeek, HabitWeek.ID> {
    List<HabitWeek> findBySuccessTrueAndIdHabitIdAndIdDayIsLessThanEqualOrderByIdDayDesc(String habitId, String day);

    List<HabitWeek> findByIdHabitIdOrderByIdDayDesc(String habitId);
}
