package TeamB.Bioskop6.service;

import TeamB.Bioskop6.entity.Schedule;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
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

    public Schedule getOneSchedule(Integer id) throws ResourceNotFoundException {
        Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(id);

        if(optionalSchedule.isEmpty()){
            throw new ResourceNotFoundException("Schedule is Not Available");
        }

        return optionalSchedule.get();
    }

    public Schedule updateScheduleById(Schedule schedule) throws ResourceNotFoundException {
        this.getOneSchedule(schedule.getScheduleId());

        return this.scheduleRepository.save(schedule);
    }

    public void deleteScheduleById(Schedule schedule) throws ResourceNotFoundException {
        Optional<Schedule> deletedSchedule = this.scheduleRepository.findById(schedule.getScheduleId());
        if(deletedSchedule.isEmpty()){
            throw new ResourceNotFoundException("Schedule is Not Available");
        }

        this.scheduleRepository.delete(deletedSchedule.get());
    }

    public void deleteSchedule(Integer id) {
    }

    public Schedule updateSchedule(Schedule schedule) throws ResourceNotFoundException {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(schedule.getScheduleId());
        if (optionalSchedule.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + schedule.getScheduleId() + " is not available!");
        }
        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return updatedSchedule;
    }

    public Schedule getScheduleById(Integer id) throws ResourceNotFoundException {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            throw new ResourceNotFoundException("Schedule with ID " + id + " is not available!");
        }
        return optionalSchedule.get();
    }
}
