package org.example.repository;

import org.example.entities.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceRepositoryImpl implements ServiceRepository {
    private final Map<Integer, Service> services = new HashMap<>();
    private int idCounter = 1;

    @Override
    public void add(Service service) {
        if (service.getServiceId() == 0) {
            service.setServiceId(idCounter++);
            idCounter++;
        }
        services.put(service.getServiceId(), service);
    }

    @Override
    public void removeAll() {
        services.clear();
        idCounter = 1;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(int id, Service newObject) {
        if (services.containsKey(id)) {
            newObject.setServiceId(id);
            services.put(id, newObject);
        } else {
            throw new IllegalArgumentException("Service with id " + id + " does not exist");
        }
    }

    @Override
    public Service getById(int id) {
        return services.get(id);
    }

    @Override
    public List<Service> getAll() {
        return new ArrayList<>(services.values());
    }
}
