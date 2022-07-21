package TeamB.Bioskop6.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TeamB.Bioskop6.constant.ERole;
import TeamB.Bioskop6.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
