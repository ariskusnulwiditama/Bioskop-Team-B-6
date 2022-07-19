package TeamB.Bioskop6.DTO;

import TeamB.Bioskop6.entity.User;
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
public class UserRequestDTO {
    private Integer userId;

    private String firstName;

    private String lastName;

    private String username;
    
    private String emailAddress;
    
    private String password;

    /***
     * request to create new User
     * @return
     */
    public User convertToEntity(){
        return User.builder()
            .userId(this.userId)
            .firstName(this.firstName)
            .lastName(this.lastName)
            .username(this.username)
            .emailAddress(this.emailAddress)
            .password(this.password)
            .build();
    }

    /***
     * request to update existing User by ID
     * @return
     */
    public User updateEntity(){
        return User.builder()
            .firstName(this.firstName)
            .lastName(this.lastName)
            .username(this.username)
            .emailAddress(this.emailAddress)
            .password(this.password)
            .build();
    }
}
