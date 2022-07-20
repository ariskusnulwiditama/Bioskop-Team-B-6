package TeamB.Bioskop6.service;

import TeamB.Bioskop6.Helpers.DataNotFoundException;
import TeamB.Bioskop6.entity.Reservations;
import TeamB.Bioskop6.entity.Schedule;
import TeamB.Bioskop6.repository.ReservationsRepository;
import TeamB.Bioskop6.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationsService {
    private final ReservationsRepository reservationsRepository;
    private final ScheduleRepository scheduleRepository;

    public List<Reservations> getAllReservations() throws DataNotFoundException {
        List<Reservations> reservationsList = this.reservationsRepository.findAll();
        if (reservationsList.isEmpty()) {
            throw new DataNotFoundException("No reservations exist!");
        }
        return this.reservationsRepository.findAll();
    }

    public Reservations createReservation (Reservations reservations) throws DataNotFoundException{
        Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(reservations.getSchedule().getSchaduleId());
        if (optionalSchedule.isEmpty()){
            throw new DataNotFoundException("Schedule with id "+ reservations.getSchedule().getSchaduleId() +" is not exist");
        }
        reservations.setSchedule(optionalSchedule.get());
        return this.reservationsRepository.save(reservations);
    }

    public Reservations updateReservation (Reservations reservations) throws DataNotFoundException {
        Optional<Reservations>  optionalReservations = this.reservationsRepository.findById(reservations.getReservationId());
        if (optionalReservations.isEmpty()){
            throw new DataNotFoundException("Reservation with id :"+ reservations.getReservationId()+ " not exist ");
        }
        return this.reservationsRepository.save(reservations);
    }

    public void deleteReservation (Reservations reservations) throws DataNotFoundException {
        Optional<Reservations> optionalReservations = this.reservationsRepository.findById(reservations.getReservationId());
        if (optionalReservations.isEmpty()){
            throw new DataNotFoundException("Reservation with id :"+ reservations.getReservationId()+ " not exist ");
        }
        this.reservationsRepository.delete(optionalReservations.get());
    }

}
