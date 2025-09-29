package org.example.cli.receipt;

import org.example.cli.Command;
import org.example.entities.Receipt;
import org.example.service.ReceiptService;
import org.example.service.ReceiptServiceImpl;

import java.util.Scanner;

public class AddReceiptCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);
    private final ReceiptServiceImpl receiptService;

    public AddReceiptCommand(ReceiptServiceImpl receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public void execute() {
        System.out.println("Enter the receipt number: ");
        String receiptNumber = scanner.nextLine();

        System.out.println("Enter the receipts client identifier: ");
        String clientIdentifier = scanner.nextLine();

        Receipt receipt = new Receipt(Integer.parseInt(receiptNumber), Integer.parseInt(clientIdentifier));
        receiptService.add(receipt);

        System.out.println("✅Receipt added successfully!");
    }

    @Override
    public String getDescription() {
        return "Add a receipt";
    }
}
