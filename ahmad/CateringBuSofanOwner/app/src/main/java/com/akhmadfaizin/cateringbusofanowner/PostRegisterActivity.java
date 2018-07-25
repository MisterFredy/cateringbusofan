package com.akhmadfaizin.cateringbusofanowner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONObject;

public class PostRegisterActivity extends AppCompatActivity {
    private String TAG = PostRegisterActivity.class.getSimpleName();
    private String jsonString = "";
    Boolean registerResponse = false,
            isUsernameAvailable = true;
    private ProgressDialog pDialog;

    String username,
            password,
            phone,
            jenis_kelamin,
            alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_register);

        getSupportActionBar().setTitle("Loading...");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Get Data From Intent
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        phone = getIntent().getStringExtra("phone");
        jenis_kelamin = getIntent().getStringExtra("jenis_kelamin");
        alamat = getIntent().getStringExtra("alamat");

        // Create The Json
        try {
            JSONObject jsonRoot = new JSONObject();
            jsonRoot.put("user", username);
            jsonRoot.put("alamat", alamat);
            jsonRoot.put("urlfoto", "null");
            jsonRoot.put("jenis_kelamin", jenis_kelamin);
            jsonRoot.put("no_hp", phone);
            jsonRoot.put("password", password);
            jsonRoot.put("role", "costumer");
            jsonRoot.put("status", "1");    // Sebelumnya integer, jadi String karena di sails parameter tidak kedetek

            Log.e(TAG, "JSON Untuk Dikirim: " + jsonRoot.toString());
            jsonString = jsonRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new GoRegister().execute();

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        i.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(i);
    }

    private class GoRegister extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PostRegisterActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String urlFront = getString(R.string.base_url) + "user?where={\"user\":\"";
            String urlBack = "\"}";
            String urlForCheck = urlFront + username + urlBack;

            String jsonStr = sh.goGetApi(urlForCheck);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr == null || jsonStr.length() < 5) {
                // Making a request to url and getting response
                // Selain untuk POST, url ini juga bisa buat GET, jadi bisa buat ngecek
                // perubahan setelah POST
                String url = getString(R.string.base_url) + "tambahuser";

                registerResponse = sh.postInsertAPI(url, jsonString);

            } else {
                isUsernameAvailable = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            getSupportActionBar().setTitle("Registrasi");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            TextView tv_register_result = findViewById(R.id.tv_register_result);
            String message = "";
            if(isUsernameAvailable) {
                if(registerResponse) {
                    message = "Akun Baru Berhasil Dibuat";

                }else{
                    message = "Akun Baru Gagal Dibuat";
                }

            } else {
                message = "Username " + username + " \ntidak tersedia untuk digunakan";
            }

            tv_register_result.setText(message);
        }
    }
}
