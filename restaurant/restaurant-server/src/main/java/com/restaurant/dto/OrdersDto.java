package com.restaurant.dto;

import com.restaurant.entity.OrderDetail;
import com.restaurant.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private List<OrderDetail> orderDetails;
}
