package org.example.service;

import org.example.entities.Favour;
import org.example.repository.FavourRepository;
import org.example.repository.FavourRepositoryImpl;
import org.example.security.InputValidator;

import java.math.BigDecimal;
import java.util.List;
//! Добавить поиск по имени
public class FavourServiceImpl implements FavourService {

    private final FavourRepository favourRepository = new FavourRepositoryImpl();

    @Override
    public void add(Favour object) {
        if (object.getBasePrice() == null || object.getBasePrice() <= 0) {
            throw new IllegalArgumentException("Base price must be greater than zero");
        }
        favourRepository.add(object);
    }

    @Override
    public void update(int id, Favour newObject) {
        Favour existing = favourRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Favour not found");
        }
        if (newObject.getBasePrice() == null || newObject.getBasePrice() <= 0) {
            throw new IllegalArgumentException("Base price must be greater than zero");
        }
        favourRepository.update(id, newObject);
    }

    @Override
    public void delete(int id) {
        Favour existing = favourRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Favour not found");
        }
        favourRepository.delete(id);
    }

    @Override
    public Favour getById(int id) {
        return favourRepository.findById(id);
    }

    @Override
    public List<Favour> getAll() {
        return favourRepository.findAll();
    }
}
