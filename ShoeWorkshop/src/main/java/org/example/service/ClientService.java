package org.example.service;

import org.example.entities.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();
    Client getClientById(int id);
    void creatClient(Client client);
    void deleteClient(Client client);
}
