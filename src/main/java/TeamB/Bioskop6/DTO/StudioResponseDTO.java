package TeamB.Bioskop6.dto;

import java.time.LocalDateTime;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudioResponseDTO {
    private Integer studioId;

    private String studioName;

    private String type;

    private String capacity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
