package com.akhmadfaizin.cateringbusofanowner;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TesSharedPreferenceActivity extends AppCompatActivity {
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes_shared_preference);
        tv_result = findViewById(R.id.tv_result);

        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
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

        tv_result.setText(result_message);



    }
}
