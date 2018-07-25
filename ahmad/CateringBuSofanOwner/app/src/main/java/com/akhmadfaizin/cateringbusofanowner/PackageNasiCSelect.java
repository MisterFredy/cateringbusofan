package com.akhmadfaizin.cateringbusofanowner;

import java.util.LinkedHashMap;
import java.util.TreeMap;

public final class PackageNasiCSelect {
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
        PackageNasiCSelect.namaKategori = namaKategori;
    }

    public static String getNamaPackage() {
        return namaPackage;
    }

    public static void setNamaPackage(String namaPackage) {
        PackageNasiCSelect.namaPackage = namaPackage;
    }

    public static int getKuantitas() {
        return kuantitas;
    }

    public static void setKuantitas(int kuantitas) {
        PackageNasiCSelect.kuantitas = kuantitas;
    }

    public static int getDefaultPrice() {
        return defaultPrice;
    }

    public static void setDefaultPrice(int defaultPrice) {
        PackageNasiCSelect.defaultPrice = defaultPrice;
    }

    public static int getFinalPrice() {
        return finalPrice;
    }

    public static void setFinalPrice(int finalPrice) {
        PackageNasiCSelect.finalPrice = finalPrice;
    }

    public static int getSubTotalPrice() {
        return subTotalPrice;
    }

    public static void setSubTotalPrice(int subTotalPrice) {
        PackageNasiCSelect.subTotalPrice = subTotalPrice;
    }

    public static String getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public static void setTanggalPengiriman(String tanggalPengiriman) {
        PackageNasiCSelect.tanggalPengiriman = tanggalPengiriman;
    }

    public static String getJamPengiriman() {
        return jamPengiriman;
    }

    public static void setJamPengiriman(String jamPengiriman) {
        PackageNasiCSelect.jamPengiriman = jamPengiriman;
    }

    public static String getAtasNama() {
        return atasNama;
    }

    public static void setAtasNama(String atasNama) {
        PackageNasiCSelect.atasNama = atasNama;
    }

    public static String getKontak() {
        return kontak;
    }

    public static void setKontak(String kontak) {
        PackageNasiCSelect.kontak = kontak;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        PackageNasiCSelect.alamat = alamat;
    }

    public static String getCatatan() {
        return catatan;
    }

    public static void setCatatan(String catatan) {
        PackageNasiCSelect.catatan = catatan;
    }

    public static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> getCollectionSelected() {
        return collectionSelected;
    }

    public static void setCollectionSelected(LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected) {
        PackageNasiCSelect.collectionSelected = collectionSelected;
    }

    public static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> getTemporaryCollectionSelected() {
        return temporaryCollectionSelected;
    }

    public static void setTemporaryCollectionSelected(LinkedHashMap<String, TreeMap<Integer, PackageChoice>> temporaryCollectionSelected) {
        PackageNasiCSelect.temporaryCollectionSelected = temporaryCollectionSelected;
    }

    public static void reset() {
        PackageNasiCSelect.namaKategori = "";
        PackageNasiCSelect.namaPackage = "";
        PackageNasiCSelect.kuantitas = 0;
        PackageNasiCSelect.defaultPrice = 0;
        PackageNasiCSelect.finalPrice = 0;
        PackageNasiCSelect.subTotalPrice = 0;
        PackageNasiCSelect.tanggalPengiriman = "";
        PackageNasiCSelect.jamPengiriman = "";
        PackageNasiCSelect.atasNama = "";
        PackageNasiCSelect.kontak = "";
        PackageNasiCSelect.alamat = "";
        PackageNasiCSelect.catatan = "";
        PackageNasiCSelect.collectionSelected = null;
        PackageNasiCSelect.temporaryCollectionSelected = null;
    }
}
