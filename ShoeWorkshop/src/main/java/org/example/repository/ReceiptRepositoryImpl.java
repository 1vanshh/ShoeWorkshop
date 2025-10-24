package org.example.repository;

import org.example.entities.Receipt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptRepositoryImpl implements ReceiptRepository {
    private final Map<Integer, Receipt> receipts = new HashMap<>();
    private int idCounter = 1;

    //TODO: Stream API
    @Override
    public List<Receipt> findByDate(LocalDate date) {
        ArrayList<Receipt> receiptsAns = new ArrayList<>();

        for (Receipt receipt : receipts.values()) {
            if (receipt.getOrderDate().equals(date)) {
                receiptsAns.add(receipt);
            }
        }
        return receiptsAns;
    }

    @Override
    public void add(Receipt object) {
        if (object.getReceiptId() == 0) {
            object.setReceiptId(idCounter);
            idCounter++;
        }
        receipts.put(object.getReceiptId(), object);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(int id, Receipt newObject) {
        if (receipts.containsKey(id)) {
            newObject.setReceiptId(id);
            receipts.put(id, newObject);
        } else {
            throw new IllegalArgumentException("Receipt with id " + id + " does not exist");
        }
    }

    @Override
    public Receipt findById(int id) {
        return receipts.get(id);
    }

    @Override
    public List<Receipt> findAll() {
        return new ArrayList<>(receipts.values());
    }
}
