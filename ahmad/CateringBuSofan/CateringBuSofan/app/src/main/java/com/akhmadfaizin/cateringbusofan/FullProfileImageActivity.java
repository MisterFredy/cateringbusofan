package com.akhmadfaizin.cateringbusofan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.squareup.picasso.Picasso;

public class FullProfileImageActivity extends AppCompatActivity {
    private String urlfoto = "";
    private TouchImageView ivFullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_profile_image);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#792525"));
        }

        getSupportActionBar().setTitle("Foto Profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivFullImage = findViewById(R.id.iv_fullImage);

        urlfoto = getIntent().getStringExtra("urlfoto");

        if(!urlfoto.equals("default")) {
            // Load Image From Url
            String linkurlfoto = getString(R.string.base_url) + "profile/" + urlfoto;
            Picasso.with(this)
                    .load(linkurlfoto)
                    .into(ivFullImage);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_image_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_ganti_foto:
                Intent i = new Intent(this, UploadFotoUserActivity.class);
                startActivity(i);
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return true;
    }
}
