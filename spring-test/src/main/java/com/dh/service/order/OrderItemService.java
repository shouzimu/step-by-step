package com.dh.service.order;

import com.dh.entity.order.OrderItem;
import java.util.List;

public interface OrderItemService {

    Long insert(OrderItem entity);

    List<OrderItem> listByOrderId(long orderId);

}
