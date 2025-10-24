package org.example.repository;

import org.example.entities.Favour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavourRepositoryImpl implements FavourRepository {
    private final Map<Integer, Favour> favours = new HashMap<>();
    private int idCounter = 1;

    @Override
    public void add(Favour favour) {
        if (favour.getFavourId() == 0) {
            favour.setFavourId(idCounter);
            idCounter++;
        }
        favours.put(favour.getFavourId(), favour);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(int id, Favour newObject) {
        if (favours.containsKey(id)) {
            newObject.setFavourId(id);
            favours.put(id, newObject);
        } else {
            throw new IllegalArgumentException("Favour with id " + id + " does not exist");
        }
    }

    @Override
    public Favour findById(int id) {
        return favours.get(id);
    }

    @Override
    public List<Favour> findAll() {
        return new ArrayList<>(favours.values());
    }
}
