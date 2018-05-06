package com.akhmadfaizin.cateringbusofan;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //For Hiding ActionBar
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
    }


    public void doLogin(View view) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

}
