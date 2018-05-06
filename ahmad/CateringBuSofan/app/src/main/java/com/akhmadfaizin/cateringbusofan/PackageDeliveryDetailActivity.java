package com.akhmadfaizin.cateringbusofan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PackageDeliveryDetailActivity extends AppCompatActivity {

    private LinkedHashMap<String, HashMap<Integer, PackageChoice>> collectionSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_delivery_detail);
        TextView tv_delivery = findViewById(R.id.tv_delivery);

        String message = "";

        message += ("Nama Kategori : " + PackageSelect.getNamaKategori() + "\n");
        message += ("Nama Package : " + PackageSelect.getNamaPackage() + "\n");

        int hargaDefault = PackageSelect.getDefaultPrice();
        collectionSelected = PackageSelect.getCollectionSelected();

        for (LinkedHashMap.Entry<String, HashMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
            String key = entry.getKey();
            HashMap<Integer, PackageChoice> value = entry.getValue();

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
