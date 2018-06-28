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
import android.widget.Toast;

public class BuktiPembayaranHapusActivity extends AppCompatActivity {
    private String TAG = BuktiPembayaranHapusActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private String idbuktipembayaran = "";
    private String kodeTransaksi = "";
    private int totalBayar;
    private int dp;
    private String stringResponse = "";
    private Boolean deleteResponse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_pembayaran_hapus);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#792525"));
        }

        idbuktipembayaran = getIntent().getStringExtra("id");
        kodeTransaksi = getIntent().getStringExtra("kodeTransaksi");
        totalBayar = getIntent().getIntExtra("totalBayar", 0);
        dp = getIntent().getIntExtra("dp", 0);

        new HapusBuktiPembayaran().execute();

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class HapusBuktiPembayaran extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BuktiPembayaranHapusActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String urlForCheck = getString(R.string.base_url) + "deletebukti/" + idbuktipembayaran;

            deleteResponse = sh.postUpdateAPINoJSON(urlForCheck);

            if(!deleteResponse) {
                stringResponse = "Bukti Pembayaran Gagal Dihapus";
            } else {
                stringResponse = "Bukti Pembayaran Berhasil Dihapus";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            Toast.makeText(BuktiPembayaranHapusActivity.this, stringResponse, Toast.LENGTH_LONG).show();
            Intent i = new Intent(BuktiPembayaranHapusActivity.this, BuktiPembayaranActivity.class);
            i.putExtra("kodeTransaksi", kodeTransaksi);
            i.putExtra("totalBayar", totalBayar);
            i.putExtra("dp", dp);
            startActivity(i);
            finish();

        }
    }
}
