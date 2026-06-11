package com.film.backend.controller;

import com.film.backend.common.Result;
import com.film.backend.dto.OrderDTO;
import com.film.backend.entity.Order;
import com.film.backend.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/create")
    public Result<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        return Result.success(orderService.createOrder(orderDTO));
    }

    @GetMapping("/user/{userId}")
    public Result<List<OrderDTO>> getOrdersByUser(@PathVariable Long userId) {
        return Result.success(orderService.getOrdersByUser(userId));
    }

    @PostMapping("/pay/{orderId}")
    public Result<Boolean> payOrder(@PathVariable Long orderId) {
        boolean success = orderService.payOrder(orderId);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error(500,"Payment failed or Order not found");
        }
    }
}
