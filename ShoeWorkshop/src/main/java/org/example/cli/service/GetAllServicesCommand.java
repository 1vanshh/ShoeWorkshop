package org.example.cli.service;

import org.example.cli.Command;
import org.example.service.ServiceService;

public class GetAllServicesCommand implements Command {
    private final ServiceService serviceService;

    public GetAllServicesCommand(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Override
    public void execute() {
        System.out.println("=== Services List ===");
        serviceService.getAll().forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "Get all services";
    }
}
