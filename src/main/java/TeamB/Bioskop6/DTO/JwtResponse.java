package TeamB.Bioskop6.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<String> roles;
    public JwtResponse(String token, Integer userId, String firstName, String lastName, String username, String email, List<String> roles) {
        this.token = token;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    
}
