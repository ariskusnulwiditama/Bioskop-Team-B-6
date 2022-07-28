package TeamB.Bioskop6.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TeamB.Bioskop6.dto.UserRequestDTO;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Tag(name = "2. User Controller")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    
    private final UserService userService;

    /***
     * Get All data from user table into list
     * @return
     */
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getAll() {
        return userService.getAllUser();
    }

    /***
     * Get a data from user table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Integer id) throws ResourceNotFoundException {
        return userService.getUserById(id);
    }

    /***
     * Update existing data in user table
     * @param userRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PostMapping("/user/update")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> update(UserRequestDTO userRequestDTO) throws ResourceNotFoundException {
        return userService.updateUser(userRequestDTO);
    }
    
    /***
     * Delete existing data in user table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/user/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        return userService.deleteUser(id);
    }
    
    /***
     * Get logged in user
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getLoggedUser")
    public ResponseEntity<?> resetPassword(){
        return userService.getLoggedUser();
    }
}
