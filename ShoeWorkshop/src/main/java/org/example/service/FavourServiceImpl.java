package org.example.service;

import org.example.entities.Favour;
import org.example.repository.FavourRepositoryImpl;

import java.util.List;

public class FavourServiceImpl implements FavourService {
    private final FavourRepositoryImpl serviceRepository;

    public FavourServiceImpl(FavourRepositoryImpl serviceRepository) {
        this.serviceRepository = serviceRepository;
    }


    @Override
    public void add(Favour object) {
        if (serviceRepository.getById(object.getFavourId()) != null) {
            throw new IllegalArgumentException("Service already exists");
        }
        serviceRepository.add(object);
    }

    @Override
    public void update(int id, Favour newObject) {
        if (serviceRepository.getById(id) == null) {
            throw new IllegalArgumentException("Service does not exist");
        }
        serviceRepository.update(id, newObject);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Favour getById(int id) {
        return null;
    }

    @Override
    public List<Favour> getAll() {
        return List.of();
    }
}
