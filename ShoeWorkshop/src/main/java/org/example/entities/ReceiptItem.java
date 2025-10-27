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
    private int favourId; // Обязательно
    private int price; // Обязательно
    private int quantity; // Обязательно
    
    public ReceiptItem(int itemId, int receiptId, int favourId, int price, int quantity) {
        this.itemId = itemId;
        this.receiptId = receiptId;
        this.favourId = favourId;
        this.price = price;
        this.quantity = quantity;
    }
}
