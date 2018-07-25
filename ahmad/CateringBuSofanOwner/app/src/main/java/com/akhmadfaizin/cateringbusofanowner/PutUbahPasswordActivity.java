package com.akhmadfaizin.cateringbusofanowner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

public class PutUbahPasswordActivity extends AppCompatActivity {
    private String TAG = PutUbahPasswordActivity.class.getSimpleName();
    private String stringResponse = "";
    private Boolean updateResponse = false;
    private ProgressDialog pDialog;

    private Boolean logged;
    private String username,
            password,
            password_lama,
            password_baru;
    private TextView tv_result_ubah_password,
            tv_string_ubah_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_ubah_password);

        // Set The Initialize Action Bar title
        getSupportActionBar().setTitle("Loading ...");

        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);

        username = sp.getString("username", null);
        password = sp.getString("password", null);

        password_lama = getIntent().getStringExtra("password_lama");
        password_baru = getIntent().getStringExtra("password_baru");

        tv_result_ubah_password = findViewById(R.id.tv_result_ubah_password);
        tv_string_ubah_password = findViewById(R.id.tv_string_ubah_password);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Check Kesamaan Password Lama yang diinputkan
        if(!password_lama.equals(password)) {   // Jika Tidak Sama
            stringResponse = "Maaf Password Lama yang dimasukkan Salah";
            getSupportActionBar().setTitle("Ubah Password");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            tv_result_ubah_password.setText(String.valueOf(updateResponse));
            tv_string_ubah_password.setText(stringResponse);
        } else {
            // Jika Sama

            new GoUbahPassword().execute();
        }

    }

    private class GoUbahPassword extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PutUbahPasswordActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String urlForCheck = getString(R.string.base_url) + "updatepassword/" + username + "/" + password_baru;

            updateResponse = sh.postUpdateAPINoJSON(urlForCheck);

            if(updateResponse) {
                stringResponse = "Password Berhasil Berubah";
            } else {
                stringResponse = "Password Gagal Berubah";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            getSupportActionBar().setTitle("Ubah Password");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Update Shared Preference
            if(updateResponse) {
                // Simpan Shared Preference yang baru
                SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("password", password_baru);
                editor.apply();
            }

            tv_result_ubah_password.setText(String.valueOf(updateResponse));
            tv_string_ubah_password.setText(stringResponse);
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
