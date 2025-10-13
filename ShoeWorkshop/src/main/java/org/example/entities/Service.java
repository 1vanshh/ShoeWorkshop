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
    private int serviceId;
    private String serviceName;
    private String basePrice;

    public Service(int serviceId, String serviceName, String basePrice) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.basePrice = basePrice;
    }
}
