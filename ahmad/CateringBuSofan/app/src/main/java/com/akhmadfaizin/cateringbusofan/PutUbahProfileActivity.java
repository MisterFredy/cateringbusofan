package com.akhmadfaizin.cateringbusofan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONObject;

public class PutUbahProfileActivity extends AppCompatActivity {
    private String TAG = PutUbahProfileActivity.class.getSimpleName();
    private String jsonString = "";
    private Boolean updateResponse = false;
    private ProgressDialog pDialog;

    private Boolean logged;
    private String id,
                    username,
                    fullname,
                    alamat,
                    urlfoto,
                    jenis_kelamin,
                    no_hp,
                    password,
                    role,
                    status;
    private TextView tv_result_ubah_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_ubah_profile);

        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);

        logged = sp.getBoolean("logged", false);
        id = sp.getString("id", null);
        username = sp.getString("username", null);
        fullname = getIntent().getStringExtra("fullname");
        alamat = getIntent().getStringExtra("alamat");
        urlfoto = sp.getString("urlfoto", null);
        jenis_kelamin = getIntent().getStringExtra("jenis_kelamin");
        no_hp = getIntent().getStringExtra("phone");
        password = sp.getString("password", null);
        role = sp.getString("role", null);
        status = sp.getString("status", null);

        tv_result_ubah_profile = findViewById(R.id.tv_result_ubah_profile);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Create The Json
        try {
            JSONObject jsonRoot = new JSONObject();
            jsonRoot.put("user", username);
            jsonRoot.put("alamat", alamat);
            jsonRoot.put("nama", fullname);
            jsonRoot.put("urlfoto", urlfoto);
            jsonRoot.put("jenis_kelamin", jenis_kelamin);
            jsonRoot.put("no_hp", no_hp);
            jsonRoot.put("password", password);
            jsonRoot.put("role", role);
            jsonRoot.put("status", status);
            jsonRoot.put("id", id);

            Log.e(TAG, "JSON Untuk Dikirim: " + jsonRoot.toString());
            jsonString = jsonRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new GoUbahProfile().execute();



    }

    private class GoUbahProfile extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PutUbahProfileActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String urlForCheck = getString(R.string.base_url) + "edituser/" + username;

            updateResponse = sh.postUpdateAPI(urlForCheck, jsonString);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            getSupportActionBar().setTitle("Ubah Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if(updateResponse) {
                // Simpan Shared Preference yang baru
                SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("logged", true);
                editor.putString("id", id);
                editor.putString("username", username);
                editor.putString("fullname", fullname);
                editor.putString("alamat", alamat);
                editor.putString("urlfoto", urlfoto);
                editor.putString("jenis_kelamin", jenis_kelamin);
                editor.putString("no_hp", no_hp);
                editor.putString("password", password);
                editor.putString("role", role);
                editor.putString("status", status);
                editor.apply();
            }

            tv_result_ubah_profile.setText(String.valueOf(updateResponse));
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
