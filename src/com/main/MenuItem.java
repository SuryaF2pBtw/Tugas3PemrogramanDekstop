/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main;

/**
 *
 * @author USER
 */
public abstract class MenuItem {
    private String name;
    private double price;
    private String category;

    public MenuItem(String name, double price, String category) {
        this.name = name.toLowerCase();
        this.price = price;
        this.category = category;
    }
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public abstract void tampilMenu();
@Override
    public String toString() {
        return getClass().getSimpleName() + ": " + name + ", Harga: " + price + ", Kategori: " + category;
    }
}