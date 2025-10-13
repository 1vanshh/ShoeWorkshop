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
    private int itemId;
    private int receiptId;
    private int serviceId;
    private int price;
    private int quantity;
    
    public ReceiptItem(int itemId, int receiptId, int serviceId, int price, int quantity) {
        this.itemId = itemId;
        this.receiptId = receiptId;
        this.serviceId = serviceId;
        this.price = price;
        this.quantity = quantity;
    }
}
