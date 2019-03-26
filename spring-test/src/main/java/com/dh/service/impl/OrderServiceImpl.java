package com.dh.service.impl;

import com.dh.entity.order.Order;
import com.dh.repository.order.OrderRepository;
import com.dh.service.order.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Long insert(Order entity) {
        return orderRepository.insert(entity);
    }

    @Override
    public Order getByOrderId(long orderId) {
        return orderRepository.getByOrderId(orderId);
    }


    @Override
    public List<Order> listByIds(List<Long> ids) {
        return orderRepository.listByIds(ids);
    }
}
