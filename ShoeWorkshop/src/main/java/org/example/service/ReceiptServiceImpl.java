package org.example.service;

import org.example.entities.Receipt;
import org.example.repository.ReceiptRepository;

import java.time.LocalDate;
import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {
    private final ReceiptRepository receiptRepository;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public List<Receipt> findByDate(LocalDate date) {
        return receiptRepository.findByDate(date);
    }

    @Override
    public void add(Receipt object) {
        if (receiptRepository.getById(object.getReceiptId()) != null) {
            throw new IllegalArgumentException("Receipt already exists");
        }
        receiptRepository.add(object);
    }

    @Override
    public void update(int id, Receipt newObject) {
        Receipt receipt = receiptRepository.getById(id);
        if (receipt != null) {
            throw new IllegalArgumentException("receipt already exists");
        }
        receiptRepository.update(id, newObject);
    }

    @Override
    public void delete(int id) {
        receiptRepository.getAll().remove(id);
    }

    @Override
    public Receipt getById(int id) {
        return receiptRepository.getById(id);
    }

    @Override
    public List<Receipt> getAll() {
        return receiptRepository.getAll();
    }
}
