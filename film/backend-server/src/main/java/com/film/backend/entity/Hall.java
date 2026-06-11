package com.film.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("t_hall")
public class Hall implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String name;
    @com.baomidou.mybatisplus.annotation.TableField("`rows`")
    private Integer totalRows;
    @com.baomidou.mybatisplus.annotation.TableField("`cols`")
    private Integer totalCols;
    private Integer type; // 1-2D, 2-3D, 3-IMAX

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
