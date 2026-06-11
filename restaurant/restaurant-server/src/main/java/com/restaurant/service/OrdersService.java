package com.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurant.entity.Orders;

public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
