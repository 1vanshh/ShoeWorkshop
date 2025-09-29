package org.example.cli.client;

import org.example.cli.Command;
import org.example.service.ClientService;
import org.example.service.ClientServiceImpl;


public class GetAllClientsCommand implements Command {
    private final ClientServiceImpl clientService;

    public GetAllClientsCommand(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.println("=== Client list ===");
        clientService.getAll().forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "Show all clients";
    }
}
