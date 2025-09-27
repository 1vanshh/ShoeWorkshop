package org.example.entities;

public class ReceiptItem {
    private int itemId;
    private int receiptId;
    private int serviceId;
    private int price;
    private int quantity;

    public ReceiptItem() {
    }

    public ReceiptItem(int itemId, int receiptId, int serviceId, int price, int quantity) {
        this.itemId = itemId;
        this.receiptId = receiptId;
        this.serviceId = serviceId;
        this.price = price;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ReceiptItem{" +
                "itemId=" + itemId +
                ", receiptId=" + receiptId +
                ", serviceId=" + serviceId +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
