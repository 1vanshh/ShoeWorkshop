package org.example.repository;

import org.example.entities.ReceiptItem;

import java.util.*;

public class ReceiptItemRepositoryImpl implements ReceiptItemRepository {
    private final Map<Integer, ReceiptItem> receiptItems = new HashMap<Integer, ReceiptItem>();
    private int idCounter = 1;

    @Override
    public void add(ReceiptItem object) {
        if (object.getItemId() == 0) {
            object.setItemId(idCounter++);
            idCounter++;
        }
        receiptItems.put(object.getItemId(), object);
    }

    @Override
    public void removeAll() {
        receiptItems.clear();
        idCounter = 1;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(int id, ReceiptItem newObject) {
        if (receiptItems.containsKey(id)) {
            newObject.setItemId(id);
            receiptItems.put(id, newObject);
        } else {
            throw new IllegalArgumentException("receiptItem not found");
        }
    }

    @Override
    public ReceiptItem getById(int id) {
        return receiptItems.get(id);
    }

    @Override
    public List<ReceiptItem> getAll() {
        return new ArrayList<ReceiptItem>(receiptItems.values());
    }
}
