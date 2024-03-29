package TeamB.Bioskop6.service;

import TeamB.Bioskop6.controller.FilmController;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.dto.FilmRequestDTO;
import TeamB.Bioskop6.dto.FilmResponseDTO;
import TeamB.Bioskop6.dto.MessageResponse;
import TeamB.Bioskop6.entity.Film;
import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.repository.FilmRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    FilmRepository filmRepository;

    @Value("${com.app.name}")
    String projectName;

    @Value("${com.app.team}")
    String projectTeam;

    private final HttpHeaders headers = new HttpHeaders();
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private static final String loggerLine = "---------------------------------------";

    @Override
    public ResponseEntity<?> getAllFilm() {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            List<Film> filmList = filmRepository.findAll();
            List<FilmResponseDTO> filmResponseDTOS = new ArrayList<>();
            for (Film film : filmList){
                filmResponseDTOS.add(film.convertToResponse());
            }
            logger.info(loggerLine);
            logger.info("Get All Film Data " + filmList);
            logger.info(loggerLine);
            return ResponseHandler.generateResponse("Successfully retrieve all Seats!", HttpStatus.OK, headers, ZonedDateTime.now(), filmResponseDTOS);
        } catch (Exception e) {
            logger.error(loggerLine);
            logger.error(e.getMessage());
            logger.error(loggerLine);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> getFilmById(Integer code) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Film film = filmRepository.findById(code).orElseThrow(() -> new ResourceNotFoundException("Film with Code " + code + " is not available!"));
            FilmResponseDTO filmResponseDTO = film.convertToResponse();
            logger.info(loggerLine);
            logger.info("Get All Film By ID " + film);
            logger.info(loggerLine);
            return ResponseHandler.generateResponse("Successfully retrieve Seats By ID!", HttpStatus.OK, headers, ZonedDateTime.now(), filmResponseDTO);
        } catch (ResourceNotFoundException e) {
            logger.error(loggerLine);
            logger.error(e.getMessage());
            logger.error(loggerLine);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> createFilm(FilmRequestDTO filmRequestDTO) throws ResourceAlreadyExistException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Film film = filmRequestDTO.convertToEntity();
            if (Boolean.TRUE.equals(filmRepository.existsByFilmCode(film.getFilmCode()))){
                throw new ResourceAlreadyExistException("Film with Code " + film.getFilmCode() + " is already exists!");
            }
            Film newFilm = filmRepository.save(film);
            logger.info(loggerLine);
            logger.info("Create Film " + film);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("User with ID " + newFilm.getFilmCode() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), new MessageResponse("Successfully create new film!"));
        } catch (ResourceAlreadyExistException e) {
            logger.error(loggerLine);
            logger.error(e.getMessage());
            logger.error(loggerLine);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> updateFilm(FilmRequestDTO filmRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Film film = filmRequestDTO.convertToEntity();
            if (Boolean.FALSE.equals(filmRepository.existsByFilmCode(film.getFilmCode()))){
                throw new ResourceNotFoundException("Film with Code " + film.getFilmCode() + " is not available!");
            }
            Film updatedFilm = filmRepository.save(film);
            logger.info(loggerLine);
            logger.info("Update Film " + film);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("User with ID " + updatedFilm.getFilmCode() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), new MessageResponse("Successfully update film!"));
        } catch (Exception e) {
            logger.error(loggerLine);
            logger.error(e.getMessage());
            logger.error(loggerLine);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> deleteFilm(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            Film film = filmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Film with ID " + id + " is not available!"));
            filmRepository.delete(film);
            logger.info(loggerLine);
            logger.info("Delete Film " + film);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), new MessageResponse("User with ID " + id + " successfully deleted!"));
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> findPlayingFilm() throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            List<Film> filmList = filmRepository.findByIsPlaying(true);
            List<FilmResponseDTO> filmResponseDTOS = new ArrayList<>();
            for (Film film : filmList){
                filmResponseDTOS.add(film.convertToResponse());
            }
            logger.info("--------------------------");
            logger.info("Get All Film Data " + filmList);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("Successfully retrieve all Seats!", HttpStatus.OK, headers, ZonedDateTime.now(), filmResponseDTOS);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }
}
