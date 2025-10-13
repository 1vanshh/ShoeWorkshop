package org.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderStatus {
    private int statusId; // Обязательно
    private String statusName; // Обязательно

    public OrderStatus(int statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }
}
