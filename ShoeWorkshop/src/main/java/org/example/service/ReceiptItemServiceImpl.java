package org.example.service;

import org.example.entities.ReceiptItem;
import org.example.repository.ReceiptItemRepository;
import org.example.repository.ReceiptItemRepositoryImpl;

import java.util.List;

public class ReceiptItemServiceImpl implements ReceiptItemService {

    private final ReceiptItemRepositoryImpl receiptItemRepository = new ReceiptItemRepositoryImpl();

    @Override
    public void deleteByReceiptId(int receiptId) {
        receiptItemRepository.deleteByReceiptId(receiptId);
    }

    @Override
    public List<ReceiptItem> getByReceiptId(int receiptId) {
        return receiptItemRepository.findByReceiptId(receiptId);
    }

    @Override
    public void add(ReceiptItem object) {
        if (object.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (object.getPrice() < 0.0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        object.setPrice(Math.round(object.getPrice() * 100.0) / 100.0);

        receiptItemRepository.add(object);
    }

    @Override
    public void update(int id, ReceiptItem newObject) {
        ReceiptItem existing = receiptItemRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Receipt item not found");
        }

        if (newObject.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        receiptItemRepository.update(id, newObject);
    }

    @Override
    public void delete(int id) {
        ReceiptItem existing = receiptItemRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Receipt item not found");
        }
        receiptItemRepository.delete(id);
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
