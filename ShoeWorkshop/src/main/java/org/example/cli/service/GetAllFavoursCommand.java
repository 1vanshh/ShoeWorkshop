package org.example.cli.service;

import org.example.cli.Command;
import org.example.service.FavourService;

public class GetAllFavoursCommand implements Command {
    private final FavourService favourService;

    public GetAllFavoursCommand(FavourService favourService) {
        this.favourService = favourService;
    }

    @Override
    public void execute() {
        System.out.println("=== Favours List ===");
        favourService.getAll().forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "Get all favours";
    }
}
