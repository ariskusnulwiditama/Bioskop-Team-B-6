package TeamB.Bioskop6.controller;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TeamB.Bioskop6.dto.StudioRequestDTO;
import TeamB.Bioskop6.dto.StudioResponseDTO;
import TeamB.Bioskop6.entity.Studio;
import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.service.StudioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class StudioController {
    private final StudioService studioService;

    /***
     * Get All data from studio table into list
     * @return
     */
    @GetMapping("/studios")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getAllStudios() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            List<Studio> studioList = this.studioService.getAllStudios();
            List<StudioResponseDTO> studioResponseDTOs = new ArrayList<>();
            for (Studio studio : studioList){
                studioResponseDTOs.add(studio.convertToResponse());
            }
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), studioResponseDTOs);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    /***
     * Get a data from studio table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/studio/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getStudioById(@PathVariable Integer id) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Studio studio = studioService.getStudioById(id);
            StudioResponseDTO studioResponseDTO = studio.convertToResponse();
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), studioResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    /***
     * Create a new data in studio table
     * @param studioRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     */
    @PostMapping("/studio/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> createStudio(StudioRequestDTO studioRequestDTO) throws ResourceAlreadyExistException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Studio studio = studioRequestDTO.convertToEntity();
            Studio newStudio = this.studioService.createStudio(studio);
            return ResponseHandler.generateResponse("Studio with ID " + newStudio.getStudioId() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), newStudio);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }
    
    /***
     * Update existing data in studio table
     * @param studioRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping("/studio/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> updateStudio(StudioRequestDTO studioRequestDTO) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            Studio studio = studioRequestDTO.convertToEntity();
            Studio updatedStudio = studioService.updateStudio(studio);
            return ResponseHandler.generateResponse("Studio with ID " + updatedStudio.getStudioId() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), updatedStudio);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
    
    /***
     * Delete existing data in studio table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/studio/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteStudio(@PathVariable Integer id) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            studioService.deleteStudio(id);
            return ResponseHandler.generateResponse("Studio with ID " + id + " successfully deleted!", HttpStatus.OK, headers, ZonedDateTime.now(), null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
}
