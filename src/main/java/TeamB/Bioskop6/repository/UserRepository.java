package TeamB.Bioskop6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import TeamB.Bioskop6.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
}