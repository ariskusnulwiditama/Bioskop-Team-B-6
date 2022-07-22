package TeamB.Bioskop6.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TeamB.Bioskop6.dto.LoginRequest;
import TeamB.Bioskop6.dto.SignupRequest;
import TeamB.Bioskop6.service.AuthServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthServiceImpl authServiceImpl;

    /***
     * Endpoint to sign in and get the token for accessing the CRUD system
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return authServiceImpl.authenticateUser(loginRequest);
    }

    /***
     * Endpoint for createing new user with their roles
     * @param signupRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        return authServiceImpl.registerUser(signupRequest);
    }
}
