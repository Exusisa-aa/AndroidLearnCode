package com.film.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.film.backend.common.Result;
import com.film.backend.entity.Schedule;
import com.film.backend.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.film.backend.dto.ScheduleDTO;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private IScheduleService scheduleService;

    @GetMapping("/movie/{movieId}")
    public Result<List<ScheduleDTO>> getSchedulesByMovie(@PathVariable Long movieId) {
        return Result.success(scheduleService.getSchedulesWithHall(movieId));
    }
}
