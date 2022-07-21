package TeamB.Bioskop6.service;

import TeamB.Bioskop6.helper.DataNotFoundException;
import TeamB.Bioskop6.entity.Seat;
import TeamB.Bioskop6.repository.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;

    @Override
    public List<Seat> getAllSeat(){
        return this.seatRepository.findAll();
    }

    @Override
    public Seat createSeat (Seat seat){
        return this.seatRepository.save(seat);
    }

    @Override
    public Seat getOneSeat(Integer id) throws DataNotFoundException {
        Optional<Seat> optionalSeat = this.seatRepository.findById(id);

        if(optionalSeat.isEmpty()){
            throw new DataNotFoundException("Seat is Not Available");
        }
        return optionalSeat.get();
    }

    @Override
    public Seat updateSeatById(Seat seat) throws DataNotFoundException {
        this.getOneSeat(seat.getSeatId());
        return this.seatRepository.save(seat);
    }

    @Override
    public void deleteSeatById(Integer id) throws DataNotFoundException {
        this.getOneSeat(id);
        this.seatRepository.deleteById(id);
    }
}
