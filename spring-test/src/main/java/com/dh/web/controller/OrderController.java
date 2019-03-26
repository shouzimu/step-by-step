package com.dh.web.controller;

import com.dh.bean.IdUtil;
import com.dh.entity.order.Order;
import com.dh.service.order.OrderItemService;
import com.dh.service.order.OrderService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order add(Order o) {
        o.setOrderId(IdUtil.id());
        o.setUserId(IdUtil.snowflakeId());
        orderService.insert(o);
        o.setStatus("Y");
        return o;
    }

    @GetMapping("/one/{orderId}")
    public Order get(@PathVariable("orderId") long orderId) {
        return orderService.getByOrderId(orderId);
    }


    @GetMapping("/ids")
    public List<Order> ids(String ids) {
        List<Long> idsArry = new ArrayList<>();
        if (null != ids) {
            idsArry = Arrays.asList(ids.split(",")).stream().map(t -> Long.parseLong(t)).collect(Collectors.toList());
        }
        return orderService.listByIds(idsArry);
    }

}
