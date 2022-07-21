package TeamB.Bioskop6.service;

import org.springframework.http.ResponseEntity;

import TeamB.Bioskop6.dto.LoginRequest;
import TeamB.Bioskop6.dto.SignupRequest;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest signinRequest);

    ResponseEntity<?> registerUser(SignupRequest signupRequest);
}
