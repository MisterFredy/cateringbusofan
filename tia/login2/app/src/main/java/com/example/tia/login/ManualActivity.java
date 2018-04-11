package com.example.tia.login;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ManualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        // Untuk Ubah Title di ActionBar
        getSupportActionBar().setTitle("Presensi Manual");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
// Lalu ubah parent di AndroidManifest


    }
}
