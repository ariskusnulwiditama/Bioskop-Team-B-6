package TeamB.Bioskop6.service;

import java.util.List;

import TeamB.Bioskop6.entity.User;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;

public interface UserService {
    List<User> getAllUser();

    User getUserById(Integer id) throws ResourceNotFoundException;
    
    User createUser(User user) throws ResourceAlreadyExistException;

    User updateUser(User user) throws ResourceNotFoundException;

    void deleteUser(Integer id) throws ResourceNotFoundException;
}
