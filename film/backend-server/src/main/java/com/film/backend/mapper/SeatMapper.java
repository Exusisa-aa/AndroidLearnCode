package com.film.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.film.backend.entity.Seat;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeatMapper extends BaseMapper<Seat> {
}
