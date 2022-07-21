package TeamB.Bioskop6.service;


import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.entity.Film;
import TeamB.Bioskop6.repository.FilmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    @Override
    public List<Film> getAllFilm() {
        List<Film> films = filmRepository.findAll();
        return films;
    }

    @Override
    public Film getFilmById(Integer id) throws ResourceNotFoundException {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if (optionalFilm.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " is not available!");
        }
        return optionalFilm.get();
    }

    @Override
    public Film createFilm(Film film) throws ResourceAlreadyExistException {
        Optional<Film> optionalFilm = filmRepository.findById(film.getFilmCode());
        if (optionalFilm.isPresent()) {
            throw new ResourceAlreadyExistException("User with ID " + film.getFilmCode() + " is already exist!");
        }
        Film newFilm = filmRepository.save(film);
        return newFilm;
    }

    @Override
    public Film updateFilm(Film film) throws ResourceNotFoundException {
        Optional<Film> optionalFilm = filmRepository.findById(film.getFilmCode());
        if (optionalFilm.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + film.getFilmCode() + " is not available!");
        }
        Film updatedFilm = filmRepository.save(film);
        return updatedFilm;
    }

    @Override
    public void deleteFilm(Integer id) throws ResourceNotFoundException {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if (optionalFilm.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " is not available!");
        }
        filmRepository.delete(optionalFilm.get());
    }

}
