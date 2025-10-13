package org.example.cli.client;

import org.example.cli.Command;
import org.example.service.ClientService;

import java.sql.SQLOutput;
import java.util.Scanner;

public class UpdateClientCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientService clientService;
    private final String[] args = {"Full Name", "Phone Number", "Email", "Address"};

    public UpdateClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.println("Please enter clients id to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("What do you want to update?");
        for (int i = 0; i < args.length; i++) {
            System.out.println(i+1 + ". " + args[i]);
        }
        System.out.print("Choose arg to update: ");
        int arg = Integer.parseInt(scanner.nextLine());


    }

    @Override
    public String getDescription() {
        return "Update client information";
    }
}
