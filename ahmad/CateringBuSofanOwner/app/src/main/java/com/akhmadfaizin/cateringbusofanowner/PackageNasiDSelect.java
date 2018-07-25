package com.akhmadfaizin.cateringbusofanowner;

import java.util.LinkedHashMap;
import java.util.TreeMap;

public final class PackageNasiDSelect {
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
        PackageNasiDSelect.namaKategori = namaKategori;
    }

    public static String getNamaPackage() {
        return namaPackage;
    }

    public static void setNamaPackage(String namaPackage) {
        PackageNasiDSelect.namaPackage = namaPackage;
    }

    public static int getKuantitas() {
        return kuantitas;
    }

    public static void setKuantitas(int kuantitas) {
        PackageNasiDSelect.kuantitas = kuantitas;
    }

    public static int getDefaultPrice() {
        return defaultPrice;
    }

    public static void setDefaultPrice(int defaultPrice) {
        PackageNasiDSelect.defaultPrice = defaultPrice;
    }

    public static int getFinalPrice() {
        return finalPrice;
    }

    public static void setFinalPrice(int finalPrice) {
        PackageNasiDSelect.finalPrice = finalPrice;
    }

    public static int getSubTotalPrice() {
        return subTotalPrice;
    }

    public static void setSubTotalPrice(int subTotalPrice) {
        PackageNasiDSelect.subTotalPrice = subTotalPrice;
    }

    public static String getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public static void setTanggalPengiriman(String tanggalPengiriman) {
        PackageNasiDSelect.tanggalPengiriman = tanggalPengiriman;
    }

    public static String getJamPengiriman() {
        return jamPengiriman;
    }

    public static void setJamPengiriman(String jamPengiriman) {
        PackageNasiDSelect.jamPengiriman = jamPengiriman;
    }

    public static String getAtasNama() {
        return atasNama;
    }

    public static void setAtasNama(String atasNama) {
        PackageNasiDSelect.atasNama = atasNama;
    }

    public static String getKontak() {
        return kontak;
    }

    public static void setKontak(String kontak) {
        PackageNasiDSelect.kontak = kontak;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        PackageNasiDSelect.alamat = alamat;
    }

    public static String getCatatan() {
        return catatan;
    }

    public static void setCatatan(String catatan) {
        PackageNasiDSelect.catatan = catatan;
    }

    public static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> getCollectionSelected() {
        return collectionSelected;
    }

    public static void setCollectionSelected(LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected) {
        PackageNasiDSelect.collectionSelected = collectionSelected;
    }

    public static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> getTemporaryCollectionSelected() {
        return temporaryCollectionSelected;
    }

    public static void setTemporaryCollectionSelected(LinkedHashMap<String, TreeMap<Integer, PackageChoice>> temporaryCollectionSelected) {
        PackageNasiDSelect.temporaryCollectionSelected = temporaryCollectionSelected;
    }

    public static void reset() {
        PackageNasiDSelect.namaKategori = "";
        PackageNasiDSelect.namaPackage = "";
        PackageNasiDSelect.kuantitas = 0;
        PackageNasiDSelect.defaultPrice = 0;
        PackageNasiDSelect.finalPrice = 0;
        PackageNasiDSelect.subTotalPrice = 0;
        PackageNasiDSelect.tanggalPengiriman = "";
        PackageNasiDSelect.jamPengiriman = "";
        PackageNasiDSelect.atasNama = "";
        PackageNasiDSelect.kontak = "";
        PackageNasiDSelect.alamat = "";
        PackageNasiDSelect.catatan = "";
        PackageNasiDSelect.collectionSelected = null;
        PackageNasiDSelect.temporaryCollectionSelected = null;
    }
}
