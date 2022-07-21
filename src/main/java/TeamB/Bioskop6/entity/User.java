package TeamB.Bioskop6.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import TeamB.Bioskop6.dto.UserResponseDTO;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userId;

    private String firstName;

    private String lastName;

    private String username;
    
    private String emailAddress;
    
    private String password;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public UserResponseDTO convertToResponse(){
        return UserResponseDTO.builder()
            .userId(userId)
            .firstName(firstName)
            .lastName(lastName)
            .username(username)
            .emailAddress(emailAddress)
            .password(password)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    } 
}
