package org.example.cli.client;

import org.example.cli.Command;
import org.example.entities.Client;
import org.example.service.ClientService;

import java.util.Scanner;

public class UpdateClientCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientService clientService;

    public UpdateClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.println("Please enter clients id to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        Client existing = clientService.getById(id);
        if (existing == null) {
            System.out.println("Client with id " + id + " does not exist");
        }

        System.out.println("Current client data: " + existing);
        System.out.println("Enter new data (leave it empty so as not to change it.)");

        System.out.print("Full name (" + existing.getFullName() + "): ");
        String fullName = scanner.nextLine();
        if (!fullName.isEmpty()) existing.setFullName(fullName);

        System.out.print("Address (" + existing.getAddress() + "): ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) existing.setAddress(address);

        System.out.print("Phone number (" + existing.getPhoneNumber() + "): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) existing.setPhoneNumber(phone);

        System.out.print("Email (" + existing.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) existing.setEmail(email);

        clientService.update(id, existing);
        System.out.println("✅ Клиент успешно обновлён!");
    }

    @Override
    public String getDescription() {
        return "Update client's information";
    }
}
