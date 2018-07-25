package com.akhmadfaizin.cateringbusofanowner;

import java.io.Serializable;

/**
 * Created by root on 5/2/18.
 */

public class PackageChoice implements Serializable {
    private boolean isSelected;
    private String nama;
    private String deskripsi;
    private int harga;
    private String urlImg;

    public PackageChoice(boolean isSelected, String nama, String deskripsi, int harga, String urlImg) {
        this.isSelected = isSelected;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.urlImg = urlImg;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
