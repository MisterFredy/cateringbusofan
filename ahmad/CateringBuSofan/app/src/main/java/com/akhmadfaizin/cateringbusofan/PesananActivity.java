package com.akhmadfaizin.cateringbusofan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PesananActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        getSupportActionBar().setTitle("Pesanan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
