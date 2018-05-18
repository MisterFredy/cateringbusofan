package com.akhmadfaizin.cateringbusofan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.TreeMap;

public class PackageDeliveryDetailActivity extends AppCompatActivity {

    private LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = new LinkedHashMap<>();
    private String message = "";
    private int hargaDefault = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_delivery_detail);
        TextView tv_delivery = findViewById(R.id.tv_delivery);

        // HARUS DIBUAH CABANG UNTUK BUFFET A, BUFFET B, BUFFET C, DLL.
        // Kalau Menggunakan Static Class

        switch(getIntent().getStringExtra("PackageName")) {
            case "Nasi Box A":
                message += ("Nama Kategori : " + PackageNasiASelect.getNamaKategori() + "\n");
                message += ("Nama Package : " + PackageNasiASelect.getNamaPackage() + "\n");

                hargaDefault = PackageNasiASelect.getDefaultPrice();
                collectionSelected = PackageNasiASelect.getCollectionSelected();
                break;
            case "Nasi Box B":
                message += ("Nama Kategori : " + PackageNasiBSelect.getNamaKategori() + "\n");
                message += ("Nama Package : " + PackageNasiBSelect.getNamaPackage() + "\n");

                hargaDefault = PackageNasiBSelect.getDefaultPrice();
                collectionSelected = PackageNasiBSelect.getCollectionSelected();
                break;
            case "Nasi Box C":
                message += ("Nama Kategori : " + PackageNasiCSelect.getNamaKategori() + "\n");
                message += ("Nama Package : " + PackageNasiCSelect.getNamaPackage() + "\n");

                hargaDefault = PackageNasiCSelect.getDefaultPrice();
                collectionSelected = PackageNasiCSelect.getCollectionSelected();
                break;
            case "Nasi Box D":
                message += ("Nama Kategori : " + PackageNasiDSelect.getNamaKategori() + "\n");
                message += ("Nama Package : " + PackageNasiDSelect.getNamaPackage() + "\n");

                hargaDefault = PackageNasiDSelect.getDefaultPrice();
                collectionSelected = PackageNasiDSelect.getCollectionSelected();
                break;
            case "Snack Box A":
                message += ("Nama Kategori : " + PackageSnackASelect.getNamaKategori() + "\n");
                message += ("Nama Package : " + PackageSnackASelect.getNamaPackage() + "\n");

                hargaDefault = PackageSnackASelect.getDefaultPrice();
                collectionSelected = PackageSnackASelect.getCollectionSelected();
                break;
            case "Snack Box B":
                message += ("Nama Kategori : " + PackageSnackBSelect.getNamaKategori() + "\n");
                message += ("Nama Package : " + PackageSnackBSelect.getNamaPackage() + "\n");

                hargaDefault = PackageSnackBSelect.getDefaultPrice();
                collectionSelected = PackageSnackBSelect.getCollectionSelected();
                break;
            case "Buffet A":
                message += ("Nama Kategori : " + PackageBuffetASelect.getNamaKategori() + "\n");
                message += ("Nama Package : " + PackageBuffetASelect.getNamaPackage() + "\n");

                hargaDefault = PackageBuffetASelect.getDefaultPrice();
                collectionSelected = PackageBuffetASelect.getCollectionSelected();
                break;
            case "Buffet B":
                message += ("Nama Kategori : " + PackageBuffetBSelect.getNamaKategori() + "\n");
                message += ("Nama Package : " + PackageBuffetBSelect.getNamaPackage() + "\n");

                hargaDefault = PackageBuffetBSelect.getDefaultPrice();
                collectionSelected = PackageBuffetBSelect.getCollectionSelected();
                break;
            case "Buffet C":
                message += ("Nama Kategori : " + PackageBuffetCSelect.getNamaKategori() + "\n");
                message += ("Nama Package : " + PackageBuffetCSelect.getNamaPackage() + "\n");

                hargaDefault = PackageBuffetCSelect.getDefaultPrice();
                collectionSelected = PackageBuffetCSelect.getCollectionSelected();
                break;
            case "Pondokan":
                message += ("Nama Kategori : " + PackagePondokanSelect.getNamaKategori() + "\n");
                message += ("Nama Package : " + PackagePondokanSelect.getNamaPackage() + "\n");

                hargaDefault = PackagePondokanSelect.getDefaultPrice();
                collectionSelected = PackagePondokanSelect.getCollectionSelected();
                break;
        }


        for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
            String key = entry.getKey();
            TreeMap<Integer, PackageChoice> value = entry.getValue();

            message += ( "== " + key + " ==\n");

            for (PackageChoice p : value.values()) {
                message += (p.getNama() + " - " + p.getHarga() + "\n");
                hargaDefault += p.getHarga();
            }
        }

        message = message + "\n" + "Total Price Per Item : " + String.valueOf(hargaDefault);

        tv_delivery.setText(message);
    }
}
