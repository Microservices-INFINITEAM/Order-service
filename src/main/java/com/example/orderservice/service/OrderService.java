package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.vo.ResponseMusic;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);

    OrderDto deleteByOrderId(String orderId);

    ResponseMusic getMusic(String musicId);

    void deleteUsersOrder(String userId);
}
