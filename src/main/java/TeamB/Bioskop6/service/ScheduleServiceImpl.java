package TeamB.Bioskop6.service;

import TeamB.Bioskop6.dto.ScheduleRequestDTO;
import TeamB.Bioskop6.dto.ScheduleResponseDTO;
import TeamB.Bioskop6.entity.Schedule;
import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.repository.ScheduleRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    
    private final HttpHeaders headers = new HttpHeaders();

    @Override
    public ResponseEntity<?> getAllSchedule() {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            List<Schedule> scheduleList = scheduleRepository.findAll();
            List<ScheduleResponseDTO> scheduleResponseDTOs = new ArrayList<>();
            for (Schedule schedule : scheduleList){
                scheduleResponseDTOs.add(schedule.convertToResponse());
            }
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), scheduleResponseDTOs);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> getScheduleById(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule with ID " + id + " is not available!"));
            ScheduleResponseDTO scheduleResponseDTO = schedule.convertToResponse();
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), scheduleResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> createSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceAlreadyExistException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Schedule schedule = scheduleRequestDTO.convertToEntity();
            scheduleRepository.findById(schedule.getScheduleId()).orElseThrow(() -> new ResourceAlreadyExistException("Schedule with ID " + schedule.getScheduleId() + " is already exist!"));
            Schedule newSchedule = scheduleRepository.save(schedule);
            return ResponseHandler.generateResponse("Schedule with ID " + newSchedule.getScheduleId() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), newSchedule);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> updateSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Schedule schedule = scheduleRequestDTO.convertToEntity();
            scheduleRepository.findById(schedule.getScheduleId()).orElseThrow(() -> new ResourceNotFoundException("Schedule with ID " + schedule.getScheduleId() + " is not available!"));
            Schedule updatedSchedule = scheduleRepository.save(schedule);
            return ResponseHandler.generateResponse("Schedule with ID " + updatedSchedule.getScheduleId() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), updatedSchedule);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> deleteSchedule(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule with ID " + id + " is not available!"));
            scheduleRepository.delete(schedule);
            return ResponseHandler.generateResponse("Schedule with ID " + id + " successfully deleted!", HttpStatus.OK, headers, ZonedDateTime.now(), null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
}
