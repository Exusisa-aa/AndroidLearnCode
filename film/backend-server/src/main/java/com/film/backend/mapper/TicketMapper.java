package com.film.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.film.backend.entity.Ticket; // Need to create Ticket entity
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketMapper extends BaseMapper<Ticket> {
}
