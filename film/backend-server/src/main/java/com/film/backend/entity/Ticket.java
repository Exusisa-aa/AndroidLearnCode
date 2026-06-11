package com.film.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_ticket")
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long orderId;
    private Long scheduleId;
    private Long seatId;
    private String seatLabel;
    private BigDecimal price;
    private Integer status; // 0-Unused, 1-Used, 2-Refunded
    private String qrCode;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
