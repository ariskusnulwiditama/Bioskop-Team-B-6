package TeamB.Bioskop6.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import TeamB.Bioskop6.dto.StudioRequestDTO;
import TeamB.Bioskop6.dto.StudioResponseDTO;
import TeamB.Bioskop6.entity.Studio;
import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.repository.StudioRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudioServiceImpl implements StudioService {
    @Autowired
    StudioRepository studioRepository;
    
    HttpHeaders headers = new HttpHeaders();
    
    @Override
    public ResponseEntity<?> getAllStudios() {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            List<Studio> studioList = studioRepository.findAll();
            List<StudioResponseDTO> studioResponseDTOs = new ArrayList<>();
            for (Studio studio : studioList){
                studioResponseDTOs.add(studio.convertToResponse());
            }
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), studioResponseDTOs);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> getStudioById(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Studio studio = studioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Studio with ID "+ id + " is not available!"));
            StudioResponseDTO studioResponseDTO = studio.convertToResponse();
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), studioResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> createStudio(StudioRequestDTO studioRequestDTO) throws ResourceAlreadyExistException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Studio studio = studioRequestDTO.convertToEntity();
            studioRepository.findById(studio.getStudioId()).orElseThrow(() -> new ResourceAlreadyExistException("Studio with ID " + studio.getStudioId() + " is already exists!"));
            Studio newStudio = studioRepository.save(studio);
            return ResponseHandler.generateResponse("Studio with ID " + newStudio.getStudioId() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), newStudio);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> updateStudio(StudioRequestDTO studioRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Studio studio = studioRequestDTO.convertToEntity();
            studioRepository.findById(studio.getStudioId()).orElseThrow(() -> new ResourceNotFoundException("Studio with ID "+ studio.getStudioId() + " is not available!"));
            Studio updatedStudio = studioRepository.save(studio);
            return ResponseHandler.generateResponse("Studio with ID " + updatedStudio.getStudioId() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), updatedStudio);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> deleteStudio(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Studio studio = studioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Studio with ID " + id + " is not available!"));
            studioRepository.delete(studio);
            return ResponseHandler.generateResponse("Studio with ID " + id + " successfully deleted!", HttpStatus.OK, headers, ZonedDateTime.now(), null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
}
