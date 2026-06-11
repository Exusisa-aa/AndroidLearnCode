package com.film.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.film.backend.entity.Schedule;

import com.film.backend.dto.ScheduleDTO;
import java.util.List;

public interface IScheduleService extends IService<Schedule> {
    List<ScheduleDTO> getSchedulesWithHall(Long movieId);
}
