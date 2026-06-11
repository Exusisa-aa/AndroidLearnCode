package com.restaurant.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // Name
    private String name;

    // Image
    private String image;

    // User ID
    private Long userId;

    // Dish ID
    private Long dishId;

    // Setmeal ID
    private Long setmealId;

    // Flavor
    private String dishFlavor;

    // Number
    private Integer number;

    // Amount
    private BigDecimal amount;

    // Create Time
    @com.baomidou.mybatisplus.annotation.TableField(fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT)
    private LocalDateTime createTime;

    // Update Time
    @com.baomidou.mybatisplus.annotation.TableField(fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private Long createUser;

    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private Long updateUser;
}
