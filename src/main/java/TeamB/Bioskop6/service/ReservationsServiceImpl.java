package TeamB.Bioskop6.service;

import TeamB.Bioskop6.controller.ReservationController;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.repository.ReservationsRepository;
import TeamB.Bioskop6.repository.SeatDetailRepository;
import TeamB.Bioskop6.repository.UserRepository;
import TeamB.Bioskop6.dto.MessageResponse;
import TeamB.Bioskop6.dto.ReservationRequestDTO;
import TeamB.Bioskop6.dto.ReservationResponseDTO;
import TeamB.Bioskop6.entity.Reservation;
import TeamB.Bioskop6.entity.SeatDetail;
import TeamB.Bioskop6.entity.User;
import TeamB.Bioskop6.entity.UserDetailsImpl;
import TeamB.Bioskop6.handler.ResponseHandler;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReservationsServiceImpl implements ReservationsService {
    @Autowired
    ReservationsRepository reservationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SeatDetailRepository seatDetailRepository;

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
    public ResponseEntity<?> createReservation(ReservationRequestDTO reservationRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findById(userDetails.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User with ID " + reservationRequestDTO.getSeatDetailId() + " is not available!"));
            SeatDetail seatDetail = seatDetailRepository.findById(reservationRequestDTO.getSeatDetailId()).orElseThrow(() -> new ResourceNotFoundException("Seat Detail with ID " + reservationRequestDTO.getSeatDetailId() + " is not available!"));
            Reservation reservation = Reservation.builder().seatDetail(seatDetail).user(user).isActive(reservationRequestDTO.getIsActive()).build();
            reservationRepository.save(reservation);
            logger.info("--------------------------");
            logger.info("Create Reservations " + reservation);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("User with ID " + reservationRequestDTO.getReservationId() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), new MessageResponse("Succesfully created new reservation!"));
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> updateReservation(ReservationRequestDTO reservationRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findById(userDetails.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User with ID " + reservationRequestDTO.getSeatDetailId() + " is not available!"));
            SeatDetail seatDetail = seatDetailRepository.findById(reservationRequestDTO.getSeatDetailId()).orElseThrow(() -> new ResourceNotFoundException("Seat Detail with ID " + reservationRequestDTO.getSeatDetailId() + " is not available!"));
            Reservation reservation = Reservation.builder().reservationId(reservationRequestDTO.getReservationId()).seatDetail(seatDetail).user(user).isActive(reservationRequestDTO.getIsActive()).build();
            reservationRepository.findById(reservation.getReservationId()).orElseThrow(() -> new ResourceNotFoundException("User with ID " + reservation.getReservationId() + " is not available!"));
            reservationRepository.save(reservation);
            logger.info("--------------------------");
            logger.info("Update Reservations " + reservation);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("User with ID " + reservationRequestDTO.getReservationId() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), new MessageResponse("Succesfully updated new reservation!"));
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

    @Override
    public ResponseEntity<?> getInvoice() throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<User> user = userRepository.findById(userDetails.getUserId());
            List<Reservation> reservationsList = reservationRepository.findReservationsByUser(user.get());
            if (reservationsList.isEmpty()) {
                throw new ResourceNotFoundException("You have no invoice!");
            }
            List<ReservationResponseDTO> reservationResponseDTOS = new ArrayList<>();
            for (Reservation reservations : reservationsList){
                reservationResponseDTOS.add(reservations.convertToResponse());
            }
            logger.info("--------------------------");
            logger.info("Get All Reservations Data " + reservationsList);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), reservationResponseDTOS);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
}
