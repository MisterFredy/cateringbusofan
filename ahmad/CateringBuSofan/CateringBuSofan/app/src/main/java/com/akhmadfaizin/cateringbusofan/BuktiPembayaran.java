package com.akhmadfaizin.cateringbusofan;

public class BuktiPembayaran {
    private String id;
    private String url_img;
    private int nominal;
    private String tanggal;
    private String konfirmasi;

    public BuktiPembayaran(String id, String url_img, int nominal, String tanggal, String konfirmasi) {
        this.id = id;
        this.url_img = url_img;
        this.nominal = nominal;
        this.tanggal = tanggal;
        this.konfirmasi = konfirmasi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
    }
}
