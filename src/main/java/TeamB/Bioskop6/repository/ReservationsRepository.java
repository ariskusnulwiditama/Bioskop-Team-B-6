package TeamB.Bioskop6.repository;

import TeamB.Bioskop6.entity.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservations, Integer> {

}
