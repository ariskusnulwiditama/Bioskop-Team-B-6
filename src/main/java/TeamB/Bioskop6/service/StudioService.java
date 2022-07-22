package TeamB.Bioskop6.service;

import org.springframework.http.ResponseEntity;

import TeamB.Bioskop6.dto.StudioRequestDTO;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;

public interface StudioService {
    ResponseEntity<?> getAllStudios();

    ResponseEntity<?> getStudioById(Integer id) throws ResourceNotFoundException;

    ResponseEntity<?> createStudio(StudioRequestDTO studioRequestDTO) throws ResourceAlreadyExistException;

    ResponseEntity<?> updateStudio(StudioRequestDTO studioRequestDTO) throws ResourceNotFoundException;

    ResponseEntity<?> deleteStudio(Integer id) throws ResourceNotFoundException;
}
