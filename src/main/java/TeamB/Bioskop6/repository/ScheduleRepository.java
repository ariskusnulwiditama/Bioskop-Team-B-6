package TeamB.Bioskop6.repository;

import TeamB.Bioskop6.entity.Schedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("select s from Schedule s where s.film.filmName = :filmName")
    List<Schedule> findScheduleByFilmName(@Param("filmName") String film);
}
