package com.akhmadfaizin.cateringbusofanowner;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FullBuktiImageActivity extends AppCompatActivity {
    private int nominal = 0;
    private String url_img = "";
    private TextView tvNominal;
    private TouchImageView ivFullImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_bukti_image);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#222D58"));
        }

        getSupportActionBar().setTitle("Foto Bukti Pembayaran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivFullImage = findViewById(R.id.iv_fullImage);
        tvNominal = findViewById(R.id.tv_bukti_nominal);

        url_img = getIntent().getStringExtra("url_img");
        nominal = getIntent().getIntExtra("nominal", 0);

        tvNominal.setText("Rp " + String.format("%,d", nominal));

        // Load Image From Url
        Picasso.with(this)
                .load(url_img)
                .into(ivFullImage);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return true;
    }
}
