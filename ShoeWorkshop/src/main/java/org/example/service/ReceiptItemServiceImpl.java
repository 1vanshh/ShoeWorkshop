package org.example.service;

import org.example.entities.ReceiptItem;
import org.example.repository.ReceiptItemRepository;

import java.util.List;

public class ReceiptItemServiceImpl implements ReceiptItemService {
    private final ReceiptItemRepository receiptItemRepository;

    public ReceiptItemServiceImpl(ReceiptItemRepository receiptItemRepository) {
        this.receiptItemRepository = receiptItemRepository;
    }

    @Override
    public void add(ReceiptItem object) {
        if (receiptItemRepository.findById(object.getItemId()) != null) {
            throw new IllegalArgumentException("Item already exists");
        }
        receiptItemRepository.add(object);
    }

    @Override
    public void update(int id, ReceiptItem newObject) {
        ReceiptItem existing = receiptItemRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("ReceiptItem with id " + id + " not found");
        }
        receiptItemRepository.update(id, newObject);
    }

    @Override
    public void delete(int id) {
        receiptItemRepository.findAll().remove(id);
    }

    @Override
    public ReceiptItem getById(int id) {
        return receiptItemRepository.findById(id);
    }

    @Override
    public List<ReceiptItem> getAll() {
        return receiptItemRepository.findAll();
    }
}
