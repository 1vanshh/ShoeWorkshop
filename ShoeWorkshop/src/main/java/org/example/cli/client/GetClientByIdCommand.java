package org.example.cli.client;

import org.example.cli.Command;
import org.example.entities.Client;
import org.example.service.ClientService;
import org.example.service.ClientServiceImpl;

import java.util.Scanner;

public class GetClientByIdCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientServiceImpl clientService;

    public GetClientByIdCommand(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.print("Enter Client ID: ");
        int clientId = scanner.nextInt();

        Client client = clientService.getById(clientId);

        System.out.println("Client with its ID: " + client.toString());
    }

    @Override
    public String getDescription() {
        return "Shows a client by its ID";
    }
}
