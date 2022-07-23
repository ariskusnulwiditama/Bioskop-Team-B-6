package TeamB.Bioskop6.controller;

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
    public ResponseEntity<?> getAll() {
        return studioService.getAllStudios();
    }

    /***
     * Get a data from studio table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/studio/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Integer id) throws ResourceNotFoundException {
        return studioService.getStudioById(id);
    }

    /***
     * Create a new data in studio table
     * @param studioRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     */
    @PostMapping("/studio/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> create(StudioRequestDTO studioRequestDTO) throws ResourceAlreadyExistException {
        return studioService.createStudio(studioRequestDTO);
    }
    
    /***
     * Update existing data in studio table
     * @param studioRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping("/studio/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(StudioRequestDTO studioRequestDTO) throws ResourceNotFoundException {
        return studioService.updateStudio(studioRequestDTO);
    }
    
    /***
     * Delete existing data in studio table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/studio/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        return studioService.deleteStudio(id);
    }
}
