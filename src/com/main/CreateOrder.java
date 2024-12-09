package com.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateOrder {
    public static void addItemToOrder(Scanner scanner, Menu menu, Pesanan pesanan) {
        List<String> orderItems = new ArrayList<>();
        List<Integer> orderQuantities = new ArrayList<>();

        menu.showMenu();
        System.out.println("Contoh Format : Nasi Goreng = 2");
        System.out.println("ketik 'selesai' untuk mengakhiri pesanan");
        System.out.println("ketik '0' Kembali ke Menu Utama");
        System.out.println("ketik '00' Keluar Aplikasi");

        while (true){
            System.out.print("Masukan Item Pesanan : ");
            String input = scanner.nextLine().trim();

            if(input.equalsIgnoreCase("selesai")){
                if(orderItems.isEmpty()){
                    System.out.println("Anda Belum Memesan");
                    continue;
                }
                    pesanan = new Pesanan();
                    break;

            }

            if(input.equalsIgnoreCase("0")){
                return;
            }

            if(input.equalsIgnoreCase("00")){
                System.exit(0);
            }

            String[] parts = input.split(" = ");
            if(parts.length != 2){
                System.out.println("Format Salah. Gunakan Format : Nama Menu = Jumlah Porsi");
                continue;
            }

            String itemName = parts[0].trim();
            int quantity;
            try{
                quantity = Integer.parseInt(parts[1].trim());
            }catch (NumberFormatException e){
                System.out.println("Jumlah Porsi Harus Berupa Angka!");
                continue;
            }

            boolean itemExists = false;
            for(MenuItem menuItem : menu.getListMenu()){
                if(menuItem.getName().equalsIgnoreCase(itemName)){
                    itemExists = true;
                    break;
                }
            }

            if(!itemExists){
                System.out.println("Item " + itemName + " tidak ada di menu. Silakan pilih item yang valid.");
                continue;
            }

            System.out.println(quantity + " "+Utils.capitalizeFirst(itemName) +" Berhasil di tambahkan");
            orderItems.add(itemName);
            orderQuantities.add(quantity);

        }

        for (int i = 0; i < orderItems.size(); i++) {
            String itemName = orderItems.get(i);
            int quantity = orderQuantities.get(i);

            MenuItem foundItem = null;
            for (MenuItem menuItem : menu.getListMenu()) {
                if (menuItem.getName().equalsIgnoreCase(itemName)) {
                    foundItem = menuItem;
                    break;
                }
            }

            if (foundItem != null) {
                pesanan.addItem(foundItem, quantity);
            } else {
                System.out.println("Item " + itemName + " tidak ditemukan. Tidak ditambahkan ke pesanan.");
            }
        }
        Utils.saveOrderToFile(pesanan, "struk.txt");
    }
}
