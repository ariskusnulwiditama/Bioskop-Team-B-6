package TeamB.Bioskop6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FilmResponseDTO {
    private Integer filmCode;

    private String filmName;

    private String genre;

    private Boolean isPlaying;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
