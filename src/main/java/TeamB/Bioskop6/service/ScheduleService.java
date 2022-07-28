package TeamB.Bioskop6.service;

import org.springframework.http.ResponseEntity;

import TeamB.Bioskop6.dto.ScheduleRequestDTO;
import TeamB.Bioskop6.dto.getScheduleByFIlmNameRequest;
import TeamB.Bioskop6.helper.ResourceNotFoundException;

public interface ScheduleService {
    ResponseEntity<?> getAllSchedule();
    ResponseEntity<?> getScheduleById(Integer id) throws ResourceNotFoundException;
    ResponseEntity<?> createSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceNotFoundException;
    ResponseEntity<?> updateSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceNotFoundException;
    ResponseEntity<?> deleteSchedule(Integer id) throws ResourceNotFoundException;
    ResponseEntity<?> findScheduleByFilmName(getScheduleByFIlmNameRequest filmName) throws ResourceNotFoundException;
}
