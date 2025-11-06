package org.example.service;

import org.example.entities.Receipt;
import org.example.entities.ReceiptItem;

import java.math.BigDecimal;
import java.util.List;

public interface ReceiptService extends Service<Receipt> {
    // создать квитанцию для клиента
    Receipt createReceipt(int clientId, int statusId);

    // добавить услугу в квитанцию (с количеством и возможной скидкой)
    void addItem(int receiptId, int favourId, int quantity, Double optionalDiscount);

    // вернуть квитанцию со всеми позициями
    Receipt getReceipt(int receiptId);

    // посчитать итоговую сумму
    Double calculateTotal(int receiptId);

    // сменить статус заказа
    void changeStatus(int receiptId, int newStatusId);

    // удалить квитанцию вместе с позициями
    void deleteReceipt(int receiptId);

    // завершить заказ и установить финальный статус
    void finalizeReceipt(int receiptId);

    // получить все квитанции клиента
    List<Receipt> getByClientId(int clientId);

    List<ReceiptItem> getItemsByReceiptId(int receiptId);
}
