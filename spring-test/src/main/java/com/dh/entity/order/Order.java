package com.dh.entity.order;

import lombok.Data;

@Data
public class Order {

    private long orderId;
    private long userId;
    private String status;
}
