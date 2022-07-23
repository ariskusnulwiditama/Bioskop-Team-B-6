package TeamB.Bioskop6.service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import TeamB.Bioskop6.config.JwtUtils;
import TeamB.Bioskop6.constant.ERole;
import TeamB.Bioskop6.dto.ConfirmOTPRequestDTO;
import TeamB.Bioskop6.dto.ForgetPasswordRequestDTO;
import TeamB.Bioskop6.dto.JwtResponse;
import TeamB.Bioskop6.dto.LoginRequest;
import TeamB.Bioskop6.dto.MessageResponse;
import TeamB.Bioskop6.dto.ResetPasswordRequestDTO;
import TeamB.Bioskop6.dto.SignupRequest;
import TeamB.Bioskop6.entity.ResetPasswordToken;
import TeamB.Bioskop6.entity.Role;
import TeamB.Bioskop6.entity.User;
import TeamB.Bioskop6.entity.UserDetailsImpl;
import TeamB.Bioskop6.handler.ResponseHandler;
import TeamB.Bioskop6.helper.PasswordNotMatchException;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.helper.WrongOTPException;
import TeamB.Bioskop6.repository.ResetPasswordTokenRepository;
import TeamB.Bioskop6.repository.RoleRepository;
import TeamB.Bioskop6.repository.UserRepository;
import TeamB.Bioskop6.util.EmailSender;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    OTPServiceImpl otpService;

    @Autowired
    EmailSender emailSender;

    private final HttpHeaders headers = new HttpHeaders();

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), new JwtResponse(jwt, userDetails.getUserId(), userDetails.getFirstName(), userDetails.getLastName(), userDetails.getUsername(), userDetails.getEmailAddress(), roles));
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signupRequest) throws ResourceAlreadyExistException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            if (userRepository.existsByUsername(signupRequest.getUsername())) {
                throw new ResourceAlreadyExistException("Username already taken!");
            }
            if (userRepository.existsByEmailAddress(signupRequest.getEmail())) {
                throw new ResourceAlreadyExistException("Email already in use!");
            }

            User user = new User(signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getUsername(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));
            Set<String> strRoles = signupRequest.getRole();
            Set<Role> roles = new HashSet<Role>();
    
            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch(role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
                            roles.add(adminRole);
                        break;
                        case "mod":
                            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
                            roles.add(modRole);
                        break;
                        default :
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
                        roles.add(userRole);
                    }
                });
            }
            user.setRoles(roles);
            userRepository.save(user);
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), new MessageResponse("User registered successfully!"));
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        }catch (RuntimeException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        } 
    }
    
    @Override
    public ResponseEntity<?> forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO) throws ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            if (!userRepository.existsByEmailAddress(forgetPasswordRequestDTO.getEmailAddress())) {
                throw new ResourceNotFoundException("User with email " + forgetPasswordRequestDTO.getEmailAddress() + " does not exist!");
            }
            String emailAddress = forgetPasswordRequestDTO.getEmailAddress();
            int otp = otpService.generateOTP(emailAddress);
            emailSender.sendSimpleMessage(emailAddress, "Reset Password Request", String.valueOf(otp));
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), new MessageResponse("OTP has been sent to your email!"));
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
    
    @Override
    public ResponseEntity<?> confirmOTP(ConfirmOTPRequestDTO confirmOTPRequestDTO) throws WrongOTPException, ResourceNotFoundException{
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            if (otpService.getOTP(confirmOTPRequestDTO.getEmailAddress()) == 0) {
                throw new ResourceNotFoundException("You have not generated OTP!");
            }else if (otpService.getOTP(confirmOTPRequestDTO.getEmailAddress()) != confirmOTPRequestDTO.getOtp()) {
                throw new WrongOTPException("Wrong OTP!");
            }
            resetPasswordTokenRepository.deleteByEmailAddress(confirmOTPRequestDTO.getEmailAddress());
            ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.save(ResetPasswordToken.builder().emailAddress(confirmOTPRequestDTO.getEmailAddress()).build());
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), "localhost:8083/api/auth/reset_password?token=" + resetPasswordToken.getToken().toString());
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        } catch (WrongOTPException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, headers, ZonedDateTime.now(), null);
        }
    }
    
    @Override
    public ResponseEntity<?> resetPassword(UUID token ,ResetPasswordRequestDTO resetPasswordRequestDTO) throws PasswordNotMatchException, ResourceNotFoundException {
        headers.set("APP-NAME", "Bioskop6 API KELOMPOK B");
        try {
            if (!resetPasswordRequestDTO.getNewPassword().equals(resetPasswordRequestDTO.getConfirmPassword())) {
                throw new PasswordNotMatchException("Password not match!");
            }
            Optional<ResetPasswordToken> resetPasswordToken = resetPasswordTokenRepository.findByToken(token);
            if (resetPasswordToken.isEmpty()) {
                throw new ResourceNotFoundException("Token is not valid!");
            }
            Optional<User> optionalUser = userRepository.findByEmailAddress(resetPasswordToken.get().getEmailAddress());
            User user = optionalUser.get();
            user.setPassword(encoder.encode(resetPasswordRequestDTO.getNewPassword()));
            userRepository.save(user);
            return ResponseHandler.generateResponse(null, HttpStatus.OK, headers, ZonedDateTime.now(), new MessageResponse("Password has been reseted!"));
        } catch (PasswordNotMatchException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, headers, ZonedDateTime.now(), null);

        }
    }
}
