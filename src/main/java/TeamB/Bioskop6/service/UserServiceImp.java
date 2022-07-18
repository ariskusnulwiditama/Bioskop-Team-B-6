package TeamB.Bioskop6.service;

import java.util.List;
import java.util.Optional;

import TeamB.Bioskop6.entity.User;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.repository.UserRepository;

public class UserServiceImp implements UserService {
    
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User getUserById(Integer id) throws ResourceNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User with ID" + id + "is not available!");
        }
        return optionalUser.get();
    }

    @Override
    public User createUser(User user) throws ResourceAlreadyExistException {
        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if (optionalUser.isPresent()) {
            throw new ResourceAlreadyExistException("User with ID" + user.getUserId() + "is already exist!");
        }
        User newUser = userRepository.save(user);
        return newUser;
    }

    @Override
    public User updateUser(User user) throws ResourceNotFoundException {
        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User with ID" + user.getUserId() + "is not available!");
        }
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @Override
    public void deleteUser(Integer id) throws ResourceNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User with ID" + id + "is not available!");
        }
    }
    
}
