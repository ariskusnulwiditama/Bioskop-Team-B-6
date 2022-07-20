package TeamB.Bioskop6.dto;

import TeamB.Bioskop6.entity.Film;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmRequestDTO {
    private Integer filmCode;
    private String filmName;
    private String genre;
    private Boolean isPlaying;

    public Film convertToEntity(){
        return Film.builder()
                .filmCode(this.filmCode)
                .genre(this.genre)
                .filmName(this.filmName)
                .isPlaying(this.isPlaying)
                .build();
    }
}
