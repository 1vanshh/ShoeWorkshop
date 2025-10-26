package org.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Favour {
    private int favourId; // Обязательно
    private String favourName; // Обязательно
    private Double basePrice; // Обязательно

    public Favour(int favourId, String favourName, Double basePrice) {
        this.favourId = favourId;
        this.favourName = favourName;
        this.basePrice = basePrice;
    }
}
