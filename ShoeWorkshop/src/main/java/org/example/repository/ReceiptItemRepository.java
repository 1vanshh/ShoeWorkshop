package org.example.repository;

import org.example.entities.ReceiptItem;

import java.util.List;

public interface ReceiptItemRepository extends Repository<ReceiptItem> {

    List<ReceiptItem> findByReceiptID(int receiptID);
}
