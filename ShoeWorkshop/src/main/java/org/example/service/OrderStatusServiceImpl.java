package org.example.service;

import org.example.entities.OrderStatus;
import org.example.repository.OrderStatusRepository;
import org.example.repository.OrderStatusRepositoryImpl;

import java.util.List;

public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepositoryImpl orderStatusRepository = new OrderStatusRepositoryImpl();

    @Override
    public OrderStatus findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Status name cannot be empty");
        }
        return orderStatusRepository.findByName(name.trim());
    }

    @Override
    public void add(OrderStatus object) {
        if (object.getStatusName() == null || object.getStatusName().trim().isEmpty()) {
            throw new IllegalArgumentException("Status name cannot be empty");
        }
        orderStatusRepository.add(object);
    }

    @Override
    public void update(int id, OrderStatus newObject) {
        OrderStatus existing = orderStatusRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Status not found");
        }
        orderStatusRepository.update(id, newObject);
    }

    @Override
    public void delete(int id) {
        OrderStatus existing = orderStatusRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Status not found");
        }
        orderStatusRepository.delete(id);
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
