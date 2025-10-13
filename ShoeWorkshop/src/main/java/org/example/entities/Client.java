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
    private int clientId; // Обязательное
    private String fullName; // Обязательное
    private String address; // Обязательное
    private String phoneNumber; // Необязательное
    private String email; // Необязательное

    public static class Builder {
        private int clientId;
        private String fullName;
        private String address;

        private String phoneNumber = "-1";
        private String email = "-1";

        public Builder(int clientId, String fullName, String address) {
            this.clientId = clientId;
            this.fullName = fullName;
            this.address = address;
        }
        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Client build() {
            return new Client(this);
        }
    }

    private Client(Builder builder) {
        this.clientId = builder.clientId;
        this.fullName = builder.fullName;
        this.address = builder.address;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
    }
}
