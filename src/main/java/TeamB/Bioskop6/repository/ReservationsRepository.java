package TeamB.Bioskop6.repository;

import TeamB.Bioskop6.entity.Reservation;
import TeamB.Bioskop6.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, Integer> {
    @Query("select r from Reservation r where r.user = :user")
    List<Reservation> findReservationsByUser(@Param("user") User user);
}
