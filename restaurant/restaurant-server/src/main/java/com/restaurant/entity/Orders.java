package com.restaurant.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Orders
 */
@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // Order Number
    private String number;

    // Status 1:Pending Payment 2:Pending Delivery 3:Delivered 4:Completed 5:Cancelled
    private Integer status;

    // User ID
    private Long userId;

    // Address Book ID
    private Long addressBookId;

    // Order Time
    private LocalDateTime orderTime;

    // Checkout Time
    private LocalDateTime checkoutTime;

    // Pay Method 1:WeChat 2:Alipay
    private Integer payMethod;

    // Amount
    private BigDecimal amount;

    // Remark
    private String remark;

    // User Name
    private String userName;

    // Phone
    private String phone;

    // Address
    private String address;

    // Consignee
    private String consignee;
    
    // Although the table has create_time/update_time, 
    // for Orders, orderTime/checkoutTime are main fields.
    // If table requires create_time, we should map it.
    // Based on error "Field 'create_time' doesn't have a default value", it IS required in DB.
    
    @com.baomidou.mybatisplus.annotation.TableField(fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT)
    private LocalDateTime createTime;

    @com.baomidou.mybatisplus.annotation.TableField(fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
