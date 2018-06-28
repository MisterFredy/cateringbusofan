package com.akhmadfaizin.cateringbusofan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        // Jika logged is false, maka ke Home Activity
        if(!sp.getBoolean("logged", false)) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        } else {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            if (Build.VERSION.SDK_INT >= 21) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.parseColor("#792525"));
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View headerView = navigationView.getHeaderView(0);
            ImageView iv_profile = headerView.findViewById(R.id.iv_profile);
            TextView tv_fullname = headerView.findViewById(R.id.tv_fullname);
            TextView tv_phone = headerView.findViewById(R.id.tv_phone);
            TextView tv_alamat = headerView.findViewById(R.id.tv_alamat);

            //Check String urlfoto dari shared preferences
            if(sp.getString("urlfoto", null).equals("null")  || sp.getString("urlfoto", null).equals("")) {
                // Load Default Picture
                Picasso.with(this)
                        .load(R.drawable.profile)
                        .resize(75, 75)
                        .into(iv_profile);
            } else {
                String urlfoto = getString(R.string.base_url) + "profile/" + sp.getString("urlfoto", null);
                // Load Image From Url
                Picasso.with(this)
                        .load(urlfoto)
                        .resize(75, 75)
                        .into(iv_profile);
            }

            iv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
                    Intent i = new Intent(getApplicationContext(), FullProfileImageActivity.class);
                    if(sp.getString("urlfoto", null).equals("null")  || sp.getString("urlfoto", null).equals("")) {
                        i.putExtra("urlfoto", "default");
                    } else {
                        i.putExtra("urlfoto", sp.getString("urlfoto", null));
                    }
                    startActivity(i);

                }
            });


            tv_fullname.setText(sp.getString("fullname", null));
            tv_phone.setText(sp.getString("no_hp", null));
            tv_alamat.setText(sp.getString("alamat", null));
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_keranjang) {
            Intent i = new Intent(this, KeranjangActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_ubah_profile) {
            Intent i = new Intent(this, UbahProfileActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_ganti_password) {
            Intent i = new Intent(this, GantiPasswordActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goAbout(View view) {
        Toast.makeText(this, "Activity Belum dibuat", Toast.LENGTH_SHORT).show();
    }

    public void goGaleri(View view) {
        Intent i = new Intent(this, CatalogueActivity.class);
        startActivity(i);
    }

    public void goPesanan(View view) {
        Intent i = new Intent(this, PesananActivity.class);
        startActivity(i);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Anda yakin ingin keluar aplikasi ini?")
                .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        //close();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                })
                .show();
    }
}