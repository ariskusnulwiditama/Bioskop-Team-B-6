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

import TeamB.Bioskop6.dto.UserRequestDTO;
import TeamB.Bioskop6.dto.UserResponseDTO;
import TeamB.Bioskop6.entity.User;
import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    
    private final UserService userService;

    /***
     * Get All data from user table into list
     * @return
     */
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            List<User> userList = this.userService.getAllUser();
            List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
            for (User user : userList){
                userResponseDTOs.add(user.convertToResponse());
            }
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), userResponseDTOs);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    /***
     * Get a data from user table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            User user = userService.getUserById(id);
            UserResponseDTO userResponseDTO = user.convertToResponse();
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), userResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    /***
     * Create a new data in user table
     * @param userRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     */
    @PostMapping("/user/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> createUser(UserRequestDTO userRequestDTO) throws ResourceAlreadyExistException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            User user = userRequestDTO.convertToEntity();
            User newUser = this.userService.createUser(user);
            return ResponseHandler.generateResponse("User with ID " + newUser.getUserId() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), newUser);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }
    
    /***
     * Update existing data in user table
     * @param userRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping("/user/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> updateUser(UserRequestDTO userRequestDTO) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            User user = userRequestDTO.convertToEntity();
            User updatedUser = userService.updateUser(user);
            return ResponseHandler.generateResponse("User with ID " + updatedUser.getUserId() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), updatedUser);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
    
    /***
     * Delete existing data in user table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/user/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            userService.deleteUser(id);
            return ResponseHandler.generateResponse("User with ID " + id + " successfully deleted!", HttpStatus.OK, headers, ZonedDateTime.now(), null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
}
