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
    private int receiptId; // Обязательно
    private int clientId; // Обязательно
    private LocalDate orderDate; // Обязательно
    private int statusId; // Обязательно

    public Receipt(int receiptId, int clientId, LocalDate orderDate, int statusId) {
        this.receiptId = receiptId;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.statusId = statusId;
    }
}
