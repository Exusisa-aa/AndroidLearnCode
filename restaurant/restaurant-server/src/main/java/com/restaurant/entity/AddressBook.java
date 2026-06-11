package com.restaurant.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Address Book
 */
@Data
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // User ID
    private Long userId;

    // Consignee
    private String consignee;

    // Phone Number
    private String phone;

    // Gender 0: Female, 1: Male
    private String sex;

    // Province Code
    private String provinceCode;

    // Province Name
    private String provinceName;

    // City Code
    private String cityCode;

    // City Name
    private String cityName;

    // District Code
    private String districtCode;

    // District Name
    private String districtName;

    // Detailed Address
    private String detail;

    // Label
    private String label;

    // Is Default 0: No, 1: Yes
    private Integer isDefault;

    // Create Time
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // Update Time
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;



    // Create User
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    // Update User
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    private Integer isDeleted;
}
