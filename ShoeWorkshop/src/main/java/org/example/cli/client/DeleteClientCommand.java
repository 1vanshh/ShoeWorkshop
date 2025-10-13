package org.example.cli.client;

import org.example.cli.Command;
import org.example.service.ClientServiceImpl;

import java.util.Scanner;

public class DeleteClientCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientServiceImpl clientService;

    public DeleteClientCommand(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.println("Please enter the client id you wish to delete:");
        int clientId = Integer.parseInt(scanner.nextLine());

        clientService.delete(clientId);
    }

    @Override
    public String getDescription() {
        return "Delete a client from the database";
    }
}
