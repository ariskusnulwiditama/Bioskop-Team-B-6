package TeamB.Bioskop6.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import TeamB.Bioskop6.entity.User;
import lombok.*;

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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

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
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
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
