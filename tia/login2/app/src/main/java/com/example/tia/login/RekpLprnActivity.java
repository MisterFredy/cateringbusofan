package com.example.tia.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RekpLprnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekp_lprn);
        getSupportActionBar().setTitle("Rekap Laporan Presensi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
