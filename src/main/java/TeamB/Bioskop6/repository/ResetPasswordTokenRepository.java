package TeamB.Bioskop6.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import TeamB.Bioskop6.entity.ResetPasswordToken;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Integer>{
    @Modifying
    @Transactional
    @Query("DELETE FROM ResetPasswordToken r WHERE r.emailAddress = :emailAddress")
    void deleteByEmailAddress(@Param("emailAddress") String emailAddress);

    Optional<ResetPasswordToken> findByToken(UUID token);
}
