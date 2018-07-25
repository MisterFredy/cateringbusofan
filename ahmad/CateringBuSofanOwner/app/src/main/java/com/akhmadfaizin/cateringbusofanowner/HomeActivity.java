package com.akhmadfaizin.cateringbusofanowner;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity {
    private String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //For Hiding ActionBar
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "token : " + refreshedToken);

        // Unsubscribe Dari Topic Owners
        FirebaseMessaging.getInstance().unsubscribeFromTopic("owners");

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
