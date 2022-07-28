package TeamB.Bioskop6.entity;

import TeamB.Bioskop6.dto.ReservationResponseDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(name = "seat_detail_id")
    private SeatDetail seatDetail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean isActive;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ReservationResponseDTO convertToResponse(){
        return ReservationResponseDTO.builder()
                .reservationId(this.reservationId)
                .seatDetail(this.seatDetail)
                .user(this.user)
                .isActive(this.isActive)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    @Override
    public String toString() {
        return "Reservation [createdAt=" + createdAt + ", isActive=" + isActive + ", reservationId=" + reservationId
                + ", seatDetail=" + seatDetail + ", updatedAt=" + updatedAt + ", user=" + user + "]";
    }


}
