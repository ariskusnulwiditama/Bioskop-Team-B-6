package TeamB.Bioskop6.service;

import TeamB.Bioskop6.Helpers.ResourceAlreadyExistException;
import TeamB.Bioskop6.Helpers.ResourceNotFoundException;
import TeamB.Bioskop6.entity.Reservations;
import TeamB.Bioskop6.repository.ReservationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationsServiceImpl implements ReservationsService {
    private final ReservationsRepository reservationsRepository;
    //    private final ScheduleRepository scheduleRepository;

    @Override
    public List<Reservations> getAllReservations() {
        List<Reservations> reservations = reservationsRepository.findAll();
        return reservations;
    }

    @Override
    public Reservations getReservationsById(Integer id) throws ResourceNotFoundException {
        Optional<Reservations> optionalReservations = reservationsRepository.findById(id);
        if (optionalReservations.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " is not available!");
        }
        return optionalReservations.get();
    }

    @Override
    public Reservations createReservations(Reservations reservations) throws ResourceAlreadyExistException {
        Optional<Reservations> optionalReservations = reservationsRepository.findById(reservations.getReservationId());
        if (optionalReservations.isPresent()) {
            throw new ResourceAlreadyExistException("User with ID " + reservations.getReservationId() + " is already exist!");
        }
        Reservations newReservations = reservationsRepository.save(reservations);
        return newReservations;
    }

    @Override
    public Reservations updateReservations(Reservations reservations) throws ResourceNotFoundException {
        Optional<Reservations> optionalReservations = reservationsRepository.findById(reservations.getReservationId());
        if (optionalReservations.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + reservations.getReservationId() + " is not available!");
        }
        Reservations updatedReservations = reservationsRepository.save(reservations);
        return updatedReservations;
    }

    @Override
    public void deleteReservations(Integer id) throws ResourceNotFoundException {
        Optional<Reservations> optionalReservations = reservationsRepository.findById(id);
        if (optionalReservations.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " is not available!");
        }
        reservationsRepository.delete(optionalReservations.get());
    }

}
