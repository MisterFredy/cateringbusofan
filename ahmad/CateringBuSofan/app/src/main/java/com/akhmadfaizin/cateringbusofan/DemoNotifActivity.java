package com.akhmadfaizin.cateringbusofan;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

public class DemoNotifActivity extends AppCompatActivity {
    Button btnNotif;
    private String TAG = DemoNotifActivity.class.getSimpleName();
    private String jsonString = "";
    Boolean response = false;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_notif);

        btnNotif = findViewById(R.id.btn_notif);

        // Create The Json
        try {
            JSONObject jsonRoot = new JSONObject();

            jsonRoot.put("to", "/topics/owners");
            jsonRoot.put("priority", "high");

            JSONObject jsonNotifObject = new JSONObject();
            jsonNotifObject.put("body", "HALO BRO");
            jsonNotifObject.put("title", "JUDUL NOTIF");
            jsonRoot.put("notification", jsonNotifObject);

            jsonString = jsonRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }



        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GoNotif().execute();
            }
        });


    }

    private class GoNotif extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DemoNotifActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://fcm.googleapis.com/fcm/send";

            response = sh.postNotif(url, jsonString);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();


            String message = "";
            if(response) {
                message = "Notif Berhasil";

            }else{
                message = "Notif Gagal";
            }

            Toast.makeText(DemoNotifActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }
}
