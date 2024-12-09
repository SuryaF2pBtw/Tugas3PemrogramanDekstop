/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main;

/**
 *
 * @author USER
 */
import java.io.*;
import java.util.*;

public class Menu {
    private final ArrayList<MenuItem> listMenu;

    public Menu() {
        this.listMenu = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        listMenu.add(item);
        Utils.MenuHandler.sortMenu(listMenu);
    }

    public void showMenu() {
        if (listMenu.isEmpty()) {
            System.out.println("Menu kosong.");
        } else {
            System.out.println("\n========| Daftar Menu |========");
            int number = 1;

            List<MenuItem> sortedMenu = new ArrayList<>(listMenu);
            Utils.MenuHandler.sortMenuByCategoryAndName(sortedMenu);

            Map<String, List<MenuItem>> categorizedMenu = Utils.categorizeMenu(sortedMenu);

            for (Map.Entry<String, List<MenuItem>> entry : categorizedMenu.entrySet()) {
                String category = entry.getKey();
                List<MenuItem> items = entry.getValue();

                System.out.println("\n==========| " + category + " |==========");

                for (MenuItem menuItem : items) {
                    System.out.print(number + ". ");
                    menuItem.tampilMenu();
                    number++;
                }
            }
            System.out.println("\n===============================");
        }
    }

    public static void saveMenu(List<MenuItem> listMenu, String fileName) {
        try {
            Utils.saveMenuToFile(listMenu, fileName);
        } catch (IOException e) {
            System.out.println("Menu Gagal disimpan");
        }
    }

    public void loadMenu(List<MenuItem> listMenu, String fileName) {
        try {
            Utils.loadMenuFromFile(listMenu, fileName);
        } catch (IOException e) {
            System.out.println("Menu Gagal dimuat");
        }
    }

    public boolean isItemExists(String nama) {
        return listMenu.stream().anyMatch(item -> item.getName().equalsIgnoreCase(nama));
    }

    public ArrayList<MenuItem> getListMenu() {
        return listMenu;
    }

}



