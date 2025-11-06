package org.example.service;

import org.example.entities.Client;
import org.example.repository.ClientRepository;
import org.example.repository.ClientRepositoryImpl;
import org.example.security.InputValidator;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository = new ClientRepositoryImpl();

    @Override
    public Client findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return clientRepository.findByEmail(email);
    }

    @Override
    public List<Client> searchClients(String query, int limit, int offset) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }
        return clientRepository.searchClients(query, limit, offset);
    }

    @Override
    public void add(Client object) {
        if (!InputValidator.isValidEmail(object.getEmail())) {
            throw new IllegalArgumentException("Email is invalid");
        }
        if (!InputValidator.isValidPhone(object.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number is invalid");
        }
        if (!InputValidator.isSafeString(object.getFullName(), 100)) {
            throw new IllegalArgumentException("Full name is invalid");
        }

        clientRepository.add(object);
    }

    @Override
    public void update(int id, Client newObject) {
        Client existing = clientRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Client with id " + id + " not found");
        }

        if (!InputValidator.isValidEmail(newObject.getEmail())) {
            throw new IllegalArgumentException("Некорректный email");
        }
        if (!InputValidator.isValidPhone(newObject.getPhoneNumber())) {
            throw new IllegalArgumentException("Некорректный телефон");
        }
        clientRepository.update(id, newObject);
    }

    @Override
    public void delete(int id) {
        Client existing = clientRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Client with id " + id + " not found");
        }
        clientRepository.delete(id);
    }

    @Override
    public Client getById(int id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }
}
