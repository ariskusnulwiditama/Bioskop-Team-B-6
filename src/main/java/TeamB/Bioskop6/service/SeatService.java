package TeamB.Bioskop6.service;

import TeamB.Bioskop6.entity.Seat;
import TeamB.Bioskop6.helper.DataNotFoundException;

import java.util.List;

public interface SeatService {
    List<Seat> getAllSeat();
    Seat createSeat (Seat seat);
    Seat getOneSeat(Integer id) throws DataNotFoundException;
    Seat updateSeatById(Seat seat) throws DataNotFoundException;
    void deleteSeatById(Integer id) throws DataNotFoundException;
}
