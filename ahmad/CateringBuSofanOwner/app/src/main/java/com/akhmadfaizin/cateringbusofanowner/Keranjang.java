package com.akhmadfaizin.cateringbusofanowner;

/**
 * Created by root on 5/17/18.
 */

public class Keranjang {
    private String kategori;
    private String paket;
    private int perPorsi;
    private int kuantitasPorsi;
    private int subTotal;
    // private String urlImg; (Belum disertakan gambar juga)


    public Keranjang(String kategori, String paket, int perPorsi, int kuantitasPorsi, int subTotal) {
        this.kategori = kategori;
        this.paket = paket;
        this.perPorsi = perPorsi;
        this.kuantitasPorsi = kuantitasPorsi;
        this.subTotal = subTotal;
    }

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

    public int getPerPorsi() {
        return perPorsi;
    }

    public void setPerPorsi(int perPorsi) {
        this.perPorsi = perPorsi;
    }

    public int getKuantitasPorsi() {
        return kuantitasPorsi;
    }

    public void setKuantitasPorsi(int kuantitasPorsi) {
        this.kuantitasPorsi = kuantitasPorsi;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }
}
