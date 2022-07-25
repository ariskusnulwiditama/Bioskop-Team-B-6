package TeamB.Bioskop6.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDTO {
    private Integer userId;

    private String firstName;

    private String lastName;

    private String username;
    
    private String emailAddress;
    
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> roles;
}
