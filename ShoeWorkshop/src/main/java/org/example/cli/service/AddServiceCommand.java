package org.example.cli.service;

import org.example.cli.Command;
import org.example.entities.Service;
import org.example.service.ServiceServiceImpl;

import java.util.Scanner;

public class AddServiceCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);
    private final ServiceServiceImpl service;

    public AddServiceCommand(ServiceServiceImpl service) {
        this.service = service;
    }

    @Override
    public void execute() {
        System.out.println("Enter service id: ");
        String serviceId = scanner.nextLine();

        System.out.println("Enter service name: ");
        String name = scanner.nextLine();

        System.out.println("Enter service base price");
        String basePrice = scanner.nextLine();

        Service service = new Service(Integer.parseInt(serviceId), name, basePrice);
    }

    @Override
    public String getDescription() {
        return "Add a new service";
    }
}
