package com.film.backend.controller;

import com.film.backend.common.Result;
import com.film.backend.dto.SeatDTO;
import com.film.backend.service.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat")
public class SeatController {

    @Autowired
    private ISeatService seatService;

    @GetMapping("/schedule/{scheduleId}")
    public Result<List<SeatDTO>> getSeatLayout(@PathVariable Long scheduleId) {
        return Result.success(seatService.getSeatLayoutBySchedule(scheduleId));
    }
}
