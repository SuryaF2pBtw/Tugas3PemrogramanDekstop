/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main;

/**
 *
 * @author USER
 */
import java.util.ArrayList;
import java.util.List;

public class Pesanan {
    private List<OrderItem> items;

    public Pesanan() {
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem menuItem, int quantity) {
        if (menuItem == null) {
            throw new IllegalArgumentException("Menu item tidak boleh null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih besar dari 0.");
        }

        for (OrderItem item : items) {
            if (item.getMenuItem().getName().equalsIgnoreCase(menuItem.getName())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
   
}

        items.add(new OrderItem(menuItem, quantity));
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double total() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public static void displayOrder(String fileName) {
        Utils.displayOrderFromFile(fileName);
    }
}

