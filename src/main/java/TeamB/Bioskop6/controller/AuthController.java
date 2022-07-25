package TeamB.Bioskop6.controller;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import TeamB.Bioskop6.dto.ConfirmOTPRequestDTO;
import TeamB.Bioskop6.dto.ForgetPasswordRequestDTO;
import TeamB.Bioskop6.dto.LoginRequest;
import TeamB.Bioskop6.dto.ResetPasswordRequestDTO;
import TeamB.Bioskop6.dto.SignupRequest;
import TeamB.Bioskop6.helper.PasswordNotMatchException;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.helper.WrongOTPException;
import TeamB.Bioskop6.service.AuthServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "1. Auth Controller")
public class AuthController {
    @Autowired
    AuthServiceImpl authServiceImpl;

    /***
     * Endpoint to sign in and get the token for accessing the CRUD system
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest){
        return authServiceImpl.authenticateUser(loginRequest);
    }

    /***
     * Endpoint for createing new user with their roles
     * @param signupRequest
     * @return
     * @throws ResourceAlreadyExistException
     */
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signupRequest) throws ResourceAlreadyExistException{
        return authServiceImpl.registerUser(signupRequest);
    }

    /***
     * Enpoint for generating OTP to reset password
     * @param forgetPasswordRequestDTO
     * @return
     * @throws ResourceNotFoundException
     * @throws MessagingException
     */
    @PostMapping("/forget_password")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequestDTO forgetPasswordRequestDTO) throws ResourceNotFoundException, MessagingException {
        return authServiceImpl.forgetPassword(forgetPasswordRequestDTO);
    }

    /***
     * Enpont for generating reset password token
     * @param confirmOTPRequestDTO
     * @return
     * @throws WrongOTPException
     * @throws ResourceNotFoundException
     */
    @PostMapping("/confirm_otp")
    public ResponseEntity<?> confirmOTP(@Valid @RequestBody ConfirmOTPRequestDTO confirmOTPRequestDTO) throws WrongOTPException, ResourceNotFoundException {
        return authServiceImpl.confirmOTP(confirmOTPRequestDTO);
    }

    /***
     * Enpoint for resetting password
     * @param token
     * @param resetPasswordRequestDTO
     * @return
     * @throws PasswordNotMatchException
     * @throws ResourceNotFoundException
     */
    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") UUID token, @Valid @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) throws PasswordNotMatchException, ResourceNotFoundException {
        return authServiceImpl.resetPassword(token, resetPasswordRequestDTO);
    }
}
