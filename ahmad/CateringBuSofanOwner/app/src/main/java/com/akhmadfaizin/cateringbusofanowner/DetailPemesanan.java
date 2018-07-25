package com.akhmadfaizin.cateringbusofanowner;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by root on 5/26/18.
 */

public class DetailPemesanan {
    private String kategori;
    private String paket;
    private int kuantitas;
    private int subTotal;
    private String tanggalPengiriman;
    private String jamPengiriman;
    private String atasNama;
    private String kontak;
    private String alamat;
    private String catatan;
    private LinkedHashMap<String, List<String>> listDetailPilihan;

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public String getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public void setTanggalPengiriman(String tanggalPengiriman) {
        this.tanggalPengiriman = tanggalPengiriman;
    }

    public String getJamPengiriman() {
        return jamPengiriman;
    }

    public void setJamPengiriman(String jamPengiriman) {
        this.jamPengiriman = jamPengiriman;
    }

    public String getAtasNama() {
        return atasNama;
    }

    public void setAtasNama(String atasNama) {
        this.atasNama = atasNama;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public LinkedHashMap<String, List<String>> getListDetailPilihan() {
        return listDetailPilihan;
    }

    public void setListDetailPilihan(LinkedHashMap<String, List<String>> listDetailPilihan) {
        this.listDetailPilihan = listDetailPilihan;
    }
}
