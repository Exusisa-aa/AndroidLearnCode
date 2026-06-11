package com.film.backend.service.impl;

import com.film.backend.dto.OrderDTO;
import com.film.backend.entity.Hall;
import com.film.backend.entity.Movie;
import com.film.backend.entity.Order;
import com.film.backend.entity.Schedule;
import com.film.backend.entity.Ticket;
import com.film.backend.mapper.HallMapper;
import com.film.backend.mapper.MovieMapper;
import com.film.backend.mapper.OrderMapper;
import com.film.backend.mapper.ScheduleMapper;
import com.film.backend.mapper.TicketMapper;
import com.film.backend.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.core.util.IdUtil;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private TicketMapper ticketMapper;
    
    @Autowired
    private HallMapper hallMapper;
    
    @Autowired
    private MovieMapper movieMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(OrderDTO orderDTO) {
        // ... (existing code)
        // 1. Get Schedule Info
        Schedule schedule = scheduleMapper.selectById(orderDTO.getScheduleId());
        if (schedule == null) {
            throw new RuntimeException("Schedule not found");
        }

        // 2. Calculate Total Price
        BigDecimal price = schedule.getPrice();
        BigDecimal totalAmount = price.multiply(new BigDecimal(orderDTO.getSeatIds().size()));

        // 3. Create Order
        Order order = new Order();
        order.setOrderNo(IdUtil.getSnowflakeNextIdStr());
        order.setUserId(orderDTO.getUserId());
        order.setScheduleId(orderDTO.getScheduleId());
        order.setTotalAmount(totalAmount);
        order.setStatus(0); // Pending
        order.setCreateTime(LocalDateTime.now());
        
        save(order);

        // 4. Create Tickets (and Lock Seats via Unique Constraint)
        List<Long> seatIds = orderDTO.getSeatIds();
        List<String> seatLabels = orderDTO.getSeatLabels();

        for (int i = 0; i < seatIds.size(); i++) {
            Ticket ticket = new Ticket();
            ticket.setOrderId(order.getId());
            ticket.setScheduleId(schedule.getId());
            ticket.setSeatId(seatIds.get(i));
            ticket.setSeatLabel(seatLabels.get(i)); // e.g. "5排6座"
            ticket.setPrice(price);
            ticket.setStatus(0); // Unused
            ticket.setCreateTime(LocalDateTime.now());

            try {
                ticketMapper.insert(ticket);
            } catch (DuplicateKeyException e) {
                throw new RuntimeException("Seat " + seatLabels.get(i) + " has been sold!");
            }
        }

        return order;
    }

    @Override
    public List<OrderDTO> getOrdersByUser(Long userId) {
        // ... (existing implementation)
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId);
        wrapper.orderByDesc(Order::getCreateTime);
        List<Order> orders = list(wrapper);
        
        List<OrderDTO> dtos = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO dto = new OrderDTO();
            BeanUtils.copyProperties(order, dto);
            
            Schedule schedule = scheduleMapper.selectById(order.getScheduleId());
            if (schedule != null) {
                dto.setStartTime(schedule.getStartTime());
                
                Hall hall = hallMapper.selectById(schedule.getHallId());
                if (hall != null) {
                    dto.setHallName(hall.getName());
                }
                
                Movie movie = movieMapper.selectById(schedule.getMovieId());
                if (movie != null) {
                    dto.setMovieTitle(movie.getTitle());
                }
            }
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public boolean payOrder(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            return false;
        }
        // Check if already paid
        if (order.getStatus() == 1) {
            return true;
        }
        
        order.setStatus(1); // Paid
        order.setPayTime(LocalDateTime.now());
        
        // In real world, we might want to update ticket status to 1 (Sold/Valid) if logic requires,
        // but here tickets are created as 0 (Unused) which is fine for "Paid" state.
        
        return updateById(order);
    }
}
