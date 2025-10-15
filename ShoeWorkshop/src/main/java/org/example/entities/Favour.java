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
    private String serviceName; // Обязательно
    private String basePrice; // Обязательно

    public Favour(int favourId, String serviceName, String basePrice) {
        this.favourId = favourId;
        this.serviceName = serviceName;
        this.basePrice = basePrice;
    }
}
