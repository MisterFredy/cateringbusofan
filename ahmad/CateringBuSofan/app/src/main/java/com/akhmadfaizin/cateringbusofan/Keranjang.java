package com.akhmadfaizin.cateringbusofan;

public class Keranjang {
    private String kategori;
    private String paket;
    private int perPorsi;
    private int kuantitasPorsi;
    private int subTotal;
    private int urlImg;

    public Keranjang(String kategori, String paket, int perPorsi, int kuantitasPorsi, int subTotal, int urlImg) {
        this.kategori = kategori;
        this.paket = paket;
        this.perPorsi = perPorsi;
        this.kuantitasPorsi = kuantitasPorsi;
        this.subTotal = subTotal;
        this.urlImg = urlImg;
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

    public int getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(int urlImg) {
        this.urlImg = urlImg;
    }
}
