package TeamB.Bioskop6.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConfirmOTPRequestDTO {
    @NotBlank
    @Size(max = 50)
    @Email
    private String emailAddress;
    
    @NotBlank
    private int otp;
}
