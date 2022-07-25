package TeamB.Bioskop6.dto;

import javax.validation.constraints.NotBlank;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
