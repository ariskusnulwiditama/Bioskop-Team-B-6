package TeamB.Bioskop6.service;


import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.dto.FilmRequestDTO;
import TeamB.Bioskop6.dto.FilmResponseDTO;
import TeamB.Bioskop6.entity.Film;
import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.repository.FilmRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    @Autowired
    FilmRepository filmRepository;

    HttpHeaders headers = new HttpHeaders();

    @Override
    public ResponseEntity<?> getAllFilm() {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            List<Film> filmList = filmRepository.findAll();
            List<FilmResponseDTO> filmResponseDTOS = new ArrayList<>();
            for (Film film : filmList){
                filmResponseDTOS.add(film.convertToResponse());
            }
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), filmResponseDTOS);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> getFilmById(Integer code) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Film film = filmRepository.findById(code).orElseThrow(() -> new ResourceNotFoundException("Film with Code " + code + " is not available!"));
            FilmResponseDTO filmResponseDTO = film.convertToResponse();
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), filmResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> createFilm(FilmRequestDTO filmRequestDTO) throws ResourceAlreadyExistException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Film film = filmRequestDTO.convertToEntity();
            filmRepository.findById(film.getFilmCode()).orElseThrow(() -> new ResourceAlreadyExistException("Film with Code " + film.getFilmCode() + " is already exists!"));
            Film newFilm = filmRepository.save(film);
            return ResponseHandler.generateResponse("User with ID " + newFilm.getFilmCode() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), newFilm);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> updateFilm(FilmRequestDTO filmRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Film film = filmRequestDTO.convertToEntity();
            filmRepository.findById(film.getFilmCode()).orElseThrow(() -> new ResourceNotFoundException("Film with Code " + film.getFilmCode() + " is not available!"));
            Film updatedFilm = filmRepository.save(film);
            return ResponseHandler.generateResponse("User with ID " + updatedFilm.getFilmCode() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), updatedFilm);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> deleteFilm(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Film film = filmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Film with ID " + id + " is not available!"));
            filmRepository.delete(film);
            return ResponseHandler.generateResponse("User with ID " + id + " successfully deleted!", HttpStatus.OK, headers, ZonedDateTime.now(), null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
}
