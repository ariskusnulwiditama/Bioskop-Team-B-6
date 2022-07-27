package TeamB.Bioskop6.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import TeamB.Bioskop6.controller.StudioController;
import TeamB.Bioskop6.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import TeamB.Bioskop6.dto.UserRequestDTO;
import TeamB.Bioskop6.dto.UserResponseDTO;
import TeamB.Bioskop6.entity.User;
import TeamB.Bioskop6.entity.UserDetailsImpl;
import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    private final HttpHeaders headers = new HttpHeaders();
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${com.app.name}")
    String projectName;

    @Value("${com.app.team}")
    String projectTeam;

    @Override
    public ResponseEntity<?> getAllUser() {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            List<User> userList = userRepository.findAll();
            List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
            for (User user : userList){
                userResponseDTOs.add(user.convertToResponse());
            }
            logger.info("--------------------------");
            logger.info("Get All User Data " + userList);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), userResponseDTOs);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> getUserById(Integer id) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " is not available!"));
            UserResponseDTO userResponseDTO = user.convertToResponse();
            logger.info("--------------------------");
            logger.info("Get All User Data By ID " + user);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), userResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> createUser(UserRequestDTO userRequestDTO) throws ResourceAlreadyExistException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            User user = userRequestDTO.convertToEntity();
            userRepository.findById(user.getUserId()).orElseThrow(() -> new ResourceAlreadyExistException("User with ID " + user.getUserId() + " is already exist!"));
            User newUser = this.userRepository.save(user);
            logger.info("--------------------------");
            logger.info("Create User " + user);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("User with ID " + newUser.getUserId() + " successfully created!", HttpStatus.CREATED, headers, ZonedDateTime.now(), newUser);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> updateUser(UserRequestDTO userRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            User user = userRequestDTO.convertToEntity();
            userRepository.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User with ID " + user.getUserId() + " is not available!"));
            User updatedUser = userRepository.save(user);
            logger.info("--------------------------");
            logger.info("Update User " + user);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("User with ID " + updatedUser.getUserId() + " successfully updated!", HttpStatus.OK, headers, ZonedDateTime.now(), updatedUser);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(Integer id) throws ResourceNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " is not available!"));
            userRepository.delete(user);
            logger.info("--------------------------");
            logger.info("Delete User " + user);
            logger.info("--------------------------");
            return ResponseHandler.generateResponse("User with ID " + id + " successfully deleted!", HttpStatus.OK, headers, ZonedDateTime.now(), null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }

    @Override
    public ResponseEntity<?> getLoggedUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), userDetails);
    }
}
