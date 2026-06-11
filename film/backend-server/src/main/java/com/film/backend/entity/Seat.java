package com.film.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("t_seat")
public class Seat implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long hallId;
    private Integer rowNum;
    private Integer colNum;
    private Integer type; // 1-Standard, 2-Couple, 0-Broken
    private Integer status; // 1-Available, 0-Unavailable

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
