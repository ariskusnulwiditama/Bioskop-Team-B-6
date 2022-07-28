package TeamB.Bioskop6.service;

import TeamB.Bioskop6.dto.ReservationRequestDTO;

import TeamB.Bioskop6.helper.ResourceNotFoundException;

import org.springframework.http.ResponseEntity;

public interface ReservationsService {

    ResponseEntity<?> getAllReservation();

    ResponseEntity<?> getReservationById(Integer id) throws ResourceNotFoundException;

    ResponseEntity<?> createReservation(ReservationRequestDTO reservationRequestDTO) throws ResourceNotFoundException;

    ResponseEntity<?> updateReservation(ReservationRequestDTO reservationRequestDTO) throws ResourceNotFoundException;

    ResponseEntity<?> deleteReservation(Integer id) throws ResourceNotFoundException;
    
    ResponseEntity<?> getInvoice() throws ResourceNotFoundException;
}
