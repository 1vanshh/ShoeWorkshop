package org.example.service;

import java.util.List;

public interface Service<T> {
    void add(T object);
    void update(int id, T newObject);
    void delete(int id);
    T getById(int id);
    List<T> getAll();
}
