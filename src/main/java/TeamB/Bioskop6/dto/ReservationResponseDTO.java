package TeamB.Bioskop6.dto;

import lombok.*;

import java.time.LocalDateTime;

import TeamB.Bioskop6.entity.SeatDetail;
import TeamB.Bioskop6.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReservationResponseDTO {

    private Integer reservationId;

    private Boolean isActive;

    private SeatDetail seatDetail;

    private User user;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
