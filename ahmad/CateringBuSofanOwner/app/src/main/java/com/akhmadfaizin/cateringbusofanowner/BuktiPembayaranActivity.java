package com.akhmadfaizin.cateringbusofanowner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuktiPembayaranActivity extends AppCompatActivity {

    private String TAG = BuktiPembayaranActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private String parameter_id_pemesanan = "";
    private String token;
    List<BuktiPembayaran> buktiPembayaranItem;
    int totalBayar;
    int dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_pembayaran);

        // Setting Color of Status Bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#222D58"));
        }

        // Set The Initialize Action Bar title
        getSupportActionBar().setTitle("Loading ...");

        parameter_id_pemesanan = getIntent().getStringExtra("kodeTransaksi");
        token = getIntent().getStringExtra("token");
        totalBayar = 0;
        dp = 0;

        buktiPembayaranItem = new ArrayList<>();

        // Execute AsyncTask To Parsing JSON for Pesanan
        new GetPesanan().execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return true;
    }

    private class GetPesanan extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BuktiPembayaranActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String urlPemesanan = getString(R.string.base_url) + "pemesanan?id=" + parameter_id_pemesanan;

            // Making a request to url and getting response
            String jsonStrPemesanan = sh.goGetApi(urlPemesanan);

            Log.e(TAG, "Response from url: " + jsonStrPemesanan);

            if (jsonStrPemesanan != null) {
                try {
                    JSONObject p = new JSONObject(jsonStrPemesanan);
                    totalBayar = p.getInt("totalBayar");
                    dp = p.getInt("dp");

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                //Log.e(TAG, "Couldn't get json from server.");
                Log.e(TAG, "json from server is either null or [ ].");
            }

            /* ====== */

            // Making a request to url and getting response
            String urlBukti = getString(R.string.base_url) + "buktibayarpesanan?status=1&id_pemesanan=" + parameter_id_pemesanan + "&sort=createdAt%20DESC";

            // Making a request to url and getting response
            String jsonStrBukti = sh.goGetApi(urlBukti);

            Log.e(TAG, "Response from url: " + jsonStrBukti);

            if (jsonStrBukti != null) {
                try {

                    JSONArray root = new JSONArray(jsonStrBukti);

                    for (int i = 0; i < root.length(); i++) {
                        JSONObject p = root.getJSONObject(i);

                        String id = p.getString("id");
                        String url_img = p.getString("url_img");
                        url_img = getString(R.string.base_url) + "bukti/" + url_img;
                        int nominal = p.getInt("nominal");
                        String tanggal = p.getString("tanggal");
                        String konfirmasi = p.getString("konfirmasi");

                        buktiPembayaranItem.add(new BuktiPembayaran(id, url_img, nominal, tanggal, konfirmasi));
                    }

                    Log.e(TAG, "Jumlah Bukti Pembayaran = " + String.valueOf(buktiPembayaranItem.size()));

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                //Log.e(TAG, "Couldn't get json from server.");
                Log.e(TAG, "json from server is either null or [ ].");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            // Set The Action Bar Title
            getSupportActionBar().setTitle("Bukti Pembayaran");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if(buktiPembayaranItem.size() == 0) {
                setContentView(R.layout.activity_bukti_pembayaran_kosong);
            } else {

                TextView tv_titleKodePemesanan = findViewById(R.id.tv_title_kodePemesanan);
                TextView tv_titleTotalBayar = findViewById(R.id.tv_title_totalBayar);
                TextView tv_titleDp = findViewById(R.id.tv_title_dp);
                TextView tv_titleList = findViewById(R.id.tv_title_list);
                View v_garisHorizontal = findViewById(R.id.v_garis_horizontal);
                TextView tv_kodePemesanan = findViewById(R.id.tv_kodePemesanan);
                TextView tv_totalBayar = findViewById(R.id.tv_totalBayar);
                TextView tv_dp = findViewById(R.id.tv_dp);

                tv_titleKodePemesanan.setVisibility(View.VISIBLE);
                tv_titleTotalBayar.setVisibility(View.VISIBLE);
                tv_titleDp.setVisibility(View.VISIBLE);
                tv_titleList.setVisibility(View.VISIBLE);
                v_garisHorizontal.setVisibility(View.VISIBLE);

                tv_kodePemesanan.setText(parameter_id_pemesanan);
                tv_totalBayar.setText("Rp " + String.format("%,d", totalBayar));
                tv_dp.setText("Rp " + String.format("%,d", dp));

                final ScrollView scrollView = findViewById(R.id.bukti_pemesanan_scrollview);
                // To Scroll to the Top when Opening The Activity
                scrollView.smoothScrollTo(0, 0);

                final NonScrollListView non_scroll_list = (NonScrollListView) findViewById(R.id.lv_bukti_pemesanan);

                final BuktiPembayaranAdapter adapter = new BuktiPembayaranAdapter(BuktiPembayaranActivity.this, buktiPembayaranItem, parameter_id_pemesanan, token, dp);
                non_scroll_list.setAdapter(adapter);
            }
        }
    }

}
