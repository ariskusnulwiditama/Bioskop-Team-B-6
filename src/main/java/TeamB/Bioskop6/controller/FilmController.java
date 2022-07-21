package TeamB.Bioskop6.controller;

import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.dto.FilmRequestDTO;
import TeamB.Bioskop6.dto.FilmResponseDTO;
import TeamB.Bioskop6.entity.Film;
import TeamB.Bioskop6.service.FilmServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class FilmController {

    private final FilmServiceImpl filmService;
//    private static final Logger logger = (Logger) LogManager.getLogger(FilmController.class);
    @GetMapping("/film")
    public ResponseEntity<Object> getAllfilm() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            List<Film> filmList = this.filmService.getAllFilm();
            List<FilmResponseDTO> filmResponseDTOS = new ArrayList<>();
            for (Film film : filmList){
                filmResponseDTOS.add(film.convertToResponse());
            }
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), filmResponseDTOS);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    @GetMapping("/film/{id}")
    public ResponseEntity<Object> getFilmById(@PathVariable Integer id) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Film film = filmService.getFilmById(id);
            FilmResponseDTO filmResponseDTO = film.convertToResponse();
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), filmResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @PostMapping("/film/create")
    public ResponseEntity<Object> createfilm(FilmRequestDTO filmRequestDTO) throws ResourceAlreadyExistException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Film film = filmRequestDTO.convertToEntity();
            Film newFilm = this.filmService.createFilm(film);
            return ResponseHandler.generateResponse("User with ID " + newFilm.getFilmCode() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), newFilm);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }

    @PutMapping("/film/update")
    public ResponseEntity<Object> updateUser(FilmRequestDTO filmRequestDTO) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Film film = filmRequestDTO.convertToEntity();
            Film updatedFilm = filmService.updateFilm(film);
            return ResponseHandler.generateResponse("User with ID " + updatedFilm.getFilmCode() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), updatedFilm);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @DeleteMapping("/film/delete/{id}")
    public ResponseEntity<Object> deleteFilm(@PathVariable Integer id) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            filmService.deleteFilm(id);
            return ResponseHandler.generateResponse("User with ID " + id + " successfully deleted!", HttpStatus.OK, headers, ZonedDateTime.now(), null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

}
