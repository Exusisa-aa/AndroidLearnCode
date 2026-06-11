package com.film.backend.dto;

import lombok.Data;

@Data
public class SeatDTO {
    private Long id;
    private Integer rowNum;
    private Integer colNum;
    private Integer type; // 1-Standard, 2-Couple
    private Integer status; // 0-Unavailable, 1-Available, 2-Sold
    private String label; // e.g., "1排1座"
}
