package com.dh.service.impl;

import com.dh.entity.order.OrderItem;
import com.dh.repository.order.OrderItemRepository;
import com.dh.service.order.OrderItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Long insert(OrderItem entity) {
        return orderItemRepository.insert(entity);
    }

    @Override
    public List<OrderItem> listByOrderId(long orderId) {
        return orderItemRepository.listByOrderId(orderId);
    }
}
