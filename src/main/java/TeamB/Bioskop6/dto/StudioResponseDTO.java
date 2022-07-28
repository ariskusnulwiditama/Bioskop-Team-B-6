package TeamB.Bioskop6.dto;

import java.time.LocalDateTime;

import TeamB.Bioskop6.constant.EStudioType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudioResponseDTO {
    private Integer studioId;

    private String studioName;

    private EStudioType type;

    private String capasity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
