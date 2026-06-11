package com.film.backend.dto;

import lombok.Data;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    // Request fields
    private Long userId;
    private Long scheduleId;
    private List<Long> seatIds;
    private List<String> seatLabels;
    
    // Response fields
    private Long id;
    private String orderNo;
    private BigDecimal totalAmount;
    private Integer status;
    private LocalDateTime createTime;
    
    private String movieTitle;
    private String hallName;
    private LocalDateTime startTime;
}
