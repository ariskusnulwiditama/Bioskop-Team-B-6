package TeamB.Bioskop6.dto;

import TeamB.Bioskop6.entity.Studio;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudioRequestDTO {
    private Integer studioId;

    private String studioName;

    private String type;

    private String capacity;

    public Studio convertToEntity(){
        return Studio.builder()
            .studioId(this.studioId)
            .studioName(this.studioName)
            .type(this.type)
            .capacity(this.capacity)
            .build();
    }
}
