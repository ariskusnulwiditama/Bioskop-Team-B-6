package TeamB.Bioskop6.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import TeamB.Bioskop6.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailAddress(String emailAddress);
    Boolean existsByUsername(String username);
    Boolean existsByEmailAddress(String emailAddress);
}
