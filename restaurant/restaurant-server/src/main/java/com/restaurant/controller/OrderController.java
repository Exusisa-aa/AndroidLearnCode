package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.BaseContext;
import com.restaurant.common.Result;
import com.restaurant.entity.Orders;
import com.restaurant.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.restaurant.dto.OrdersDto;
import com.restaurant.entity.OrderDetail;
import com.restaurant.service.OrderDetailService;
import org.springframework.beans.BeanUtils;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;
    
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * Submit Order
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders){
        log.info("Order Data: {}", orders);
        ordersService.submit(orders);
        return Result.success("Order submitted successfully");
    }

    /**
     * Get User Orders
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public Result<Page> userPage(int page, int pageSize){
        // Pagination
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrdersDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByDesc(Orders::getOrderTime);

        ordersService.page(pageInfo, queryWrapper);

        // Copy page info
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");

        List<Orders> records = pageInfo.getRecords();
        List<OrdersDto> list = records.stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(item, ordersDto);

            // Get Order ID
            Long orderId = item.getId();

            // Get Order Details
            LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OrderDetail::getOrderId, orderId);
            List<OrderDetail> orderDetails = orderDetailService.list(wrapper);

            ordersDto.setOrderDetails(orderDetails);
            return ordersDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return Result.success(dtoPage);
    }
}
