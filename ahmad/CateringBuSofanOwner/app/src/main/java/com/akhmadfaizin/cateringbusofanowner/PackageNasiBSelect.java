package com.akhmadfaizin.cateringbusofanowner;

import java.util.LinkedHashMap;
import java.util.TreeMap;

public final class PackageNasiBSelect {
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
        PackageNasiBSelect.namaKategori = namaKategori;
    }

    public static String getNamaPackage() {
        return namaPackage;
    }

    public static void setNamaPackage(String namaPackage) {
        PackageNasiBSelect.namaPackage = namaPackage;
    }

    public static int getKuantitas() {
        return kuantitas;
    }

    public static void setKuantitas(int kuantitas) {
        PackageNasiBSelect.kuantitas = kuantitas;
    }

    public static int getDefaultPrice() {
        return defaultPrice;
    }

    public static void setDefaultPrice(int defaultPrice) {
        PackageNasiBSelect.defaultPrice = defaultPrice;
    }

    public static int getFinalPrice() {
        return finalPrice;
    }

    public static void setFinalPrice(int finalPrice) {
        PackageNasiBSelect.finalPrice = finalPrice;
    }

    public static int getSubTotalPrice() {
        return subTotalPrice;
    }

    public static void setSubTotalPrice(int subTotalPrice) {
        PackageNasiBSelect.subTotalPrice = subTotalPrice;
    }

    public static String getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public static void setTanggalPengiriman(String tanggalPengiriman) {
        PackageNasiBSelect.tanggalPengiriman = tanggalPengiriman;
    }

    public static String getJamPengiriman() {
        return jamPengiriman;
    }

    public static void setJamPengiriman(String jamPengiriman) {
        PackageNasiBSelect.jamPengiriman = jamPengiriman;
    }

    public static String getAtasNama() {
        return atasNama;
    }

    public static void setAtasNama(String atasNama) {
        PackageNasiBSelect.atasNama = atasNama;
    }

    public static String getKontak() {
        return kontak;
    }

    public static void setKontak(String kontak) {
        PackageNasiBSelect.kontak = kontak;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        PackageNasiBSelect.alamat = alamat;
    }

    public static String getCatatan() {
        return catatan;
    }

    public static void setCatatan(String catatan) {
        PackageNasiBSelect.catatan = catatan;
    }

    public static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> getCollectionSelected() {
        return collectionSelected;
    }

    public static void setCollectionSelected(LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected) {
        PackageNasiBSelect.collectionSelected = collectionSelected;
    }

    public static LinkedHashMap<String, TreeMap<Integer, PackageChoice>> getTemporaryCollectionSelected() {
        return temporaryCollectionSelected;
    }

    public static void setTemporaryCollectionSelected(LinkedHashMap<String, TreeMap<Integer, PackageChoice>> temporaryCollectionSelected) {
        PackageNasiBSelect.temporaryCollectionSelected = temporaryCollectionSelected;
    }

    public static void reset() {
        PackageNasiBSelect.namaKategori = "";
        PackageNasiBSelect.namaPackage = "";
        PackageNasiBSelect.kuantitas = 0;
        PackageNasiBSelect.defaultPrice = 0;
        PackageNasiBSelect.finalPrice = 0;
        PackageNasiBSelect.subTotalPrice = 0;
        PackageNasiBSelect.tanggalPengiriman = "";
        PackageNasiBSelect.jamPengiriman = "";
        PackageNasiBSelect.atasNama = "";
        PackageNasiBSelect.kontak = "";
        PackageNasiBSelect.alamat = "";
        PackageNasiBSelect.catatan = "";
        PackageNasiBSelect.collectionSelected = null;
        PackageNasiBSelect.temporaryCollectionSelected = null;
    }
}
