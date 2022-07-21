package TeamB.Bioskop6.service;

import TeamB.Bioskop6.entity.Reservations;

import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;

import java.util.List;

public interface ReservationsService {

    List<Reservations> getAllReservations();

    Reservations getReservationsById(Integer id) throws ResourceNotFoundException;

    Reservations createReservations(Reservations reservations) throws ResourceAlreadyExistException;

    Reservations updateReservations(Reservations reservations) throws ResourceNotFoundException;

    void deleteReservations(Integer id) throws ResourceNotFoundException;
}
