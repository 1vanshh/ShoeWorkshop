package org.example.repository;

import org.example.entities.Client;

import java.util.List;

public interface ClientRepository extends Repository<Client> {

    Client findByEmail(String email);
}
