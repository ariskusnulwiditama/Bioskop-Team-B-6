package TeamB.Bioskop6.service;

import org.springframework.http.ResponseEntity;

import TeamB.Bioskop6.dto.UserRequestDTO;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;

public interface UserService {
    ResponseEntity<?> getAllUser();

    ResponseEntity<?> getUserById(Integer id) throws ResourceNotFoundException;
    
    ResponseEntity<?> createUser(UserRequestDTO userRequestDTO) throws ResourceAlreadyExistException;

    ResponseEntity<?> updateUser(UserRequestDTO userRequestDTO) throws ResourceNotFoundException;

    ResponseEntity<?> deleteUser(Integer id) throws ResourceNotFoundException;
}
