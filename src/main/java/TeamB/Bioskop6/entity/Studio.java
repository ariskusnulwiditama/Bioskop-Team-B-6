package TeamB.Bioskop6.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    private String type;

    private String capacity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public StudioResponseDTO convertToResponse(){
        return StudioResponseDTO.builder()
            .studioId(this.studioId)
            .studioName(this.studioName)
            .type(this.type)
            .capacity(this.capacity)
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
            .build();
    }
}
