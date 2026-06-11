package com.film.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.film.backend.dto.OrderDTO;
import com.film.backend.entity.Order;

import com.film.backend.dto.OrderDTO;
import java.util.List;

public interface IOrderService extends IService<Order> {
    Order createOrder(OrderDTO orderDTO);
    List<OrderDTO> getOrdersByUser(Long userId);
    boolean payOrder(Long orderId);
}
