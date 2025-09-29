package org.example.service;

import org.example.entities.Client;

import java.util.List;

public interface ClientService extends Service<Client> {
    Client findByEmail(String email);
}
