package com.film.backend.service;

import com.film.backend.dto.SeatDTO;
import java.util.List;

public interface ISeatService {
    List<SeatDTO> getSeatLayoutBySchedule(Long scheduleId);
}
