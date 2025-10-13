package org.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Service {
    private int serviceId; // Обязательно
    private String serviceName; // Обязательно
    private String basePrice; // Обязательно

    public Service(int serviceId, String serviceName, String basePrice) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.basePrice = basePrice;
    }
}
