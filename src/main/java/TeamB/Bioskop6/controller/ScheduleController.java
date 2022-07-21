package TeamB.Bioskop6.controller;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import TeamB.Bioskop6.dto.ScheduleRequestDTO;
import TeamB.Bioskop6.dto.ScheduleResponseDTO;
import TeamB.Bioskop6.entity.Schedule;
import TeamB.Bioskop6.service.ScheduleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.service.ScheduleService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /***
     * Get All data from schedule table into list
     * @return
     */
    @GetMapping("/schedule")
    public ResponseEntity<Object> getAllSchedule() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            List<Schedule> scheduleList = this.scheduleService.getAllSchedule();
            List<ScheduleResponseDTO> scheduleResponseDTOs = new ArrayList<>();
            for (Schedule schedule : scheduleList){
                scheduleResponseDTOs.add(schedule.convertToResponse());
            }
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), scheduleResponseDTOs);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    /***
     * Get a data from schedule table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/schedule/{id}")
    public ResponseEntity<Object> getScheduleById(@PathVariable Integer id) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Schedule schedule = scheduleService.getScheduleById(id);
            ScheduleResponseDTO scheduleResponseDTO = schedule.convertToResponse();
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), scheduleResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    /***
     * Create a new data in schedule table
     * @param scheduleRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     */
    @PostMapping("/schedule/create")
    public ResponseEntity<Object> createSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceAlreadyExistException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        Schedule schedule = scheduleRequestDTO.convertToEntity();
        Schedule newSchedule = this.scheduleService.createSchedule(schedule);
        return ResponseHandler.generateResponse("Schedule with ID " + newSchedule.getScheduleId() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), newSchedule);
    }

    /***
     * Update existing data in schedule table
     * @param scheduleRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping("/schedule/update")
    public ResponseEntity<Object> updateSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Schedule schedule = scheduleRequestDTO.convertToEntity();
            Schedule updatedSchedule = scheduleService.updateSchedule(schedule);
            return ResponseHandler.generateResponse("Schedule with ID " + updatedSchedule.getScheduleId() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), updatedSchedule);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    /***
     * Delete existing data in schedule table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/schedule/delete/{id}")
    public ResponseEntity<Object> deleteSchedule(@PathVariable Integer id) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            scheduleService.deleteSchedule(id);
            return ResponseHandler.generateResponse("Schedule with ID " + id + " successfully deleted!", HttpStatus.OK, headers, ZonedDateTime.now(), null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
}
