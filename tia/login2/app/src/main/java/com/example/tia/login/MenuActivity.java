package com.example.tia.login;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //hiding action bar
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();

    }

    public void goQRCode(View view) {
        Toast.makeText(this, "MENUJU KE QR CODE", Toast.LENGTH_SHORT).show();
    }

    public void goRekap(View view) {
        Intent i = new Intent(this, RekpLprnActivity.class);
        startActivity(i);
    }

    public void goManual(View view) {
        Intent i = new Intent(this, ManualActivity.class);
        startActivity(i);
    }

    public void goData(View view) {
        Toast.makeText(this, "MENUJU KE DATA", Toast.LENGTH_SHORT).show();
    }
}
