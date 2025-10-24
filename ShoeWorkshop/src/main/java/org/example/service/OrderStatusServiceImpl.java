package org.example.service;

import org.example.entities.OrderStatus;
import org.example.repository.OrderStatusRepository;

import java.util.List;

public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void add(OrderStatus object) {
        if (orderStatusRepository.findById(object.getStatusId()) != null) {
            throw new IllegalArgumentException("Order status already exists");
        }
        orderStatusRepository.add(object);
    }

    @Override
    public void update(int id, OrderStatus newObject) {
        OrderStatus existing = orderStatusRepository.findById(id);
        if (existing != null) {
            throw new IllegalArgumentException("Order status with id " + id + " already exists");
        }
        orderStatusRepository.update(id, newObject);
    }

    @Override
    public void delete(int id) {
        orderStatusRepository.findAll().removeIf(orderStatus -> orderStatus.getStatusId() == id);
    }

    @Override
    public OrderStatus getById(int id) {
        return orderStatusRepository.findById(id);
    }

    @Override
    public List<OrderStatus> getAll() {
        return orderStatusRepository.findAll();
    }
}
