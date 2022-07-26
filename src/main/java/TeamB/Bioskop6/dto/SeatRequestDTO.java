package TeamB.Bioskop6.dto;

import TeamB.Bioskop6.entity.Seat;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SeatRequestDTO {
    private Integer seatId;
    private Character seatCol;
    private Byte seatRow;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Seat convertToEntity() {
        return Seat.builder()
                .seatId(this.seatId)
                .seatCol(this.seatCol)
                .seatRow(this.seatRow)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
