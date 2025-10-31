package org.example.service;

import org.example.entities.ReceiptItem;

import java.util.List;

public interface ReceiptItemService extends Service<ReceiptItem> {

    List<ReceiptItem> getByReceiptId (int receiptId);
    void deleteByReceiptId (int receiptId);
}
