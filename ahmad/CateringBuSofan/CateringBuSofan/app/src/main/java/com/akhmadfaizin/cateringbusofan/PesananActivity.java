package com.akhmadfaizin.cateringbusofan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PesananActivity extends AppCompatActivity {
    private String TAG = PesananActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    List<Pesanan> pesananItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);

        // Setting Color of Status Bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#792525"));
        }

        // Set The Initialize Action Bar title
        getSupportActionBar().setTitle("Loading ...");

        pesananItem = new ArrayList<>();

        // Execute AsyncTask To Parsing JSON for Pesanan
        new GetPesanan().execute();

    }

    private class GetPesanan extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PesananActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
            String parameter_pemesan = sp.getString("username", null);
            String url = getString(R.string.base_url) + "pemesanan?status=1&user=" + parameter_pemesan + "&sort=createdAt%20DESC";

            // Making a request to url and getting response
            String jsonStr = sh.goGetApi(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    JSONArray root = new JSONArray(jsonStr);

                    for (int i = 0; i < root.length(); i++) {
                        JSONObject p = root.getJSONObject(i);

                        String kodeTransaksi = p.getString("id");
                        String nama = p.getString("pemesan");
                        String tanggalPemesanan = p.getString("tanggalPemesanan");
                        String approval = p.getString("approval");
                        String urlPhoto = p.getString("urlPhoto");
                        int totalBayar = p.getInt("totalBayar");
                        int dp = p.getInt("dp");

                        pesananItem.add(new Pesanan(kodeTransaksi, nama, tanggalPemesanan, approval, urlPhoto, totalBayar, dp));
                    }

                    Log.e(TAG, "Jumlah Pesanan = " + String.valueOf(pesananItem.size()));

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
            getSupportActionBar().setTitle("Pesanan");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if(pesananItem.size() == 0) {
                setContentView(R.layout.activity_pesanan_kosong);
            } else {
                final ScrollView scrollView = findViewById(R.id.pesanan_scrollview);
                // To Scroll to the Top when Opening The Activity
                scrollView.smoothScrollTo(0, 0);

                final NonScrollListView non_scroll_list = (NonScrollListView) findViewById(R.id.lv_pesanan);

                final PesananAdapter adapter = new PesananAdapter(PesananActivity.this, pesananItem);
                non_scroll_list.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(i);
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
