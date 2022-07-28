package TeamB.Bioskop6.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReservationRequestDTO {
    private Integer reservationId;

    private Integer seatDetailId;

    private Integer userId;
    
    private Boolean isActive;
}
