package com.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurant.entity.Dish;
import com.restaurant.entity.DishFlavor;
import java.util.List;

public interface DishService extends IService<Dish> {
    List<Dish> listWithFlavor(Dish dish);
}
