package com.akhmadfaizin.cateringbusofanowner;

import android.app.ProgressDialog;
import android.content.Intent;
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
            window.setStatusBarColor(Color.parseColor("#222D58"));
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
            String url = getString(R.string.base_url) + "joinorder";

            // Making a request to url and getting response
            String jsonStr = sh.goGetApi(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    JSONArray root = new JSONArray(jsonStr);

                    for (int i = (root.length()-1); i >= 0; i--) {
                        JSONObject p = root.getJSONObject(i);

                        String statusDocument = p.getString("status");
                        if(statusDocument.equals("0")) {
                            continue;
                        }

                        String kodeTransaksi = p.getString("_id");
                        String tanggalPemesanan = p.getString("tanggalPemesanan");
                        String approval = p.getString("approval");

                        JSONArray user_docs = p.getJSONArray("user_docs");
                        JSONObject user_docs_obj = user_docs.getJSONObject(0);

                        String nama = user_docs_obj.getString("nama");
                        String token = user_docs_obj.getString("token");
                        String urlPhoto = user_docs_obj.getString("urlfoto");

                        pesananItem.add(new Pesanan(kodeTransaksi, nama, tanggalPemesanan, approval, token, urlPhoto));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == RESULT_CANCELED){
                //Do nothing
            } else if (resultCode == RESULT_OK) {
                Intent i = new Intent(this, PesananActivity.class);
                startActivity(i);
                finish();
            }
        }
    }


}
