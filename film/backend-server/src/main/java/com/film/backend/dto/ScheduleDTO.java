package com.film.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ScheduleDTO {
    private Long id;
    private Long movieId;
    private Long hallId;
    private String hallName; // Added field
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal price;
}
