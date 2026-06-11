package com.film.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.film.backend.entity.Hall; // Need to create Hall entity first
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HallMapper extends BaseMapper<Hall> {
}
