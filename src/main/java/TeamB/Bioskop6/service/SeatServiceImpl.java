package TeamB.Bioskop6.service;

import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.dto.SeatRequestDTO;
import TeamB.Bioskop6.dto.SeatResponseDTO;
import TeamB.Bioskop6.entity.Seat;
import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.repository.SeatRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {
    @Autowired
    SeatRepository seatRepository;

    HttpHeaders headers = new HttpHeaders();
    
    @Override
    public ResponseEntity<?> getAllSeat() {
        headers.set("APP-NAME", "Bioskop API B");
        try {
            List<Seat> seats = seatRepository.findAll();
            List<SeatResponseDTO> seatResponseDTO = new ArrayList<>();
            for(Seat seat : seats){
                seatResponseDTO.add(seat.convertToResponse());
            }
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), seatResponseDTO);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> getSeatById(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop API B");
        try {
            Seat seat = seatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat with ID "+ id + " is not available!"));
            SeatResponseDTO seatResponseDTO = seat.convertToResponse();
            return ResponseHandler.generateResponse(null, HttpStatus.OK, new HttpHeaders(), ZonedDateTime.now(), seatResponseDTO);
        }catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, new HttpHeaders(), ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> createSeat(SeatRequestDTO seatRequestDTO) throws ResourceAlreadyExistException {
        headers.set("APP-NAME", "Bioskop API B");
        try {
            Seat seat = seatRequestDTO.convertToEntity();
            seatRepository.findById(seat.getSeatId()).orElseThrow(() -> new ResourceAlreadyExistException("Studio with ID "+ seat.getSeatId() + " is already exist!"));
            Seat newSeat = seatRepository.save(seat);
            return ResponseHandler.generateResponse(null, HttpStatus.CREATED, headers, ZonedDateTime.now(), newSeat);
        }catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, new HttpHeaders(), ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> updateSeat(SeatRequestDTO seatRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop API B");
        try {
            Seat seat = seatRequestDTO.convertToEntity();
            seatRepository.findById(seat.getSeatId()).orElseThrow(() -> new ResourceNotFoundException("Seat with ID "+ seat.getSeatId() + " is not available!"));
            Seat updatedSeat = seatRepository.save(seat);
            return ResponseHandler.generateResponse("update seat", HttpStatus.OK, new HttpHeaders(), ZonedDateTime.now(), updatedSeat.convertToResponse());
        }catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, new HttpHeaders(), ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> deleteSeat(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop API B");
        try {
            try {
                Seat seat = seatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat with ID "+ id + " is not available!"));
                seatRepository.delete(seat);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return ResponseHandler.generateResponse("delete seat " + id, HttpStatus.OK, new HttpHeaders(), ZonedDateTime.now(), null);
        }catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, new HttpHeaders(), ZonedDateTime.now(), null);
        }
    }
}
