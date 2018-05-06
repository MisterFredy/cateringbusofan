package com.akhmadfaizin.cateringbusofan;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by root on 5/6/18.
 */

public final class PackageSelect {
    private static String namaKategori;
    private static String namaPackage;
    private static int defaultPrice;
    private static LinkedHashMap<String, HashMap<Integer, PackageChoice>> collectionSelected;

    public static String getNamaKategori() {
        return namaKategori;
    }

    public static void setNamaKategori(String namaKategori) {
        PackageSelect.namaKategori = namaKategori;
    }

    public static String getNamaPackage() {
        return namaPackage;
    }

    public static void setNamaPackage(String namaPackage) {
        PackageSelect.namaPackage = namaPackage;
    }

    public static int getDefaultPrice() {
        return defaultPrice;
    }

    public static void setDefaultPrice(int defaultPrice) {
        PackageSelect.defaultPrice = defaultPrice;
    }

    public static LinkedHashMap<String, HashMap<Integer, PackageChoice>> getCollectionSelected() {
        return collectionSelected;
    }

    public static void setCollectionSelected(LinkedHashMap<String, HashMap<Integer, PackageChoice>> collectionSelected) {
        PackageSelect.collectionSelected = collectionSelected;
    }
}
