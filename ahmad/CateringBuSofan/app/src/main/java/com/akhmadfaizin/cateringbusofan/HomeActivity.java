package com.akhmadfaizin.cateringbusofan;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //For Hiding ActionBar
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();

        /*
        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        if(sp.getString("username",null) != null) {
            String username = sp.getString("username", null);
            String alamat = sp.getString("alamat", null);
            String urlfoto = sp.getString("urlfoto", null);
            String jenis_kelamin = sp.getString("jenis_kelamin", null);
            String no_hp = sp.getString("no_hp", null);
            String password = sp.getString("password", null);
            String role = sp.getString("role", null);
            String status = sp.getString("status", null);

            String result_message = "";
            result_message += ("Username : " + username + "\n");
            result_message += ("Alamat : " + alamat + "\n");
            result_message += ("Url Foto : " + urlfoto + "\n");
            result_message += ("Jenis Kelamin : " + jenis_kelamin + "\n");
            result_message += ("No Hp : " + no_hp + "\n");
            result_message += ("Password : " + password + "\n");
            result_message += ("Role : " + role + "\n");
            result_message += ("Status : " + status + "\n");

            Toast.makeText(this, result_message, Toast.LENGTH_SHORT).show();
        } else {
            String message = "Shared Preference Kosong";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        */
    }

    public void openRegister(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void openLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
