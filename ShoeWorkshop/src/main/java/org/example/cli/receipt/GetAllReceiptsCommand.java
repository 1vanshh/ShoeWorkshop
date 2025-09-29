package org.example.cli.receipt;

import org.example.cli.Command;
import org.example.service.ReceiptServiceImpl;

public class GetAllReceiptsCommand implements Command {
    private final ReceiptServiceImpl receiptService;

    public GetAllReceiptsCommand(ReceiptServiceImpl receiptService) {
        this.receiptService = receiptService;
    }


    @Override
    public void execute() {
        System.out.println("=== Receipts List ===");
        receiptService.getAll().forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "Show all receipts";
    }
}
