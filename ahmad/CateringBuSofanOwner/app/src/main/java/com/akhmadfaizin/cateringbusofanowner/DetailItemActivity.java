package com.akhmadfaizin.cateringbusofanowner;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailItemActivity extends AppCompatActivity {
    private String nama, deskripsi, urlimg;
    private int harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#222D58"));
        }

        // Get Bundle Extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nama = extras.getString("NAMA");
            harga = extras.getInt("HARGA");
            deskripsi = extras.getString("DESKRIPSI");
            urlimg = extras.getString("URLIMG");
        }

        getSupportActionBar().setTitle(nama);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv_nama_item_pilihan = findViewById(R.id.tv_nama_item_pilihan);
        TextView tv_harga_item_pilihan = findViewById(R.id.tv_harga_item_pilihan);
        TextView tv_item_pilihan_deskripsi = findViewById(R.id.tv_item_pilihan_deskripsi);
        ImageView iv_item_pilihan = findViewById(R.id.iv_item_pilihan);

        tv_nama_item_pilihan.setText(nama);
        String strHarga = "+ Rp " + String.format("%,d", harga);
        tv_harga_item_pilihan.setText(strHarga);
        tv_item_pilihan_deskripsi.setText(deskripsi);
        Picasso.with(this).load(urlimg).into(iv_item_pilihan);

    }

    /*
        Making The Up Button behavior like Back Button
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }
}
