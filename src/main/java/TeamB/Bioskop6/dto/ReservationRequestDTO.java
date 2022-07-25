package TeamB.Bioskop6.dto;

import TeamB.Bioskop6.entity.Reservation;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReservationRequestDTO {

    private Integer reservationId;
    
    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Reservation convertToEntity(){
        return Reservation.builder()
                .reservationId(this.reservationId)
//                .schedule(this.schedule)
                .isActive(this.isActive)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
