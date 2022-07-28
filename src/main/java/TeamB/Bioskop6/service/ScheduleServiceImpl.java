package TeamB.Bioskop6.service;

import TeamB.Bioskop6.controller.ScheduleController;
import TeamB.Bioskop6.dto.MessageResponse;
import TeamB.Bioskop6.dto.ScheduleRequestDTO;
import TeamB.Bioskop6.dto.ScheduleResponseDTO;
import TeamB.Bioskop6.entity.Film;
import TeamB.Bioskop6.entity.Schedule;
import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.repository.FilmRepository;
import TeamB.Bioskop6.repository.ScheduleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    
    private final HttpHeaders headers = new HttpHeaders();

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    FilmRepository filmRepository;

    @Value("${com.app.name}")
    String projectName;

    @Value("${com.app.team}")
    String projectTeam;

    @Override
    public ResponseEntity<?> getAllSchedule() {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            List<Schedule> scheduleList = scheduleRepository.findAll();
            List<ScheduleResponseDTO> scheduleResponseDTOs = new ArrayList<>();
            for (Schedule schedule : scheduleList){
                scheduleResponseDTOs.add(schedule.convertToResponse());
            }
            logger.info("--------------------------");
            logger.info("Get All Schedule Data " + scheduleList);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), scheduleResponseDTOs);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> getScheduleById(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule with ID " + id + " is not available!"));
            ScheduleResponseDTO scheduleResponseDTO = schedule.convertToResponse();
            logger.info("--------------------------");
            logger.info("Get All Schedule By ID " + schedule);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), scheduleResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> createSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Film film = filmRepository.findById(scheduleRequestDTO.getFilmId()).orElseThrow(() -> new ResourceNotFoundException("Film with ID " + scheduleRequestDTO.getScheduleId() + " is not available!"));
            Schedule schedule = Schedule.builder().film(film).Date(scheduleRequestDTO.getDate()).startTime(scheduleRequestDTO.getStartTime()).endTime(scheduleRequestDTO.getEndTime()).Price(scheduleRequestDTO.getPrice()).build();
            scheduleRepository.save(schedule);
            logger.info("--------------------------");
            logger.info("Create Schedule " + schedule);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse(null, HttpStatus.CREATED, headers, ZonedDateTime.now(), new MessageResponse("New schedule succesfully created!"));
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> updateSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Film film = filmRepository.findById(scheduleRequestDTO.getFilmId()).orElseThrow(() -> new ResourceNotFoundException("Film with ID " + scheduleRequestDTO.getScheduleId() + " is not available!"));
            Schedule schedule = Schedule.builder().film(film).Date(scheduleRequestDTO.getDate()).startTime(scheduleRequestDTO.getStartTime()).endTime(scheduleRequestDTO.getEndTime()).Price(scheduleRequestDTO.getPrice()).build();
            scheduleRepository.findById(schedule.getScheduleId()).orElseThrow(() -> new ResourceNotFoundException("Schedule with ID " + schedule.getScheduleId() + " is not available!"));
            scheduleRepository.save(schedule);
            logger.info("--------------------------");
            logger.info("Update Schedule " + schedule);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), new MessageResponse("Schedule succesfully created!"));
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> deleteSchedule(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule with ID " + id + " is not available!"));
            scheduleRepository.delete(schedule);
            logger.info("--------------------------");
            logger.info("Delete Schedule " + schedule);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("Schedule with ID " + id + " successfully deleted!", HttpStatus.OK, headers, ZonedDateTime.now(), null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
}
