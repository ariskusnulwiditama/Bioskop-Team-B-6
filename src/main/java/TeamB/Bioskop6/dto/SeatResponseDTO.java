package TeamB.Bioskop6.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatResponseDTO {
    private Integer seatId;
    private Character seatCol;
    private Byte seatRow;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
