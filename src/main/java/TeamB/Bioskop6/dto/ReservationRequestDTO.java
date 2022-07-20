package TeamB.Bioskop6.dto;

import TeamB.Bioskop6.entity.Reservations;
import TeamB.Bioskop6.entity.Schedule;

import java.time.LocalDateTime;

public class ReservationRequestDTO {

    private Integer reservationId;
    private Schedule schedule;
    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Reservations convertToReservations(){
        return Reservations.builder()
                .reservationId(this.reservationId)
                .schedule(this.schedule)
                .isActive(this.isActive)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
