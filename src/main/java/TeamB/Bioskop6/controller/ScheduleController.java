package TeamB.Bioskop6.controller;

import TeamB.Bioskop6.dto.ScheduleRequestDTO;
import TeamB.Bioskop6.service.ScheduleServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ScheduleController {
    @Autowired
    ScheduleServiceImpl scheduleService;

    /***
     * Get All data from schedule table into list
     * @return
     */
    @GetMapping("/schedule")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getAllSchedule() {
        return scheduleService.getAllSchedule();
    }

    /***
     * Get a data from schedule table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/schedule/{id}")
    public ResponseEntity<?> getScheduleById(@PathVariable Integer id) throws ResourceNotFoundException {
        return scheduleService.getScheduleById(id);
    }

    /***
     * Create a new data in schedule table
     * @param scheduleRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/schedule/create")
    public ResponseEntity<?> createSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceAlreadyExistException {
        return scheduleService.createSchedule(scheduleRequestDTO);
    }

    /***
     * Update existing data in schedule table
     * @param scheduleRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/schedule/update")
    public ResponseEntity<?> updateSchedule(ScheduleRequestDTO scheduleRequestDTO) throws ResourceNotFoundException {
        return scheduleService.updateSchedule(scheduleRequestDTO);
    }

    /***
     * Delete existing data in schedule table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/schedule/delete/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Integer id) throws ResourceNotFoundException {
        return scheduleService.deleteSchedule(id);
    }
}
