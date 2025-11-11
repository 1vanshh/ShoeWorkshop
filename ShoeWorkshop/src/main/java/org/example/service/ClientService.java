package org.example.service;

import org.example.entities.Client;

import java.util.List;

public interface ClientService extends Service<Client> {

    Client findByEmail(String email);
    List<Client> searchClients(String query, int limit, int offset);
    List<Client> getAllSortedByName(boolean asc);
}
