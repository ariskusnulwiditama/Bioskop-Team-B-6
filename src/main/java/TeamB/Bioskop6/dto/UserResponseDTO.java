package TeamB.Bioskop6.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
