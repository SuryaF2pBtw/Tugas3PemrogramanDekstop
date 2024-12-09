/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main;

/**
 *
 * @author USER
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        Pesanan pesanan = new Pesanan();

        menu.loadMenu(menu.getListMenu(),"menu.txt");
        Utils.clearFile("struk.txt");
        while (true) {
            MainMenu.showMainMenu();

            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    ManageItem.addItem(scanner,menu);
                    break;
                case 2:
                    menu.showMenu();
                    break;
                case 3:
                    CreateOrder.addItemToOrder(scanner, menu, pesanan);
                    pesanan = new Pesanan();
                    break;
                case 4:
                    Pesanan.displayOrder("struk.txt");
                    break;
                case 0:
                    System.out.println("Keluar dari program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
