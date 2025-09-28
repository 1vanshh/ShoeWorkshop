package org.example.repository;

import org.example.entities.Receipt;

import java.time.LocalDate;
import java.util.List;

public interface ReceiptRepository extends Repository<Receipt> {

    List<Receipt> findByDate(LocalDate date);
}
