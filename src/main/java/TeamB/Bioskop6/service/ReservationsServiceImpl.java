package TeamB.Bioskop6.service;

import TeamB.Bioskop6.controller.FilmController;
import TeamB.Bioskop6.controller.ReservationController;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.repository.ReservationsRepository;
import TeamB.Bioskop6.dto.ReservationRequestDTO;
import TeamB.Bioskop6.dto.ReservationResponseDTO;
import TeamB.Bioskop6.entity.Reservation;
import TeamB.Bioskop6.handler.ResponseHandler;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReservationsServiceImpl implements ReservationsService {
    @Autowired
    ReservationsRepository reservationRepository;

    private final HttpHeaders headers = new HttpHeaders();
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);


    @Value("${com.app.name}")
    String projectName;

    @Value("${com.app.team}")
    String projectTeam;

    @Override
    public ResponseEntity<?> getAllReservation() {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            List<Reservation> reservationsList = reservationRepository.findAll();
            List<ReservationResponseDTO> reservationResponseDTOS = new ArrayList<>();
            for (Reservation reservations : reservationsList){
                reservationResponseDTOS.add(reservations.convertToResponse());
            }
            logger.info("--------------------------");
            logger.info("Get All Reservations Data " + reservationsList);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), reservationResponseDTOS);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> getReservationById(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " is not available!"));
            ReservationResponseDTO reservationResponseDTO = reservation.convertToResponse();
            logger.info("--------------------------");
            logger.info("Get All Reservations By ID " + reservation);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), reservationResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> createReservation(ReservationRequestDTO reservationRequestDTO) throws ResourceAlreadyExistException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Reservation reservation = reservationRequestDTO.convertToEntity();
            reservationRepository.findById(reservation.getReservationId()).orElseThrow(() -> new ResourceAlreadyExistException("User with ID " + reservation.getReservationId() + " is already exist!"));
            Reservation newReservations = reservationRepository.save(reservation);
            logger.info("--------------------------");
            logger.info("Create Reservations " + reservation);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("User with ID " + newReservations.getReservationId() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), newReservations);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> updateReservation(ReservationRequestDTO reservationRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Reservation reservation = reservationRequestDTO.convertToEntity();
            reservationRepository.findById(reservation.getReservationId()).orElseThrow(() -> new ResourceNotFoundException("User with ID " + reservation.getReservationId() + " is not available!"));
            Reservation updatedReservations = reservationRepository.save(reservation);
            logger.info("--------------------------");
            logger.info("Update Reservations " + reservation);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("User with ID " + updatedReservations.getReservationId() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), updatedReservations);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> deleteReservation(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " is not available!"));
            reservationRepository.delete(reservation);
            logger.info("--------------------------");
            logger.info("Delete Reservations " + reservation);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("User with ID " + id + " successfully deleted!", HttpStatus.OK, headers, ZonedDateTime.now(), null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

}
