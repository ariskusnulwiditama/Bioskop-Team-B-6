package TeamB.Bioskop6.service;

import org.springframework.http.ResponseEntity;

import TeamB.Bioskop6.dto.SeatRequestDTO;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;

public interface SeatService {
    ResponseEntity<?> getAllSeat();

    ResponseEntity<?> getSeatById(Integer id) throws ResourceNotFoundException;

    ResponseEntity<?> createSeat(SeatRequestDTO seatRequestDTO) throws ResourceAlreadyExistException;
    
    ResponseEntity<?> updateSeat(SeatRequestDTO seatRequestDTO) throws ResourceNotFoundException;
    
    ResponseEntity<?> deleteSeat(Integer id) throws ResourceNotFoundException;
}
