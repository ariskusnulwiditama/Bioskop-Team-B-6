package TeamB.Bioskop6.service;

import TeamB.Bioskop6.dto.FilmRequestDTO;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;

import org.springframework.http.ResponseEntity;

public interface FilmService {
    ResponseEntity<?> getAllFilm();

    ResponseEntity<?> getFilmById(Integer code) throws ResourceNotFoundException;

    ResponseEntity<?> createFilm(FilmRequestDTO filmRequestDTO) throws ResourceAlreadyExistException;

    ResponseEntity<?> updateFilm(FilmRequestDTO filmRequestDTO) throws ResourceNotFoundException;

    ResponseEntity<?> deleteFilm(Integer id) throws ResourceNotFoundException;
}
