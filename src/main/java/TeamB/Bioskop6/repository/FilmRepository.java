package TeamB.Bioskop6.repository;

import TeamB.Bioskop6.entity.Film;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film,Integer> {
    Boolean existsByFilmCode(Integer filmCode);

    List<Film> findByIsPlaying(Boolean isPlaying);

    Film findByFilmName(String filmName);
}
