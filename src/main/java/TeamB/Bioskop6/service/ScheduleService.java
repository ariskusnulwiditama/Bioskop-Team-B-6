package TeamB.Bioskop6.service;

import org.springframework.http.ResponseEntity;

import TeamB.Bioskop6.dto.ScheduleRequestDTO;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;

public interface ScheduleService {
    ResponseEntity<?> getAllSchedule();
    ResponseEntity<?> getScheduleById(Integer id) throws ResourceNotFoundException;
    ResponseEntity<?> createSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceAlreadyExistException;
    ResponseEntity<?> updateSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceNotFoundException;
    ResponseEntity<?> deleteSchedule(Integer id) throws ResourceNotFoundException;
}
