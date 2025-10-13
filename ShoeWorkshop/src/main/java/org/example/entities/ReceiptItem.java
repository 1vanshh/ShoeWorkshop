package org.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReceiptItem {
    private int itemId; // Обязательно
    private int receiptId; // Обязательно
    private int serviceId; // Обязательно
    private int price; // Обязательно
    private int quantity; // Обязательно
    
    public ReceiptItem(int itemId, int receiptId, int serviceId, int price, int quantity) {
        this.itemId = itemId;
        this.receiptId = receiptId;
        this.serviceId = serviceId;
        this.price = price;
        this.quantity = quantity;
    }
}
