package com.akhmadfaizin.cateringbusofan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UbahProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profile);

        getSupportActionBar().setTitle("Ubah Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
