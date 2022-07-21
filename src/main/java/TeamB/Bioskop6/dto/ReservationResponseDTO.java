package TeamB.Bioskop6.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReservationResponseDTO {

    private Integer reservationId;
    private Integer scheduleId;
    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
