package com.film.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String orderNo;
    private Long userId;
    private Long scheduleId;
    private BigDecimal totalAmount;
    private Integer status; // 0-Pending, 1-Paid, 2-Cancelled
    private LocalDateTime payTime;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
