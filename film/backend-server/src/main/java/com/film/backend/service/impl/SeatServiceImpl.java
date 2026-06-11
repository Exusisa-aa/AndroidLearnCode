package com.film.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.film.backend.dto.SeatDTO;
import com.film.backend.entity.Schedule;
import com.film.backend.entity.Seat;
import com.film.backend.entity.Ticket;
import com.film.backend.mapper.ScheduleMapper;
import com.film.backend.mapper.SeatMapper;
import com.film.backend.mapper.TicketMapper;
import com.film.backend.service.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat> implements ISeatService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public List<SeatDTO> getSeatLayoutBySchedule(Long scheduleId) {
        // 1. Get Schedule -> Hall ID
        Schedule schedule = scheduleMapper.selectById(scheduleId);
        if (schedule == null) {
            throw new RuntimeException("Schedule not found");
        }

        // 2. Get All Seats in Hall
        LambdaQueryWrapper<Seat> seatWrapper = new LambdaQueryWrapper<>();
        seatWrapper.eq(Seat::getHallId, schedule.getHallId());
        List<Seat> seats = list(seatWrapper);

        // 3. Get Sold Tickets for this Schedule
        LambdaQueryWrapper<Ticket> ticketWrapper = new LambdaQueryWrapper<>();
        ticketWrapper.eq(Ticket::getScheduleId, scheduleId);
        ticketWrapper.in(Ticket::getStatus, 0, 1); // Unused or Used (not Refunded)
        List<Ticket> tickets = ticketMapper.selectList(ticketWrapper);
        
        Set<Long> soldSeatIds = tickets.stream()
                .map(Ticket::getSeatId)
                .collect(Collectors.toSet());

        // 4. Map to DTO
        List<SeatDTO> seatDTOs = new ArrayList<>();
        for (Seat seat : seats) {
            SeatDTO dto = new SeatDTO();
            dto.setId(seat.getId());
            dto.setRowNum(seat.getRowNum());
            dto.setColNum(seat.getColNum());
            dto.setType(seat.getType());
            dto.setLabel(seat.getRowNum() + "排" + seat.getColNum() + "座");

            if (seat.getStatus() == 0) {
                dto.setStatus(0); // Broken/Unavailable
            } else if (soldSeatIds.contains(seat.getId())) {
                dto.setStatus(2); // Sold
            } else {
                dto.setStatus(1); // Available
            }
            seatDTOs.add(dto);
        }
        return seatDTOs;
    }
}
