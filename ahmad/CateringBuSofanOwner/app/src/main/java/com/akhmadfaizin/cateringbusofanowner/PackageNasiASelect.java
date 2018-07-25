package com.akhmadfaizin.cateringbusofanowner;

import java.util.LinkedHashMap;
import java.util.TreeMap;

public final class PackageNasiASelect {
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
        PackageNasiASelect.namaKategori = namaKategori;
    }

    public static String getNamaPackage() {
        return namaPackage;
    }

    public static void setNamaPackage(String namaPackage) {
        PackageNasiASelect.namaPackage = namaPackage;
    }

    public static int getKuantitas() {
        return kuantitas;
    }

    public static void setKuantitas(int kuantitas) {
        PackageNasiASelect.kuantitas = kuantitas;
    }

    public static int getDefaultPrice() {
        return defaultPrice;
    }

    public static void setDefaultPrice(int defaultPrice) {
        PackageNasiASelect.defaultPrice = defaultPrice;
    }

    public static int getFinalPrice() {
        return finalPrice;
    }

    public static void setFinalPrice(int finalPrice) {
        PackageNasiASelect.finalPrice = finalPrice;
    }

    public static int getSubTotalPrice() {
        return subTotalPrice;
    }

    public static void setSubTotalPrice(int subTotalPrice) {
        PackageNasiASelect.subTotalPrice = subTotalPrice;
    }

    public static String getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public static void setTanggalPengiriman(String tanggalPengiriman) {
        PackageNasiASelect.tanggalPengiriman = tanggalPengiriman;
    }

    public static String getJamPengiriman() {
        return jamPengiriman;
    }

    public static void setJamPengiriman(String jamPengiriman) {
        PackageNasiASelect.jamPengiriman = jamPengiriman;
    }

    public static String getAtasNama() {
        return atasNama;
    }

    public static void setAtasNama(String atasNama) {
        PackageNasiASelect.atasNama = atasNama;
    }

    public static String getKontak() {
        return kontak;
    }

    public static void setKontak(String kontak) {
        PackageNasiASelect.kontak = kontak;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        PackageNasiASelect.alamat = alamat;
    }

    public static String getCatatan() {
        return catatan;
    }

    public static void setCatatan(String catatan) {
        PackageNasiASelect.catatan = catatan;
    }

    public static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> getCollectionSelected() {
        return collectionSelected;
    }

    public static void setCollectionSelected(LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected) {
        PackageNasiASelect.collectionSelected = collectionSelected;
    }

    public static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> getTemporaryCollectionSelected() {
        return temporaryCollectionSelected;
    }

    public static void setTemporaryCollectionSelected(LinkedHashMap<String, TreeMap<Integer, PackageChoice>> temporaryCollectionSelected) {
        PackageNasiASelect.temporaryCollectionSelected = temporaryCollectionSelected;
    }

    public static void reset() {
        PackageNasiASelect.namaKategori = "";
        PackageNasiASelect.namaPackage = "";
        PackageNasiASelect.kuantitas = 0;
        PackageNasiASelect.defaultPrice = 0;
        PackageNasiASelect.finalPrice = 0;
        PackageNasiASelect.subTotalPrice = 0;
        PackageNasiASelect.tanggalPengiriman = "";
        PackageNasiASelect.jamPengiriman = "";
        PackageNasiASelect.atasNama = "";
        PackageNasiASelect.kontak = "";
        PackageNasiASelect.alamat = "";
        PackageNasiASelect.catatan = "";
        PackageNasiASelect.collectionSelected = null;
        PackageNasiASelect.temporaryCollectionSelected = null;
    }
}
