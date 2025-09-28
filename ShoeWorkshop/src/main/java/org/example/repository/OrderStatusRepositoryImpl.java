package org.example.repository;

import org.example.entities.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderStatusRepositoryImpl implements OrderStatusRepository {
    private final Map<Integer, OrderStatus> orderStatuses = new HashMap<>();
    private int idCounter = 1;

    @Override
    public void add(OrderStatus object) {
        if (object.getStatusId() == 0) {
            object.setStatusId(idCounter++);
            idCounter++;
        }
        orderStatuses.put(object.getStatusId(), object);
    }

    @Override
    public void removeAll() {
        orderStatuses.clear();
        idCounter = 1;
    }

    @Override
    public void update(int id, OrderStatus newObject) {
        if (orderStatuses.containsKey(id)) {
            newObject.setStatusId(id);
            orderStatuses.put(id, newObject);
        } else {
            throw new IllegalArgumentException("Order status does not exist");
        }
    }

    @Override
    public OrderStatus getById(int id) {
        return orderStatuses.get(id);
    }

    @Override
    public List<OrderStatus> getAll() {
        return new ArrayList<>(orderStatuses.values());
    }
}
