package com.film.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_movie")
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String title;
    private String posterUrl;
    private String description;
    private String videoUrl;
    private LocalDate releaseDate;
    private Integer duration;
    private String director;
    private String actors;
    private BigDecimal rating;
    private Integer status; // 1-Hot, 2-Coming Soon, 0-Offline

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
