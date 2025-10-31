package org.example.service;

import org.example.entities.OrderStatus;

public interface OrderStatusService extends Service<OrderStatus> {

    OrderStatus findByName(String name);
}
