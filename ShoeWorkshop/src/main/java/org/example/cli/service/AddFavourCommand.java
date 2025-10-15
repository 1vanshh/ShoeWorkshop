package org.example.cli.service;

import org.example.cli.Command;
import org.example.entities.Favour;
import org.example.service.FavourServiceImpl;

import java.util.Scanner;

public class AddFavourCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);
    private final FavourServiceImpl favourService;

    public AddFavourCommand(FavourServiceImpl favourService) {
        this.favourService = favourService;
    }

    @Override
    public void execute() {
        System.out.println("Enter favour name: ");
        String name = scanner.nextLine();

        System.out.println("Enter favour base price");
        String basePrice = scanner.nextLine();

        Favour favour = new Favour(1, name, basePrice);
        favourService.add(favour);
    }

    @Override
    public String getDescription() {
        return "Add a new favour";
    }
}
