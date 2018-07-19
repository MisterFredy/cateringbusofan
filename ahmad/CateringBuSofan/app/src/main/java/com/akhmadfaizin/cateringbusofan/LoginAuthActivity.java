package com.akhmadfaizin.cateringbusofan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginAuthActivity extends AppCompatActivity {
    private String TAG = LoginAuthActivity.class.getSimpleName();
    private ProgressDialog pDialog;

    String username,
            password,
            jsonId,
            jsonUsername,
            jsonFullname,
            jsonAlamat,
            jsonUrlFoto,
            jsonJenisKelamin,
            jsonNoHp,
            jsonPassword,
            jsonRole,
            jsonStatus,
            loginMessage;
    Boolean isLoginSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_auth);

        getSupportActionBar().setTitle("Loading...");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Get Data From Intent
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        new LoginHandler().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class LoginHandler extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginAuthActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // URL to get JSON
            String urlFront = getString(R.string.base_url) + "user?where={\"user\":\"";
            String urlBack = "\"}";
            String urlForCheck = urlFront + username + urlBack;

            // Making a request to url and getting response
            String jsonStr = sh.goGetApi(urlForCheck);

            Log.e(TAG, "Response from urlCheck: " + jsonStr);

            if (jsonStr == null || jsonStr.length() < 5) {
                // Akun Belum Terdaftar
                loginMessage = "Maaf Username Belum Terdaftar\nSilahkan Register Dahulu";
            } else {
                // Akun Sudah Terdaftar

                try {

                    JSONArray root = new JSONArray(jsonStr);
                    JSONObject jsonObject = root.getJSONObject(0);
                    jsonId = jsonObject.getString("id");
                    jsonUsername = jsonObject.getString("user");
                    jsonFullname = jsonObject.getString("nama");
                    jsonAlamat = jsonObject.getString("alamat");
                    jsonUrlFoto = jsonObject.getString("urlfoto");
                    jsonJenisKelamin = jsonObject.getString("jenis_kelamin");
                    jsonNoHp = jsonObject.getString("no_hp");
                    jsonPassword = jsonObject.getString("password");
                    jsonRole = jsonObject.getString("role");
                    jsonStatus = jsonObject.getString("status");

                    // Check Dulu Statusnya
                    if(jsonStatus.equals("0")) {
                        loginMessage = "Maaf Username " + jsonUsername + "\nsudah TIDAK AKTIF lagi";
                    } else {
                        // Check Dulu Rolenya
                        if(jsonRole.equals("owner")) {
                            loginMessage = "Untuk keamanan, maaf Username " + jsonUsername +"\nTidak Bisa Login Di Aplikasi ini";
                        } else {
                            // Check Passwordnya Benar Tidak
                            if(jsonPassword.equals(password)) {
                                isLoginSuccess = true;

                                // Update Token Di Database sesuai dengan token device
                                String device_token = FirebaseInstanceId.getInstance().getToken();
                                Log.e(TAG, "device_ token : " + device_token);
                                String urlForTokenUpdate = getString(R.string.base_url) + "updtoken/" + jsonUsername + "/" + device_token;

                                Boolean updateTokenResponse = sh.postUpdateAPINoJSON(urlForTokenUpdate);

                                if(!updateTokenResponse) {
                                    isLoginSuccess = false;
                                    loginMessage = "Maaf terjadi masalah koneksi";
                                }

                            } else {
                                loginMessage = "Maaf Password Salah";
                            }
                        }
                    }


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

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            getSupportActionBar().setTitle("Login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if(isLoginSuccess) {
                SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("logged", true);
                editor.putString("id", jsonId);
                editor.putString("username", jsonUsername);
                editor.putString("fullname", jsonFullname);
                editor.putString("alamat", jsonAlamat);
                editor.putString("urlfoto", jsonUrlFoto);
                editor.putString("jenis_kelamin", jsonJenisKelamin);
                editor.putString("no_hp", jsonNoHp);
                editor.putString("password", jsonPassword);
                editor.putString("role", jsonRole);
                editor.putString("status", jsonStatus);
                editor.apply();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            } else {
                TextView tv_login_result = findViewById(R.id.tv_login_result);
                tv_login_result.setText(loginMessage);
            }

        }

    }


}
