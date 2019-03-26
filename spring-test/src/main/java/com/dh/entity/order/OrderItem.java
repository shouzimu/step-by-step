package com.dh.entity.order;

import lombok.Data;

@Data
public class OrderItem {

    private long orderItemId;
    private long orderId;
    private long userId;
    private String status;
}
