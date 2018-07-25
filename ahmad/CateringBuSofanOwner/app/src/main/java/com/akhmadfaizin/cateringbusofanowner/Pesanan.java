package com.akhmadfaizin.cateringbusofanowner;

public class Pesanan {
    private String kodeTransaksi;
    private String fullName;
    private String tanggalPemesanan;
    private String approval;
    private String token;
    private String urlPhoto;

    public Pesanan(String kodeTransaksi, String fullName, String tanggalPemesanan, String approval, String token, String urlPhoto) {
        this.kodeTransaksi = kodeTransaksi;
        this.fullName = fullName;
        this.tanggalPemesanan = tanggalPemesanan;
        this.approval = approval;
        this.token = token;
        this.urlPhoto = urlPhoto;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTanggalPemesanan() {
        return tanggalPemesanan;
    }

    public void setTanggalPemesanan(String tanggalPemesanan) {
        this.tanggalPemesanan = tanggalPemesanan;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
}
