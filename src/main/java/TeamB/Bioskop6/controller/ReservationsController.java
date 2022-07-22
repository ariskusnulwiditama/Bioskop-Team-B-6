package TeamB.Bioskop6.controller;

import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.dto.ReservationRequestDTO;
import TeamB.Bioskop6.service.ReservationsServiceImpl;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class ReservationsController {
    @Autowired
    ReservationsServiceImpl reservationService;

    /***
     * Get All data from reservation table into list
     * @return
     */
    @GetMapping("/reservations")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getAllReservations() {
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
    public ResponseEntity<?> getReservationsById(@PathVariable Integer id) throws ResourceNotFoundException {
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
    public ResponseEntity<?> createReservations(ReservationRequestDTO reservationRequestDTO) throws ResourceAlreadyExistException {
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
    public ResponseEntity<?> updateReservations(ReservationRequestDTO reservationRequestDTO) throws ResourceNotFoundException {
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
}
