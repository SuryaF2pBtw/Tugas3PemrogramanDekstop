package com.main;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

public class Utils {
    public static String formatIDR(double amount) {
        NumberFormat formatIDR = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatIDR.format(amount);
    }

    public static String capitalizeFirst(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String[] words = text.split(" ");
        StringBuilder capitalizedText = new StringBuilder();
        for (String word : words) {
            if (word.length() > 1) {
                capitalizedText.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase());
            } else {
                capitalizedText.append(word.toUpperCase());
            }
            capitalizedText.append(" ");
        }
        return capitalizedText.toString().trim();
    }

    public static void saveMenuToFile(List<MenuItem> menu, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Map<String, List<MenuItem>> categorizedMenu = categorizeMenu(menu);
            List<String> sortedCategories = new ArrayList<>(categorizedMenu.keySet());
            CategoryHandler.sortCategories(sortedCategories);

            for (String category : sortedCategories) {
                List<MenuItem> items = categorizedMenu.get(category);
                items.sort(Comparator.comparing(MenuItem::getName));

                for (MenuItem item : items) {
                    if (item instanceof Diskon) {
                        Diskon discountItem = (Diskon) item;
                        writer.write(String.join(",", item.getCategory(), item.getName(), String.valueOf(item.getPrice()), String.valueOf(discountItem.getDiscount())));
                    } else {
                        writer.write(String.join(",", item.getCategory(), item.getName(), String.valueOf(item.getPrice())));
                    }
                    writer.newLine();
                }
            }
        }
    }

    public static void loadMenuFromFile(List<MenuItem> listMenu, String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                MenuItem menuItem = parseMenuItem(data);
                if (menuItem != null) {
                    listMenu.add(menuItem);
                }
            }
        }
    }

    public static void saveOrderToFile(Pesanan pesanan, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("========| Struk Pesanan |========\n");
            int number = 1;
            for (OrderItem item : pesanan.getItems()) {
                double priceItem;
                if(item.getMenuItem() instanceof Diskon){
                    priceItem = ((Diskon) item.getMenuItem()).getPriceAfterDiscount();
                } else {
                    priceItem = item.getMenuItem().getPrice();
                }
                writer.write(String.format("%d. %s x %d | Harga: %s%n",
                        number,
                        item.getMenuItem().getName(),
                        item.getQuantity(),
                        Utils.formatIDR(priceItem * item.getQuantity())));
                number++;
            }

            double totalBeforeDiscount = pesanan.total();
            double discount = 0;
            double tax = totalBeforeDiscount * 0.1;

            if (totalBeforeDiscount > 200000) {
                discount = totalBeforeDiscount * 0.1;
            } else if (totalBeforeDiscount > 100000) {
                discount = totalBeforeDiscount * 0.05;
            }

            double totalAfterDiscount = totalBeforeDiscount - discount;
            double totalWithTax = totalAfterDiscount + tax;

            writer.write(String.format("Total Sebelum Diskon: %s%n", Utils.formatIDR(totalBeforeDiscount)));
            writer.write(String.format("Diskon: - %s%n", Utils.formatIDR(discount)));
            writer.write(String.format("Pajak (10%%): + %s%n", Utils.formatIDR(tax)));
            writer.write(String.format("Total Akhir: %s%n", Utils.formatIDR(totalWithTax)));
            writer.write("===============================\n");

            System.out.println("Pesanan berhasil disimpan ke dalam data restoran.");
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat menyimpan pesanan: " + e.getMessage());
        }
    }

    public static void displayOrderFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Struk kosong. Belum ada pesanan.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(Utils.capitalizeFirst(line));
            }
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }

    public static void clearFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
        } catch (IOException e) {
            System.err.println("Gagal mengosongkan file: " + e.getMessage());
        }
    }

    public static Map<String, List<MenuItem>> categorizeMenu(List<MenuItem> menu) {
        Map<String, List<MenuItem>> categorizedMenu = new LinkedHashMap<>();
        for (MenuItem item : menu) {
            categorizedMenu.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item);
        }
        return categorizedMenu;
    }

    private static MenuItem parseMenuItem(String[] data) {
        if (data.length < 3) {
            throw new IllegalArgumentException("Data menu tidak lengkap: " + Arrays.toString(data));
        }

        try {
            switch (data[0].toLowerCase()) {
                case "makanan":
                    return new Makanan(data[1], Double.parseDouble(data[2]));
                case "minuman":
                    return new Minuman(data[1], Double.parseDouble(data[2]));
                case "diskon":
                    if (data.length < 4) {
                        throw new IllegalArgumentException("Data diskon tidak lengkap: " + Arrays.toString(data));
                    }
                    return new Diskon(data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]));
                default:
                    throw new IllegalArgumentException("Kategori menu tidak valid: " + data[0]);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Format angka tidak valid dalam data: " + Arrays.toString(data), e);
        }
    }

    public class MenuHandler {
        public static void sortMenuByCategoryAndName(List<MenuItem> menu) {
            menu.sort((item1, item2) -> {
                int categoryComparison = CategoryHandler.compareCategories(item1.getCategory(), item2.getCategory());
                if (categoryComparison != 0) {
                    return categoryComparison;
                }
                return item1.getName().compareToIgnoreCase(item2.getName());
            });
        }

        public static void sortMenu(List<MenuItem> menu) {
            menu.sort((item1, item2) -> {
                int categoryComparison = CategoryHandler.compareCategories(item1.getCategory(), item2.getCategory());
                if (categoryComparison != 0) {
                    return categoryComparison;
                }
                return item1.getName().compareToIgnoreCase(item2.getName());
            });
        }
    }

    public class CategoryHandler {
        private static int compareCategories(String cat1, String cat2) {
            if (cat1.equalsIgnoreCase("Diskon") && !cat2.equalsIgnoreCase("Diskon")) {
                return -1;
            } else if (!cat1.equalsIgnoreCase("Diskon") && cat2.equalsIgnoreCase("Diskon")) {
                return 1;
            } else if (cat1.equalsIgnoreCase("Makanan") && !cat2.equalsIgnoreCase("Makanan")) {
                return -1;
            } else if (!cat1.equalsIgnoreCase("Makanan") && cat2.equalsIgnoreCase("Makanan")) {
                return 1;
            } else {
                return cat1.compareToIgnoreCase(cat2);
            }
        }

        private static void sortCategories(List<String> categories) {
            categories.sort(CategoryHandler::compareCategories);
        }
    }

}
