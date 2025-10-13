package org.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Client {
    private int clientId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;

    public Client(int clientId, String fullName, String phoneNumber, String email, String address) {
        this.clientId = clientId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
}
