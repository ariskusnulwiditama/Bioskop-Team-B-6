// package TeamB.Bioskop6.service;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
// import java.util.stream.Collectors;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import TeamB.Bioskop6.config.CustomAuthentication;
// import TeamB.Bioskop6.config.JwtUtils;
// import TeamB.Bioskop6.constant.ERole;
// import TeamB.Bioskop6.dto.LoginRequest;
// import TeamB.Bioskop6.entity.Role;
// import TeamB.Bioskop6.entity.User;
// import TeamB.Bioskop6.entity.UserDetailsImpl;
// import TeamB.Bioskop6.repository.ResetPasswordTokenRepository;
// import TeamB.Bioskop6.repository.RoleRepository;
// import TeamB.Bioskop6.handler.ResponseHandler;
// import TeamB.Bioskop6.repository.UserRepository;
// import TeamB.Bioskop6.util.EmailSender;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.Mockito.when;

// @ExtendWith(SpringExtension.class)
// @SpringBootTest(classes = AuthServiceImpl.class)
// public class AuthServiceTest {
//     @MockBean
//     AuthenticationManager authenticationManager;

//     @MockBean
//     UserRepository userRepository;

//     @MockBean
//     RoleRepository roleRepository;

//     @MockBean
//     ResetPasswordTokenRepository resetPasswordTokenRepository;

//     @MockBean
//     PasswordEncoder encoder;

//     @MockBean
//     JwtUtils jwtUtils;

//     @MockBean
//     OTPServiceImpl otpService;

//     @MockBean
//     EmailSender emailSender;

//     @MockBean
//     Authentication authentication;

//     @Autowired
//     AuthServiceImpl authService;

//     @Value("${com.app.domain}")
//     String domain;

//     @Value("${server.port}")
//     String port;

//     @Value("${com.app.name}")
//     String projectName;

//     @Value("${com.app.team}")
//     String projectTeam;

//     @Test
//     void loginWithUsernamePositive_Test(){
//         Role userRole = Role.builder().roleId(1).name(ERole.ROLE_ADMIN).build();

//         GrantedAuthority grantedAuthority = new GrantedAuthority();


//         User.builder()
//                 .userId(1)
//                 .firstName("Najib")
//                 .lastName("Djulfikar")
//                 .username("najibdj_")
//                 .emailAddress("najib.djulfikar@gmail.com")
//                 .password("password")
//                 .createdAt(LocalDateTime.now())
//                 .updatedAt(LocalDateTime.now())
//                 .build();

//         LoginRequest loginRequest = LoginRequest.builder()
//                     .username("najibdj_")
//                     .password("password")
//                     .build();

//         UserDetailsImpl userDetails = UserDetailsImpl.builder()
//             .firstName("Najib")
//             .lastName("Djulfikar")
//             .username("najibdj_")
//             .emailAddress("najib.djulfikar@gmail.com")
//             .password("password")
//             .authorities([{"authority": "ROLE_ADMIN" }, {"authority": "ROLE_USER"}])
//             .build();

//         when(authenticationManager.authenticate(any())).thenReturn(new CustomAuthentication());
//         when(jwtUtils.generateJwtToken(any())).thenReturn("token");
//         when(userDetails.getAuthorities()).thenReturn();
//         ResponseEntity<?> response = authService.authenticateUser(loginRequest);
//         Map<String, Object> responseMap = (Map<String, Object>) response.getBody();
        

//         assertEquals("Bioskop6-API Team-B", responseMap.get("header"));
//     }
// }
