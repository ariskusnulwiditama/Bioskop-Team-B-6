package TeamB.Bioskop6.service;

import TeamB.Bioskop6.entity.Film;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.repository.FilmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FilmService {
    List<Film> getAllFilm();

    Film getFilmById(Integer id) throws ResourceNotFoundException;

    Film createFilm(Film film) throws ResourceAlreadyExistException;

    Film updateFilm(Film film) throws ResourceNotFoundException;

    void deleteFilm(Integer id) throws ResourceNotFoundException;
}
