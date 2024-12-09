/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main;

/**
 *
 * @author USER
 */
public class OrderItem {
    private MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        if (menuItem == null) {
            throw new IllegalArgumentException("Menu item tidak boleh null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih besar dari 0.");
        }
        this.menuItem = menuItem;
        this.quantity = quantity;
    }
    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih besar dari 0.");
        }
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        double harga = menuItem instanceof Diskon ? ((Diskon) menuItem).getPriceAfterDiscount() : menuItem.getPrice();
        return harga * quantity;
    }
}

