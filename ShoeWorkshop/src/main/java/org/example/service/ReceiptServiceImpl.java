package org.example.service;

import org.example.entities.Favour;
import org.example.entities.OrderStatus;
import org.example.entities.Receipt;
import org.example.entities.ReceiptItem;
import org.example.repository.FavourRepositoryImpl;
import org.example.repository.OrderStatusRepositoryImpl;
import org.example.repository.ReceiptItemRepositoryImpl;
import org.example.repository.ReceiptRepositoryImpl;

import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepositoryImpl receiptRepository = new ReceiptRepositoryImpl();
    private final ReceiptItemRepositoryImpl receiptItemRepository = new ReceiptItemRepositoryImpl();
    private final OrderStatusRepositoryImpl orderStatusRepository = new OrderStatusRepositoryImpl();
    private final FavourRepositoryImpl favourRepository = new FavourRepositoryImpl();

    @Override
    public Receipt createReceipt(int clientId, int statusId) {

        Receipt receipt = new Receipt();
        receipt.setClientId(clientId);
        receipt.setStatusId(statusId);
        receiptRepository.add(receipt);
        return receipt;
    }

    @Override
    public void addItem(int receiptId, int favourId, int quantity, Double optionalDiscount) {

        Favour favour = favourRepository.findById(favourId);
        if (favour == null) {
            throw new IllegalArgumentException("Favour not found");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Double basePrice = favour.getBasePrice();
        Double unitPrice = basePrice;

        if (optionalDiscount != null) {
            if (optionalDiscount < 1.0) {
                //* если скидка в процентах (0.1 = 10%)
                unitPrice = basePrice * (1 - optionalDiscount);
            } else {
                //* если скидка абсолютная (например 100 рублей)
                unitPrice = basePrice - optionalDiscount;
            }
        }
        if (unitPrice < 0.0) {
            unitPrice = 0.0;
        }

        unitPrice = Math.round(unitPrice * 100.0) / 100.0;

        ReceiptItem item = new ReceiptItem();
        item.setReceiptId(receiptId);
        item.setFavourId(favourId);
        item.setQuantity(quantity);
        item.setPrice(unitPrice);

        receiptItemRepository.add(item);
    }


    @Override
    public Receipt getReceipt(int receiptId) {
        return receiptRepository.findById(receiptId);
    }

    @Override
    public Double calculateTotal(int receiptId) {
        List<ReceiptItem> items = receiptItemRepository.findByReceiptId(receiptId);

        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        return Math.round(total * 100.0) / 100.0;
    }

    @Override
    public void changeStatus(int receiptId, int newStatusId) {
        Receipt receipt = receiptRepository.findById(receiptId);
        if (receipt == null) {
            throw new IllegalArgumentException("Receipt not found");
        }
        OrderStatus orderStatus = orderStatusRepository.findById(newStatusId);
        if (orderStatus == null) {
            throw new IllegalArgumentException("Order status not found");
        }

        receiptRepository.updateStatus(receiptId, newStatusId);
    }

    @Override
    public void deleteReceipt(int receiptId) {
        receiptItemRepository.deleteByReceiptId(receiptId);
        receiptRepository.delete(receiptId);
    }

    @Override
    public void finalizeReceipt(int receiptId) {
        Receipt receipt = getReceipt(receiptId);
        if (receipt == null) {
            throw new IllegalArgumentException("Квитанция не найдена");
        }

        Double total = calculateTotal(receiptId);
        System.out.println("Итоговая сумма квитанции №" + receiptId + ": " + total);

        // меняем статус на "Выдан"
        OrderStatus readyStatus = orderStatusRepository.findByName("Выдан");
        if (readyStatus != null) {
            receiptRepository.updateStatus(receiptId, readyStatus.getStatusId());
        }
    }

    @Override
    public List<Receipt> getByClientId(int clientId) {
        return receiptRepository.findByClientId(clientId);
    }

    @Override
    public void add(Receipt object) {
        receiptRepository.add(object);
    }

    @Override
    public void update(int id, Receipt newObject) {
        Receipt receipt = receiptRepository.findById(id);
        if (receipt == null) {
            throw new IllegalArgumentException("Receipt not found");
        }
        receiptRepository.update(id, newObject);
    }

    @Override
    public void delete(int id) {
        Receipt receipt = receiptRepository.findById(id);
        if (receipt == null) {
            throw new IllegalArgumentException("Receipt not found");
        }
        receiptRepository.delete(id);
    }

    @Override
    public Receipt getById(int id) {
        return receiptRepository.findById(id);
    }

    @Override
    public List<Receipt> getAll() {
        return receiptRepository.findAll();
    }
}
