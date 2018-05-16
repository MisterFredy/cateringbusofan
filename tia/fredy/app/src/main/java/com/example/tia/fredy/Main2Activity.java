package com.example.tia.fredy;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        android.support.v7.app.ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();

    }
    public void goQRCode(View view){
        Intent i = new Intent(this, qrcode.class);
        startActivity(i);
    }
    public void goRekap(View view) {
        Intent i = new Intent(this, RkpAbsn.class);
        startActivity(i);
    }

    public void goManual(View view) {
        Intent i = new Intent(this, rekapmanual.class);
        startActivity(i);
    }

    public void goData(View view) {
        Toast.makeText(this, "MENUJU KE DATA", Toast.LENGTH_SHORT).show();
    }
}


