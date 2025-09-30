package org.example.repository;

import org.example.entities.Client;

import java.util.*;

public class ClientRepositoryImpl implements ClientRepository {
    private final Map<Integer, Client> clients = new HashMap<>();
    private int idCounter = 1; // "авто инкремент" для clientId

    //TODO: Можно сделать через лямбда-выражение
    @Override
    public Client findByEmail(String email) {
        if (!isValidEmail(email)) {
//            throw new IllegalArgumentException("❌ Некорректный email: " + email);
            System.out.println("❌ Некорректный email: " + email);
        }
        for (Client client : clients.values()) {
            if (client.getEmail().equals(email))
                return client;
        }
        return null;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }


    @Override
    public void add(Client client) {
        if (client.getClientId() == 0) {
            client.setClientId(idCounter++);
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
    public void update(int id, Client newObject) {
        if (clients.containsKey(id)) {
            newObject.setClientId(id); // сохраняем id
            clients.put(id, newObject);
        } else {
//            throw new IllegalArgumentException("Client with id " + id + " does not exist");
            System.out.println("Client with id " + id + " does not exist");
        }
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
