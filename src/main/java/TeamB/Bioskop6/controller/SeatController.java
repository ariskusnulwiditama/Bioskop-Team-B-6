package TeamB.Bioskop6.controller;

import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.dto.SeatRequestDTO;
import TeamB.Bioskop6.service.SeatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Tag(name = "4. Seat Controller")
@SecurityRequirement(name = "bearer-key")
public class SeatController {
    @Autowired
    private final SeatService seatService;

    /***
     * Get All data from seat table into list
     * @return
     */
    @GetMapping("/seats")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getAll() {
        return seatService.getAllSeat();
    }

    /***
     * Get a data from seat table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/seat/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Integer id) throws ResourceNotFoundException {
        return seatService.getSeatById(id);
    }

    /***
     * Create a new data in seat table
     * @param seatRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     */
    @PostMapping("/seats")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody SeatRequestDTO seatRequestDTO) throws ResourceAlreadyExistException {
        return seatService.createSeat(seatRequestDTO);
    }

    /***
     * Update existing data in seat table
     * @param id
     * @param seatRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PostMapping("/seat/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody SeatRequestDTO seatRequestDTO) throws ResourceNotFoundException {
        return seatService.updateSeat(seatRequestDTO);
    }

    /***
     * Delete existing data in seat table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     * @throws DataNotFoundException
     */
    @DeleteMapping("/seat/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        return seatService.deleteSeat(id);
    }
}
