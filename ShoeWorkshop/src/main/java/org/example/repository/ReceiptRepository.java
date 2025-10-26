package org.example.repository;

import org.example.entities.Receipt;

import java.time.LocalDate;
import java.util.List;

public interface ReceiptRepository extends Repository<Receipt> {

    List<Receipt> findByClientId(int clientId);
    void updateStatus(int receiptId, int newStatusId);
}
