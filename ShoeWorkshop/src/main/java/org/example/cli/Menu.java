package org.example.cli;

import org.example.cli.client.*;
import org.example.cli.receipt.AddReceiptCommand;
import org.example.cli.receipt.GetAllReceiptsCommand;
import org.example.cli.service.AddFavourCommand;
import org.example.cli.service.GetAllFavoursCommand;
import org.example.repository.*;
import org.example.service.ClientServiceImpl;
import org.example.service.ReceiptServiceImpl;
import org.example.service.FavourServiceImpl;

import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static Command[] commands;

    static {
        ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
        ClientServiceImpl clientService = new ClientServiceImpl(clientRepository);

        ReceiptRepositoryImpl receiptRepository = new ReceiptRepositoryImpl();
        ReceiptServiceImpl receiptService = new ReceiptServiceImpl(receiptRepository);

        FavourRepositoryImpl serviceRepository = new FavourRepositoryImpl();
        FavourServiceImpl serviceService = new FavourServiceImpl(serviceRepository);

        commands = new Command[] {
                new AddClientCommand(clientService),
                new GetAllClientsCommand(clientService),
                new GetClientByIdCommand(clientService),
                new GetClientByEmailCommand(clientService),
                new DeleteClientCommand(clientService),
                new UpdateClientCommand(clientService),

                new AddReceiptCommand(receiptService),
                new GetAllReceiptsCommand(receiptService),

                new AddFavourCommand(serviceService),
                new GetAllFavoursCommand(serviceService),
        };
    }


    public static void run() {
        while (true) {
            System.out.println("\nMenu:");
            for (int i = 0; i < commands.length; i++) {
                System.out.println((i + 1) + " - " + commands[i].getDescription());
            }
            System.out.println("0 - Exit");
            System.out.print("Choose a command: ");
            int choice = scanner.nextInt();

            if (choice == 0)
                break;

            if (choice > 0 || choice <= commands.length) {
                commands[choice-1].execute();
            } else
                System.out.println("Invalid choice");
        }
    }
}
