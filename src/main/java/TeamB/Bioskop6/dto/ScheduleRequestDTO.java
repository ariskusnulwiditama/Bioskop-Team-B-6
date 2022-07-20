package TeamB.Bioskop6.dto;

import TeamB.Bioskop6.entity.Film;
import TeamB.Bioskop6.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleRequestDTO {

    private Film film;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate Date;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "HH:mm:ss")
    private LocalTime startTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "HH:mm:ss")
    private LocalTime endTime;

    private Double Price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Schedule convertToEntity(){
        return Schedule.builder()
                .film(this.film)
                .Date(this.Date)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .Price(this.Price)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
