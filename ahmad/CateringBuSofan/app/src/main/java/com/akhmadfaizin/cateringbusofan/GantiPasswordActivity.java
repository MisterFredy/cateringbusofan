package com.akhmadfaizin.cateringbusofan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GantiPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);

        getSupportActionBar().setTitle("Ganti Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
