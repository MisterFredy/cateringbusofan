package com.akhmadfaizin.cateringbusofan;

public class Pesanan {
    private String kodeTransaksi;
    private String fullName;
    private String tanggalPemesanan;
    private String approval;
    private String urlPhoto;
    private int totalBayar;
    private int dp;

    public Pesanan(String kodeTransaksi, String fullName, String tanggalPemesanan, String approval, String urlPhoto, int totalBayar, int dp) {
        this.kodeTransaksi = kodeTransaksi;
        this.fullName = fullName;
        this.tanggalPemesanan = tanggalPemesanan;
        this.approval = approval;
        this.urlPhoto = urlPhoto;
        this.totalBayar = totalBayar;
        this.dp = dp;
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

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public int getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(int totalBayar) {
        this.totalBayar = totalBayar;
    }

    public int getDp() {
        return dp;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }
}
