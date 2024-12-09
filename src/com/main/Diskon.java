package com.main;

public class Diskon extends MenuItem {
    private double diskon;

    public Diskon(String name, double harga, double diskon) {
        super(name, harga, "Diskon");
        this.diskon = diskon;
    }

    public double getDiscount() {
        return diskon;
    }


    public double getPriceAfterDiscount() {
        return getPrice() * (1 - (diskon / 100));
    }

    @Override
    public void tampilMenu() {
        double hargaAwal = getPrice();
        double hargaDiskon = getPriceAfterDiscount();

        String diskonFormatted = String.format("%d%%", (int) diskon);

        System.out.println(Utils.capitalizeFirst(getName()) + " - "
                + Utils.formatIDR(hargaAwal) + " -> "
                + diskonFormatted
                + " -> " + Utils.formatIDR(hargaDiskon));
    }
}
