package com.akhmadfaizin.cateringbusofanowner;

import java.util.LinkedHashMap;
import java.util.TreeMap;

public final class PackageBuffetASelect {
    private static String namaKategori;
    private static String namaPackage;
    private static int kuantitas;
    private static int defaultPrice;
    private static int finalPrice;
    private static int subTotalPrice;
    private static String tanggalPengiriman;
    private static String jamPengiriman;
    private static String atasNama;
    private static String kontak;
    private static String alamat;
    private static String catatan;
    private static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected;
    private static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> temporaryCollectionSelected;

    public static String getNamaKategori() {
        return namaKategori;
    }

    public static void setNamaKategori(String namaKategori) {
        PackageBuffetASelect.namaKategori = namaKategori;
    }

    public static String getNamaPackage() {
        return namaPackage;
    }

    public static void setNamaPackage(String namaPackage) {
        PackageBuffetASelect.namaPackage = namaPackage;
    }

    public static int getKuantitas() {
        return kuantitas;
    }

    public static void setKuantitas(int kuantitas) {
        PackageBuffetASelect.kuantitas = kuantitas;
    }

    public static int getDefaultPrice() {
        return defaultPrice;
    }

    public static void setDefaultPrice(int defaultPrice) {
        PackageBuffetASelect.defaultPrice = defaultPrice;
    }

    public static int getFinalPrice() {
        return finalPrice;
    }

    public static void setFinalPrice(int finalPrice) {
        PackageBuffetASelect.finalPrice = finalPrice;
    }

    public static int getSubTotalPrice() {
        return subTotalPrice;
    }

    public static void setSubTotalPrice(int subTotalPrice) {
        PackageBuffetASelect.subTotalPrice = subTotalPrice;
    }

    public static String getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public static void setTanggalPengiriman(String tanggalPengiriman) {
        PackageBuffetASelect.tanggalPengiriman = tanggalPengiriman;
    }

    public static String getJamPengiriman() {
        return jamPengiriman;
    }

    public static void setJamPengiriman(String jamPengiriman) {
        PackageBuffetASelect.jamPengiriman = jamPengiriman;
    }

    public static String getAtasNama() {
        return atasNama;
    }

    public static void setAtasNama(String atasNama) {
        PackageBuffetASelect.atasNama = atasNama;
    }

    public static String getKontak() {
        return kontak;
    }

    public static void setKontak(String kontak) {
        PackageBuffetASelect.kontak = kontak;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        PackageBuffetASelect.alamat = alamat;
    }

    public static String getCatatan() {
        return catatan;
    }

    public static void setCatatan(String catatan) {
        PackageBuffetASelect.catatan = catatan;
    }

    public static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> getCollectionSelected() {
        return collectionSelected;
    }

    public static void setCollectionSelected(LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected) {
        PackageBuffetASelect.collectionSelected = collectionSelected;
    }

    public static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> getTemporaryCollectionSelected() {
        return temporaryCollectionSelected;
    }

    public static void setTemporaryCollectionSelected(LinkedHashMap<String, TreeMap<Integer, PackageChoice>> temporaryCollectionSelected) {
        PackageBuffetASelect.temporaryCollectionSelected = temporaryCollectionSelected;
    }

    public static void reset() {
        PackageBuffetASelect.namaKategori = "";
        PackageBuffetASelect.namaPackage = "";
        PackageBuffetASelect.kuantitas = 0;
        PackageBuffetASelect.defaultPrice = 0;
        PackageBuffetASelect.finalPrice = 0;
        PackageBuffetASelect.subTotalPrice = 0;
        PackageBuffetASelect.tanggalPengiriman = "";
        PackageBuffetASelect.jamPengiriman = "";
        PackageBuffetASelect.atasNama = "";
        PackageBuffetASelect.kontak = "";
        PackageBuffetASelect.alamat = "";
        PackageBuffetASelect.catatan = "";
        PackageBuffetASelect.collectionSelected = null;
        PackageBuffetASelect.temporaryCollectionSelected = null;
    }
}
