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

public class ManageItem {
    public static void addItem(Scanner scanner, Menu menu, int jenis) {
        boolean itemExists;
        String nama;
        double harga;

        String jenisItem = (jenis == 1) ? "Makanan" : (jenis == 2) ? "Minuman" : "Diskon";

        while (true) {
            System.out.printf("Masukkan nama %s: ", jenisItem);
            nama = scanner.nextLine();
            while (true) {
                System.out.printf("Masukkan harga %s: ", jenisItem);
                harga = scanner.nextDouble();
                scanner.nextLine();

                if (harga >= 0) {
                    break;
                } else {
                    System.out.println("Harga tidak boleh negatif. Silakan coba lagi.");
                }
            }

            itemExists = menu.isItemExists(nama);
            if (itemExists) {
                System.out.println("Item dengan nama '" + nama + "' sudah ada di menu. Silakan masukkan item lain.");
            } else {
                break;
            }
        }

        if (jenis == 1) {
            menu.addItem(new Makanan(nama, harga));
        } else if (jenis == 2) {
            menu.addItem(new Minuman(nama, harga));
        } else if (jenis == 3) {
            double diskon;
            while (true){
                System.out.print("Masukkan persentase diskon: ");
                diskon = scanner.nextDouble();
                scanner.nextLine();
                if (diskon >= 0 && diskon <= 100) {
                    break;
                } else {
                    System.out.println("Persentase diskon tidak valid. Harus antara 0 dan 100. Silakan coba lagi.");
                }
            }
            menu.addItem(new Diskon(nama, harga, diskon));
        }
            Menu.saveMenu(menu.getListMenu(), "menu.txt");
            System.out.println("Item berhasil ditambahkan");

        while (true) {
            System.out.printf("Apakah ingin menambah %s lagi? (y/n): ", jenisItem);
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y")) {
                System.out.printf("Menambahkan %s baru...\n", jenisItem);
                addItem(scanner, menu, jenis);
                return;
            } else if (response.equals("n")) {
                System.out.println("Kembali ke menu utama.");
                return;
            } else {
                System.out.println("Jawaban tidak valid. Silakan ketik 'y' untuk ya atau 'n' untuk tidak.");
            }
        }
    }

    public static void addItem(Scanner scanner, Menu menu) {
        int jenis;

        while (true) {
            System.out.println("Pilih jenis item:");
            System.out.println("1. Makanan");
            System.out.println("2. Minuman");
            System.out.println("3. Diskon");
            System.out.println("0. Kembali ke menu utama");
            System.out.print("Masukkan pilihan: ");

            if (scanner.hasNextInt()) {
                jenis = scanner.nextInt();
                scanner.nextLine();

                if (jenis == 0) {
                    System.out.println("Kembali ke menu utama.");
                    return;
                } else if (jenis >= 1 && jenis <= 3) {
                    addItem(scanner, menu, jenis);
                    return;
                } else {
                    System.out.println("Jenis item tidak valid. Silakan coba lagi.");
                }
            } else {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
                scanner.nextLine();
            }
        }
    }
}