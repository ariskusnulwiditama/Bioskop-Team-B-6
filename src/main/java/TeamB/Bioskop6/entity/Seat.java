package TeamB.Bioskop6.entity;

import javax.persistence.*;

import TeamB.Bioskop6.dto.SeatResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Seat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer seatId;

  @Column(length = 1, nullable = false, name = "seat_col")
  private Character seatCol;

  @Column(length = 4, nullable = false, name = "seat_row")
  private Byte seatRow;

  @CreationTimestamp
  @Column(nullable = false, updatable = false, name = "created_at")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false, name = "updated_at")
  private LocalDateTime updatedAt;

  public SeatResponseDTO convertToResponse() {
    return SeatResponseDTO.builder()
            .seatId(this.seatId)
            .seatCol(this.seatCol)
            .seatRow(this.seatRow)
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
            .build();
  }

  @Override
  public String toString() {
    return "Seat{" +
            "seatId=" + seatId +
            ", seatCol=" + seatCol +
            ", seatRow=" + seatRow +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
  }
}