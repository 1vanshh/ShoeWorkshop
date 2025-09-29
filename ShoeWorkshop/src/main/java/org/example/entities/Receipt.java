package org.example.entities;

import java.time.LocalDate;

public class Receipt {
    private int receiptId;
    private int clientId;
    private LocalDate orderDate;
    private int statusId;

    public Receipt() {
    }

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

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId=" + receiptId +
                ", clientId=" + clientId +
                ", orderDate=" + orderDate +
                ", statusId=" + statusId +
                '}';
    }
}
