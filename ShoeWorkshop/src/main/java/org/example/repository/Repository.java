package org.example.repository;

import java.util.List;

public interface Repository<T> {
    T findById(int id);
    List<T> findAll();
    void add(T object);
    void update(int id, T newObject);
    void delete(int id);
}
