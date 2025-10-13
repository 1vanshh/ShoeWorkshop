package org.example.repository;

import org.example.entities.Client;

import java.util.*;

public class ClientRepositoryImpl implements ClientRepository {
    private final Map<Integer, Client> clients = new HashMap<>();
    private int idCounter = 1; // "авто инкремент" для clientId

    //TODO: Можно сделать через лямбда-выражение
    @Override
    public Client findByEmail(String email) {
        for (Client client : clients.values()) {
            if (client.getEmail().equals(email))
                return client;
        }
        return null;
    }

    @Override
    public void add(Client client) {
        if (client.getClientId() == 0) {
            client.setClientId(idCounter);
            idCounter++;
        }
        clients.put(client.getClientId(), client);
    }

    @Override
    public void removeAll() {
        clients.clear();
        idCounter = 1;
    }

    @Override
    public void delete(int id) {
        clients.remove(id);
    }

    @Override
    public void update(int id, Client newObject) {
        clients.put(id, newObject);
    }

    @Override
    public Client getById(int id) {
        return clients.get(id);
    }

    @Override
    public List<Client> getAll() {
        return new ArrayList<>(clients.values());
    }
}
