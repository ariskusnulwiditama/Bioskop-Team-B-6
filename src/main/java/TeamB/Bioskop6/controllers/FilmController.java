package TeamB.Bioskop6.controllers;

import TeamB.Bioskop6.dto.FilmResponseDTO;
import TeamB.Bioskop6.entity.Film;
import TeamB.Bioskop6.service.FilmService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@AllArgsConstructor
public class FilmController {

    private final FilmService filmService;
//    private static final Logger logger = (Logger) LogManager.getLogger(FilmController.class);


}
