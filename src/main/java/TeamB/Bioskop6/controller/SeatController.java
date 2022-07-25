package TeamB.Bioskop6.controller;

import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.helper.DataNotFoundException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.dto.SeatRequestDTO;
import TeamB.Bioskop6.dto.SeatResponseDTO;
import TeamB.Bioskop6.entity.Seat;
import TeamB.Bioskop6.service.SeatServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class SeatController {
    @Autowired
    private final SeatServiceImpl seatServiceImpl;

    /***
     * Get All data from seat table into list
     * @return ResponseEntity<Object>
     * @throws ResourceNotFoundException
     */
    @GetMapping("/seats")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getAllSeats() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop API B");
        try {
            List<Seat> seats = this.seatServiceImpl.getAllSeat();
            List<SeatResponseDTO> seatResponseDTO = new ArrayList<>();
            for(Seat seat : seats){
                seatResponseDTO.add(seat.convertToResponse());
            }
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), seatResponseDTO);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    /***
     * Get a data from seat table by its ID
     * @return ResponseEntity<Object>
     * @throws ResourceNotFoundException
     */
    @PostMapping("/seats")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> createSeat(@RequestBody SeatRequestDTO seatRequestDTO) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop API B");

        try {
            Seat seat = seatRequestDTO.convertToEntity();
            Seat newSeat = this.seatServiceImpl.createSeat(seat);
            return ResponseHandler.generateResponse(null, HttpStatus.CREATED, headers, ZonedDateTime.now(), newSeat);
        }catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, new HttpHeaders(), ZonedDateTime.now(), null);
        }

    }

    /***
     * Update a data from seat table by its ID
     * @return ResponseEntity<Object>
     * @throws ResourceNotFoundException
     */
    @GetMapping("/seat/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getOneSeat(@PathVariable Integer id) throws ResourceNotFoundException, DataNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop API B");
        try {
            Seat seat = this.seatServiceImpl.getOneSeat(id);
            SeatResponseDTO seatResponseDTO = seat.convertToResponse();
            return ResponseHandler.generateResponse(null, HttpStatus.OK, new HttpHeaders(), ZonedDateTime.now(), seatResponseDTO);
        }catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, new HttpHeaders(), ZonedDateTime.now(), null);
        }
    }

    /***
     * Update a data from seat table by its ID
     * @return ResponseEntity<Object>
     * @throws ResourceNotFoundException
     */
    @PostMapping("/seat/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> updateSeatById(@PathVariable Integer id, @RequestBody SeatRequestDTO seatRequestDTO) throws ResourceNotFoundException, DataNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop API B");
        try {
            Seat newSeat = seatRequestDTO.convertToEntity();
            Seat updatedSeat = this.seatServiceImpl.updateSeatById(newSeat);
            return ResponseHandler.generateResponse("update seat", HttpStatus.OK, new HttpHeaders(), ZonedDateTime.now(), updatedSeat.convertToResponse());
        }catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, new HttpHeaders(), ZonedDateTime.now(), null);
        }

    }

    /***
     * Delete a data from seat table by its ID
     * @return ResponseEntity<Object>
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/seat/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteSeatById(@PathVariable Integer id) throws ResourceNotFoundException, DataNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop API B");
        try {
            try {
                this.seatServiceImpl.deleteSeatById(id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return ResponseHandler.generateResponse("delete seat " + id, HttpStatus.OK, new HttpHeaders(), ZonedDateTime.now(), null);
        }catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, new HttpHeaders(), ZonedDateTime.now(), null);
        }

    }
}
