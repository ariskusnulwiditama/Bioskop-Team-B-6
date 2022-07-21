package TeamB.Bioskop6.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import TeamB.Bioskop6.config.JwtUtils;
import TeamB.Bioskop6.constant.ERole;
import TeamB.Bioskop6.dto.JwtResponse;
import TeamB.Bioskop6.dto.LoginRequest;
import TeamB.Bioskop6.dto.MessageResponse;
import TeamB.Bioskop6.dto.SignupRequest;
import TeamB.Bioskop6.entity.Role;
import TeamB.Bioskop6.entity.User;
import TeamB.Bioskop6.entity.UserDetailsImpl;
import TeamB.Bioskop6.repository.RoleRepository;
import TeamB.Bioskop6.repository.UserRepository;

public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUserId(), userDetails.getFirstName(), userDetails.getLastName(), userDetails.getUsername(), userDetails.getEmailAddress(), roles));
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username already taken!"));
        }
        if (userRepository.existsByEmailAddress(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email already in use!"));
        }

        User user = new User(signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getUsername(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<Role>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
            roles.add(userRole);
        }else {
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

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    
}
