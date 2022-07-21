package TeamB.Bioskop6.controllers;

import TeamB.Bioskop6.Handler.ResponseHandler;
import TeamB.Bioskop6.Helpers.ResourceAlreadyExistException;
import TeamB.Bioskop6.Helpers.ResourceNotFoundException;
import TeamB.Bioskop6.dto.ReservationRequestDTO;
import TeamB.Bioskop6.dto.ReservationResponseDTO;
import TeamB.Bioskop6.entity.Reservations;
import TeamB.Bioskop6.service.ReservationsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class ReservationsController {

    private final ReservationsServiceImpl reservationsService;

    @GetMapping("/Reservations")
    public ResponseEntity<Object> getAllReservations() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            List<Reservations> reservationsList = this.reservationsService.getAllReservations();
            List<ReservationResponseDTO> reservationResponseDTOS = new ArrayList<>();
            for (Reservations reservations : reservationsList){
                reservationResponseDTOS.add(reservations.convertToResponse());
            }
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), reservationResponseDTOS);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    @GetMapping("/Reservations/{id}")
    public ResponseEntity<Object> getReservationsById(@PathVariable Integer id) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Reservations reservations = reservationsService.getReservationsById(id);
            ReservationResponseDTO reservationResponseDTO = reservations.convertToResponse();
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), reservationResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @PostMapping("/Reservations/create")
    public ResponseEntity<Object> createReservations(ReservationRequestDTO reservationRequestDTO) throws ResourceAlreadyExistException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Reservations reservations = reservationRequestDTO.convertToEntity();
            Reservations newReservations = this.reservationsService.createReservations(reservations);
            return ResponseHandler.generateResponse("User with ID " + newReservations.getReservationId() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), newReservations);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }

    @PutMapping("/Reservations/update")
    public ResponseEntity<Object> updateReservations(ReservationRequestDTO reservationRequestDTO) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Reservations reservations = reservationRequestDTO.convertToEntity();
            Reservations updatedReservations = reservationsService.updateReservations(reservations);
            return ResponseHandler.generateResponse("User with ID " + updatedReservations.getReservationId() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), updatedReservations);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @DeleteMapping("/Reservations/delete/{id}")
    public ResponseEntity<Object> deleteReservations(@PathVariable Integer id) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            reservationsService.deleteReservations(id);
            return ResponseHandler.generateResponse("User with ID " + id + " successfully deleted!", HttpStatus.OK, headers, ZonedDateTime.now(), null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
}
