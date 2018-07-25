package com.akhmadfaizin.cateringbusofanowner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PesananUbahApprovalActivity extends AppCompatActivity {
    private String TAG = PesananUbahApprovalActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView listView;
    private Button btnProcess;
    private List<PesananUbahApproval> listChoice;
    private PesananUbahApproval selectedRadio;
    private int preSelectedIndex = -1;
    private String stringResponse = "";
    private Boolean updateResponse = false;
    private String id;
    private String token;
    private String jsonNotifString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_ubah_approval);

        id = getIntent().getStringExtra("kodeTransaksi");
        token = getIntent().getStringExtra("token");

        // Setting Color of Status Bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#222D58"));
        }

        // Set The Action Bar Title
        getSupportActionBar().setTitle("Ubah Approval");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ScrollView scrollView = findViewById(R.id.approval_scrollview);
        // To Scroll to the Top when Opening The Activity
        scrollView.smoothScrollTo(0, 0);

        listView = findViewById(R.id.lv_approval);

        listChoice = new ArrayList<>();
        listChoice.add(new PesananUbahApproval(false,"Belum Disetujui", "Belum Bayar DP"));
        listChoice.add(new PesananUbahApproval(false,"Sudah Disetujui", "Sudah Bayar DP"));
        listChoice.add(new PesananUbahApproval(false,"Sudah Disetujui", "Lunas"));

        final PesananUbahApprovalAdapter adapter = new PesananUbahApprovalAdapter(PesananUbahApprovalActivity.this, listChoice);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get Model From Item that Selected
                PesananUbahApproval choice = listChoice.get(i);

                selectedRadio = listChoice.get(i);

                // Change the isSelected state
                choice.setSelected(true);

                if (preSelectedIndex > -1 && preSelectedIndex != i){

                    PesananUbahApproval preRecord = listChoice.get(preSelectedIndex);
                    preRecord.setSelected(false);

                    listChoice.set(preSelectedIndex, preRecord);
                }

                preSelectedIndex = i;

                //Now update adapter so we are going to make a update method in adapter
                //Now declare adapter final to access in inner method
                adapter.updateRecords(listChoice);
            }
        });

        btnProcess = findViewById(R.id.btn_process);
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking if all true using checkAllRadioButtonSelected method
                Boolean isAllRadioButtonSelected = checkAllRadioButtonSelected(listChoice);

                if(isAllRadioButtonSelected) {
                    new UbahApproval().execute();
                } else {
                    Snackbar snackbar = Snackbar.make(scrollView, "Silahkan Pilih Salah Satu", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class UbahApproval extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PesananUbahApprovalActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String urlForCheck = "";

            SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
            String pengaprove = sp.getString("username", null);

            String alasan = selectedRadio.getAlasan();

            if(alasan.equals("Belum Bayar DP")) {
                urlForCheck = getString(R.string.base_url) + "approvalbelum/" + id + "/" + pengaprove;
            } else if(alasan.equals("Sudah Bayar DP")) {
                urlForCheck = getString(R.string.base_url) + "approvalsudah/" + id + "/" + pengaprove;
            } else if(alasan.equals("Lunas")) {
                urlForCheck = getString(R.string.base_url) + "approvallunas/" + id + "/" + pengaprove;
            }

            updateResponse = sh.postUpdateAPINoJSON(urlForCheck);

            if(!updateResponse) {
                stringResponse = "Approval Gagal Diupdate";
            } else {
                stringResponse = "Approval Berhasil Diupdate";

                // Create The Json Untuk Notif
                try {
                    JSONObject jsonNotif = new JSONObject();

                    String bodyApprovalNotif = "";

                    if(alasan.equals("Belum Bayar DP")) {
                        bodyApprovalNotif = "Pesanan Belum Disetujui\nBelum Bayar DP";
                    } else if(alasan.equals("Sudah Bayar DP")) {
                        bodyApprovalNotif = "Pesanan Disetujui\nSudah Bayar DP";
                    } else if(alasan.equals("Lunas")) {
                        bodyApprovalNotif = "Pesanan Disetujui\nPembayaran Lunas";
                    }

                    jsonNotif.put("to", token);
                    jsonNotif.put("priority", "high");

                    JSONObject jsonNotifObject = new JSONObject();
                    String titleNotif = "Id Pemesanan " + id;
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


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            Toast.makeText(PesananUbahApprovalActivity.this, stringResponse, Toast.LENGTH_LONG).show();
            Intent i = new Intent(PesananUbahApprovalActivity.this, PesananActivity.class);
            startActivity(i);
            setResult(RESULT_OK);
            finish();

        }
    }

    /*
        Method To Check If Radio Button is Selected
     */
    public Boolean checkAllRadioButtonSelected(List<PesananUbahApproval> listChoice) {
        Boolean returnValue = false;
        for (int i = 0; i < listChoice.size(); i++) {
            if(listChoice.get(i).isSelected()) {
                returnValue = true;
                break;
            }
        }
        return returnValue;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
