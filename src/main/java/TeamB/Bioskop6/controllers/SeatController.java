package TeamB.Bioskop6.controllers;

import TeamB.Bioskop6.Handler.ResponseHandler;
import TeamB.Bioskop6.Helpers.DataNotFoundException;
import TeamB.Bioskop6.dto.SeatRequestDTO;
import TeamB.Bioskop6.dto.SeatResponseDTO;
import TeamB.Bioskop6.entity.Seat;
import TeamB.Bioskop6.service.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class SeatController {
    @Autowired
    private final SeatService seatService;

    @GetMapping("/seats")
    public ResponseEntity<?> getAllSeats() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop API B");
        //List<Seat> seats = this.seatService.getAllSeat();
        try {
            List<SeatResponseDTO> seatResponseDTO = new ArrayList<>();
            ResponseEntity<?> body = ResponseHandler.generateResponse("get all seats", HttpStatus.OK, new HttpHeaders(), ZonedDateTime.now(ZoneId.of("Asia/Tokyo")), seatResponseDTO);
            return ResponseEntity.status(body.getStatusCode()).headers(body.getHeaders()).body(body.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(new HttpHeaders()).body(e.getMessage());
        }
    }

    @PostMapping("/seats")
    public ResponseEntity<?> createSeat(@RequestBody SeatRequestDTO seatRequestDTO) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop API B");
        Seat newSeat = seatRequestDTO.convertToEntity();
        Seat seat = this.seatService.createSeat(newSeat);
        SeatResponseDTO seatResponseDTO = seat.convertToResponse();
        ResponseEntity<?> body = ResponseHandler.generateResponse("create seat", HttpStatus.OK, new HttpHeaders(), ZonedDateTime.now(ZoneId.of("Asia/Tokyo")), seatResponseDTO);
        return ResponseEntity.status(body.getStatusCode()).headers(body.getHeaders()).body(body.getBody());
    }

    @GetMapping("/seat/{id}")
    public ResponseEntity<?> getOneSeat(@PathVariable Integer id, @RequestBody SeatRequestDTO seatRequestDTO) throws DataNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop API B");
        Seat seat = this.seatService.getOneSeat(id);
        return ResponseHandler.generateResponse("Success get one seat", HttpStatus.OK, new HttpHeaders(), ZonedDateTime.now(ZoneId.of("Asia/Tokyo")), seat);
    }

    @PostMapping("/seat/{id}")
    public ResponseEntity<?> updateSeatById(@PathVariable Integer id, @RequestBody SeatRequestDTO seatRequestDTO) throws DataNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop API B");
        Seat newSeat = seatRequestDTO.convertToEntity();
        newSeat.setSeatId(id);
        Seat updatedSeat = this.seatService.updateSeatById(newSeat);
        SeatResponseDTO seatResponseDTO = updatedSeat.convertToResponse();
        ResponseEntity<?> body = ResponseHandler.generateResponse("Success update seat", HttpStatus.OK, new HttpHeaders(), ZonedDateTime.now(ZoneId.of("Asia/Tokyo")), seatResponseDTO);
        return ResponseEntity.status(body.getStatusCode()).headers(body.getHeaders()).body(body.getBody());
    }

    @DeleteMapping("/seat/{id}")
    public ResponseEntity<?> deleteSeatById(@PathVariable Integer id) throws DataNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop API B");
        Seat seat = new Seat();
        seat.setSeatId(id);
        this.seatService.deleteSeatById(seat);
        ResponseEntity<?> body = ResponseHandler.generateResponse("Success delete seat", HttpStatus.OK, new HttpHeaders(), ZonedDateTime.now(ZoneId.of("Asia/Tokyo")), null);
        return ResponseEntity.status(body.getStatusCode()).headers(body.getHeaders()).body(body.getBody());
    }
}
