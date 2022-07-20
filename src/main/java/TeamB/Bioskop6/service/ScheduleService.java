package TeamB.Bioskop6.service;

import TeamB.Bioskop6.Helpers.DataNotFoundException;
import TeamB.Bioskop6.entity.Schedule;
import TeamB.Bioskop6.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> getAllSchedule(){
        return this.scheduleRepository.findAll();
    }

    public Schedule createSchedule (Schedule schedule){
        return this.scheduleRepository.save(schedule);
    }

    public Schedule getOneSchedule(Integer id) throws DataNotFoundException {
        Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(id);

        if(optionalSchedule.isEmpty()){
            throw new DataNotFoundException("Schedule is Not Available");
        }

        return optionalSchedule.get();
    }

    public Schedule updateScheduleById(Schedule schedule) throws DataNotFoundException {
        this.getOneSchedule(schedule.getSchaduleId());

        return this.scheduleRepository.save(schedule);
    }

    public void deleteScheduleById(Schedule schedule) throws DataNotFoundException {
        Optional<Schedule> deletedSchedule = this.scheduleRepository.findById(schedule.getSchaduleId());
        if(deletedSchedule.isEmpty()){
            throw new DataNotFoundException("Schedule is Not Available");
        }

        this.scheduleRepository.delete(deletedSchedule.get());
    }

}
