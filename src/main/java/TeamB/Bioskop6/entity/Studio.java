package TeamB.Bioskop6.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import TeamB.Bioskop6.constant.EStudioType;
import TeamB.Bioskop6.dto.StudioResponseDTO;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studioId;

    private String studioName;

    @Enumerated(EnumType.STRING)
    private EStudioType type;

    private String capasity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public StudioResponseDTO convertToResponse(){
        return StudioResponseDTO.builder()
            .studioId(this.studioId)
            .studioName(this.studioName)
            .type(this.type)
            .capasity(this.capasity)
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
            .build();
    }
}
