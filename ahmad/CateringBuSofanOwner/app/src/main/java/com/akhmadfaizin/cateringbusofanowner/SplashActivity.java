package com.akhmadfaizin.cateringbusofanowner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {
    // Set Waktu Lama Splash Screen
    private static int splashInterval = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#222D58"));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Menghubungkan Activity Splash Screen Ke Main Activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                // Jeda Waktu Splash Screen
                this.finish();
            }

            private void finish() {
            }
        }, splashInterval);
    }
}
