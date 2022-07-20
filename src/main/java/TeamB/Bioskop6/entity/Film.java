package TeamB.Bioskop6.entity;

import TeamB.Bioskop6.dto.FilmResponseDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Film {


    @Id
    private Integer filmCode;

    @Column(length = 100)
    private String filmName;

    @Column(length = 20)
    private String genre;

    private Boolean isPlaying;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public FilmResponseDTO convertToResponse(){
        return FilmResponseDTO.builder()
                .filmCode(this.filmCode)
                .genre(this.genre)
                .filmName(this.filmName)
                .isPlaying(this.isPlaying)
                .build();
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmCode=" + filmCode +
                ", filmName='" + filmName + '\'' +
                ", genre='" + genre + '\'' +
                ", isPlaying=" + isPlaying +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
