package TeamB.Bioskop6.entity;

import TeamB.Bioskop6.dto.ScheduleResponseDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schaduleId;

    @ManyToOne
    @JoinColumn(name = "film_code")
    public Film film;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate Date;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private Double Price;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ScheduleResponseDTO convertToResponse(){
        return ScheduleResponseDTO.builder()
                .filmCode(this.film.getFilmCode())
                .Date(this.Date)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .Price(this.Price)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "schaduleId=" + schaduleId +
                ", film=" + film +
                ", Date=" + Date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", Price=" + Price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
