package org.example.repository;

import java.util.List;

public interface Repository<T> {
    void add(T object);
    void removeAll();
    void delete(int id);
    void update(int id, T newObject);
    T getById(int id);
    List<T> getAll();
}
