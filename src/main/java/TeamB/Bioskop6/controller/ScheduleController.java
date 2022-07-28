package TeamB.Bioskop6.controller;

import TeamB.Bioskop6.dto.ScheduleRequestDTO;
import TeamB.Bioskop6.dto.getScheduleByFIlmNameRequest;
import TeamB.Bioskop6.service.ReportPDFImpl;
import TeamB.Bioskop6.service.ScheduleServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;

import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Tag(name = "5. Schedule Controller")
@SecurityRequirement(name = "bearer-key")
public class ScheduleController {
    @Autowired
    ScheduleServiceImpl scheduleService;
    private HttpServletResponse response;
    private ReportPDFImpl reportReservation;

    /***
     * Get All data from schedule table into list
     * @return
     */
    @GetMapping("/schedule")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getAll() {
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
    public ResponseEntity<?> getById(@PathVariable Integer id) throws ResourceNotFoundException {
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
    public ResponseEntity<?> create(ScheduleRequestDTO scheduleRequestDTO) throws ResourceNotFoundException {
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
    public ResponseEntity<?> update(ScheduleRequestDTO scheduleRequestDTO) throws ResourceNotFoundException {
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
    public ResponseEntity<?> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        return scheduleService.deleteSchedule(id);
    }

    /***
     * Delete existing data in schedule table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/schedule/byfilmname")
    public ResponseEntity<?> findByFilmName(@RequestBody getScheduleByFIlmNameRequest filmName) throws ResourceNotFoundException {
        return scheduleService.findScheduleByFilmName(filmName);
    }

    /***
     * print report of schedule table
     * @return void
     * @throws Exception
     */
    @GetMapping("/schedules/print")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void printReport() throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"report.pdf\"");
        JasperPrint jasperPrint = this.reportReservation.generateJasperPrint();
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
