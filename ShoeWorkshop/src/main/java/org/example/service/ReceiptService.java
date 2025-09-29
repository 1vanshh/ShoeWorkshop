package org.example.service;

import org.example.entities.Receipt;

import java.time.LocalDate;
import java.util.List;

public interface ReceiptService extends Service<Receipt> {
    List<Receipt> findByDate(LocalDate date);
}
