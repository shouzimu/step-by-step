package com.dh.repository.order;

import com.dh.entity.order.OrderItem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemRepository {

    Long insert(OrderItem entity);

    List<OrderItem> listByOrderId(long orderId);

}
