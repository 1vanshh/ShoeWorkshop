package org.example.service;

import org.example.entities.Client;
import org.example.repository.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> getAllClients() {
        return List.of();
    }

    @Override
    public Client getClientById(int id) {
        return null;
    }

    @Override
    public void creatClient(Client client) {

    }

    @Override
    public void deleteClient(Client client) {

    }
}
