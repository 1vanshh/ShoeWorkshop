package org.example.cli.client;

import org.example.cli.Command;
import org.example.entities.Client;
import org.example.service.ClientService;
import org.example.service.ClientServiceImpl;

import java.util.Scanner;

public class GetClientByEmailCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientServiceImpl clientService;

    public GetClientByEmailCommand(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.print("Enter Client Email: ");
        String clientEmail = scanner.nextLine();

        Client client = clientService.findByEmail(clientEmail);
        if (client == null) {
            System.out.println("No client with that email " + clientEmail);
        } else {
            System.out.println("Client with its Email: " + client);
        }
    }

    @Override
    public String getDescription() {
        return "Shows a client by its Email";
    }
}
