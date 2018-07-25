package com.akhmadfaizin.cateringbusofanowner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONObject;

public class BuktiPembayaranUpdateKonfirmasiActivity extends AppCompatActivity {
    private String TAG = BuktiPembayaranUpdateKonfirmasiActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private String stringResponse = "";
    private Boolean updateResponse = false;
    private Boolean updatedpResponse = false;
    private String kodeTransaksi;
    private int dp;
    private int nominal;
    private String id;
    private String token;
    private String konfirmasi;
    private String jsonNotifString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_pembayaran_update_konfirmasi);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#222D58"));
        }

        kodeTransaksi = getIntent().getStringExtra("kodeTransaksi");
        dp = getIntent().getIntExtra("dp", 0);
        nominal = getIntent().getIntExtra("nominal", 0);
        id = getIntent().getStringExtra("id");
        token = getIntent().getStringExtra("token");
        konfirmasi = getIntent().getStringExtra("konfirmasi");

        new UpdateKonfirmasiBukti().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class UpdateKonfirmasiBukti extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BuktiPembayaranUpdateKonfirmasiActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String urlForCheck = "";

            SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
            String perespon = sp.getString("username", null);

            if(konfirmasi.equals("Gambar Cocok Dengan Nominal")) {
                urlForCheck = getString(R.string.base_url) + "bukticocok/" + id + "/" + perespon;
            } else if(konfirmasi.equals("Gambar Tidak Cocok Dengan Nominal")) {
                urlForCheck = getString(R.string.base_url) + "buktitidakcocok/" + id + "/" + perespon;
            }

            updateResponse = sh.postUpdateAPINoJSON(urlForCheck);

            if(!updateResponse) {
                stringResponse = "Konfirmasi Bukti Pembayaran Gagal Diupdate";
            } else {
                stringResponse = "Konfirmasi Bukti Pembayaran Berhasil Diupdate";

                if(konfirmasi.equals("Gambar Cocok Dengan Nominal")) {
                    // Update value DP di pemesanan berdasarkan kodeTransaksi
                    int updatedDP = dp + nominal;

                    String urlForUpdateDP = getString(R.string.base_url) + "updatedp/" + kodeTransaksi + "/" + updatedDP;
                    updatedpResponse = sh.postUpdateAPINoJSON(urlForUpdateDP);
                }
            }

            // Create The Json Untuk Notif
            try {
                JSONObject jsonNotif = new JSONObject();

                String bodyApprovalNotif = "Sebesar Rp " + String.format("%,d", nominal);

                if(konfirmasi.equals("Gambar Cocok Dengan Nominal")) {
                    bodyApprovalNotif += ("\nCocok Dengan Gambar");
                } else if(konfirmasi.equals("Gambar Tidak Cocok Dengan Nominal")) {
                    bodyApprovalNotif += ("\nTidak Cocok Dengan Gambar");
                }

                String titleNotif = "Bukti Pembayaran";

                jsonNotif.put("to", token);
                jsonNotif.put("priority", "high");

                JSONObject jsonNotifObject = new JSONObject();
                jsonNotifObject.put("body", bodyApprovalNotif);
                jsonNotifObject.put("title", titleNotif);
                jsonNotif.put("notification", jsonNotifObject);

                Log.e(TAG, "JSON Untuk Dikirim: " + jsonNotif.toString());
                jsonNotifString = jsonNotif.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Post Notifikasi Ke Owner
            String notifUrl = "https://fcm.googleapis.com/fcm/send";
            Boolean notifResponse = sh.postNotif(notifUrl, jsonNotifString);

            if(!notifResponse) {
                Log.e(TAG, "Notif gagal dikirim");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            String message = "";

            if(updatedpResponse) {
                message += "Nilai DP sudah terupdate\n";
            }

            message += stringResponse;
            Toast.makeText(BuktiPembayaranUpdateKonfirmasiActivity.this, message, Toast.LENGTH_LONG).show();
            Intent i = new Intent(BuktiPembayaranUpdateKonfirmasiActivity.this, BuktiPembayaranActivity.class);
            i.putExtra("kodeTransaksi", kodeTransaksi);
            startActivity(i);
            finish();

        }
    }
}
