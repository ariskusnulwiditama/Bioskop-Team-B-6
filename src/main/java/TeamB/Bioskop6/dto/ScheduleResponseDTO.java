package TeamB.Bioskop6.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ScheduleResponseDTO {

    private Integer filmCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate Date;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    private Double Price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
