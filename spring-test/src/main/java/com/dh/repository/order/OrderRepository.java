package com.dh.repository.order;

import com.dh.entity.order.Order;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderRepository {

    Long insert(Order entity);

    Order getByOrderId(long orderId);

    List<Order> listByIds(@Param("ids") List<Long> ids);

}
