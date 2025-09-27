package org.example;

import org.example.entities.Client;
import org.example.entities.Receipt;

import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Receipt receipt = new Receipt();

        receipt.setOrderDate(LocalDate.now());

        System.out.println(receipt.getOrderDate());
    }
}