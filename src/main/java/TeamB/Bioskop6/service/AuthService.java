package TeamB.Bioskop6.service;


import java.util.UUID;

import org.springframework.http.ResponseEntity;

import TeamB.Bioskop6.dto.ConfirmOTPRequestDTO;
import TeamB.Bioskop6.dto.ForgetPasswordRequestDTO;
import TeamB.Bioskop6.dto.LoginRequest;
import TeamB.Bioskop6.dto.ResetPasswordRequestDTO;
import TeamB.Bioskop6.dto.SignupRequest;
import TeamB.Bioskop6.helper.PasswordNotMatchException;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.helper.WrongOTPException;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest signinRequest);

    ResponseEntity<?> registerUser(SignupRequest signupRequest) throws ResourceAlreadyExistException;

    ResponseEntity<?> forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO) throws ResourceNotFoundException;

    ResponseEntity<?> confirmOTP(ConfirmOTPRequestDTO confirmOTPRequestDTO) throws WrongOTPException, ResourceNotFoundException;

    ResponseEntity<?> resetPassword(UUID token, ResetPasswordRequestDTO resetPasswordRequestDTO) throws PasswordNotMatchException, ResourceNotFoundException;
}
