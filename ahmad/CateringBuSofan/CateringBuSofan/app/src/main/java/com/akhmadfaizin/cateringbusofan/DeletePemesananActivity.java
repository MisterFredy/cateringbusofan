package com.akhmadfaizin.cateringbusofan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class DeletePemesananActivity extends AppCompatActivity {
    private String TAG = DeletePemesananActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private String id = "";
    private String stringResponse = "";
    private Boolean deleteResponse = false;

    private TextView tvResultDeletePemesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_pemesanan);

        id = getIntent().getStringExtra("kodeTransaksi");
        tvResultDeletePemesanan = findViewById(R.id.tv_result_delete_pemesanan);

        // Setting Color of Status Bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#792525"));
        }

        // Set The Initialize Action Bar title
        getSupportActionBar().setTitle("Loading ...");

        new GoDeletePemesanan().execute();
    }

    private class GoDeletePemesanan extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DeletePemesananActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String urlForCheck = getString(R.string.base_url) + "deletepemesanan/" + id;

            deleteResponse = sh.postUpdateAPINoJSON(urlForCheck);

            if(!deleteResponse) {
                stringResponse = "Pemesanan Gagal Dihapus";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            if(deleteResponse) {
                // Buka Lagi Activity Pesanan
                Intent i = new Intent(getApplicationContext(), PesananActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else {
                // Show Fail Message
                tvResultDeletePemesanan.setText(stringResponse);
            }
        }
    }
}
