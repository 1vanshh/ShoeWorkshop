package org.example.service;

import org.example.entities.Service;
import org.example.repository.ServiceRepositoryImpl;

import java.util.List;

public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepositoryImpl serviceRepository;

    public ServiceServiceImpl(ServiceRepositoryImpl serviceRepository) {
        this.serviceRepository = serviceRepository;
    }


    @Override
    public void add(Service object) {
        if (serviceRepository.getById(object.getServiceId()) != null) {
            throw new IllegalArgumentException("Service already exists");
        }
        serviceRepository.add(object);
    }

    @Override
    public void update(int id, Service newObject) {
        if (serviceRepository.getById(id) == null) {
            throw new IllegalArgumentException("Service does not exist");
        }
        serviceRepository.update(id, newObject);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Service getById(int id) {
        return null;
    }

    @Override
    public List<Service> getAll() {
        return List.of();
    }
}
