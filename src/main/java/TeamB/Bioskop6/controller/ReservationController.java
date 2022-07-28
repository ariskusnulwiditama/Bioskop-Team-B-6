package TeamB.Bioskop6.controller;

import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.dto.ReservationRequestDTO;
import TeamB.Bioskop6.service.ReportPDFImpl;
import TeamB.Bioskop6.service.ReservationsServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Tag(name = "6. Reservation Controller")
@SecurityRequirement(name = "bearer-key")
public class ReservationController {
    @Autowired
    ReservationsServiceImpl reservationService;
    private HttpServletResponse response;
    private ReportPDFImpl reportReservation;

    /***
     * Get All data from reservation table into list
     * @return
     */
    @GetMapping("/reservations")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getAll() {
        return reservationService.getAllReservation();
    }

    /***
     * Get a data from reservation table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/reservation/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Integer id) throws ResourceNotFoundException {
        return reservationService.getReservationById(id);
    }

    /***
     * Create a new data in reservation table
     * @param reservationRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     */
    @PostMapping("/reservation/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> create(ReservationRequestDTO reservationRequestDTO) throws ResourceNotFoundException {
        return reservationService.createReservation(reservationRequestDTO);
    }

    /***
     * Update existing data in reservation table   
     * @param reservationRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping("/reservation/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(ReservationRequestDTO reservationRequestDTO) throws ResourceNotFoundException {
        return reservationService.updateReservation(reservationRequestDTO);
    }

    /***
     * Delete existing data in reservation table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/reservation/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteReservations(@PathVariable Integer id) throws ResourceNotFoundException {
        return reservationService.deleteReservation(id);
    }

    /***
     * print report of reservation table
     * @return void
     * @throws Exception
     */
    @GetMapping("/reservations/print")
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
