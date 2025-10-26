package org.example.cli.client;

import org.example.cli.Command;
import org.example.entities.Client;
import org.example.service.ClientService;
import org.example.service.ClientServiceImpl;

import java.util.Scanner;

public class AddClientCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientServiceImpl clientService;

    public AddClientCommand(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.println("Please enter the client full name: ");
        String fullName = scanner.nextLine();

        System.out.println("Please enter the client phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.println("Please enter the client email: ");
        String email = scanner.nextLine();

        System.out.println("Please enter the client address: ");
        String address = scanner.nextLine();

        Client client = new Client.Builder(fullName, address).phoneNumber(phoneNumber).email(email).build();
        clientService.add(client);

        System.out.println("✅Client added successfully!");
    }

    @Override
    public String getDescription() {
        return "Add a new client";
    }
}
