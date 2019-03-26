package com.dh.service.order;

import com.dh.entity.order.Order;
import java.util.List;

public interface OrderService {

    Long insert(Order entity);

    Order getByOrderId(long orderId);

    List<Order> listByIds(List<Long> ids);

}
