package TeamB.Bioskop6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import TeamB.Bioskop6.entity.Studio;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Integer> {
    
}
