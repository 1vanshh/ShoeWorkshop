package org.example.service;

import org.example.entities.Client;
import org.example.repository.ClientRepository;
import org.example.repository.ClientRepositoryImpl;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private final ClientRepositoryImpl clientRepository;

    @Override
    public void update(int id, Client newClient) {
        Client existing = clientRepository.getById(id);
        if (existing == null) {
            System.out.println("Client with id" + id + " does not exist");
        }
        clientRepository.update(id, newClient);
    }

    @Override
    public void add(Client client) {
        if (clientRepository.findByEmail(client.getEmail()) != null) {
//            throw new IllegalArgumentException("Клиент с таким email уже существует!");
            System.out.println("Клиент с таким email уже существует!");
        }
        clientRepository.add(client);
    }

    public ClientServiceImpl(ClientRepositoryImpl clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    @Override
    public Client getById(int id) {
        return clientRepository.getById(id);
    }

    @Override
    public void delete(int id) {
        //TODO: Добавить проверку на текущие заказы (Если они есть, нужно предупредить)
        clientRepository.delete(id);
    }

    @Override
    public Client findByEmail(String email) {
        Client client = clientRepository.findByEmail(email);
        if (client == null) System.out.println("<UNK> <UNK> <UNK> email <UNK> <UNK> <UNK>");
        return client;
    }
}
