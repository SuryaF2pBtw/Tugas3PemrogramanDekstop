/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main;

/**
 *
 * @author USER
 */
public class Minuman extends MenuItem {
    public Minuman(String name, double price) {
        super(name, price,"Minuman");
    }
    
    @Override
    public void tampilMenu() {
        System.out.println(Utils.capitalizeFirst(getName()) + " - " + Utils.formatIDR(getPrice()));
    }
    
}
