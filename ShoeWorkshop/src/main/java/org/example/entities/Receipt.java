package org.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Receipt {
    private int receiptId;
    private int clientId;
    private LocalDate orderDate;
    private int statusId;

    public Receipt(int receiptId, int clientId, LocalDate orderDate, int statusId) {
        this.receiptId = receiptId;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.statusId = statusId;
    }

    public Receipt(int receiptId, int clientId) {
        this.receiptId = receiptId;
        this.clientId = clientId;
        this.orderDate = LocalDate.now();
        this.statusId = statusId;
    }
}
