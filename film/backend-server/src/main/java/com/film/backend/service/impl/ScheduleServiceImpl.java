package com.film.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.film.backend.entity.Schedule;
import com.film.backend.mapper.ScheduleMapper;
import com.film.backend.service.IScheduleService;
import org.springframework.stereotype.Service;

import com.film.backend.dto.ScheduleDTO;
import com.film.backend.entity.Hall;
import com.film.backend.mapper.HallMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements IScheduleService {

    @Autowired
    private HallMapper hallMapper;

    @Override
    public List<ScheduleDTO> getSchedulesWithHall(Long movieId) {
        LambdaQueryWrapper<Schedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Schedule::getMovieId, movieId);
        wrapper.orderByAsc(Schedule::getStartTime);
        List<Schedule> schedules = list(wrapper);
        
        List<ScheduleDTO> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleDTO dto = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, dto);
            
            Hall hall = hallMapper.selectById(schedule.getHallId());
            if (hall != null) {
                dto.setHallName(hall.getName());
            }
            dtos.add(dto);
        }
        return dtos;
    }
}
