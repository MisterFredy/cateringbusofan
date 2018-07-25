package com.akhmadfaizin.cateringbusofanowner;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class PutPemesananActivity extends AppCompatActivity {
    private String TAG = PutPemesananActivity.class.getSimpleName();
    private String jsonString = "";
    Boolean response = false;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_pemesanan);

        String pemesan = "mubarok";
        String pengaprove = "";
        String tanggalPemesanan = "24-08-2018";
        String kodeTransaksi = "";
        String approval = "Sudah DP";
        int totalBayar = 50000000;
        int dp = 20000000;
        int status = 1;

        // Create The Json
        try {
            JSONObject jsonRoot = new JSONObject();
            jsonRoot.put("pemesan", pemesan);
            jsonRoot.put("pengaprove", pengaprove);
            jsonRoot.put("tanggalPemesanan", tanggalPemesanan);
            jsonRoot.put("kodeTransaksi", kodeTransaksi);
            jsonRoot.put("approval", approval);
            jsonRoot.put("totalBayar", totalBayar);
            jsonRoot.put("dp", dp);
            jsonRoot.put("status", 1);

            JSONArray jsonArrayRoot = new JSONArray();

            // UNTUK NASI BOX
            JSONObject listA = new JSONObject();
            listA.put("kategori", "Nasi Box");
            listA.put("menu", "Nasi Box A");
            listA.put("kuantitas", 120);
            listA.put("subtotal", 15000000);
            listA.put("tanggalPengiriman", "25-08-2018");
            listA.put("jamPengiriman", "12:30");
            listA.put("atasNama", "Tia Ayu");
            listA.put("kontak", "081234567890");
            listA.put("alamat", "Jalan Mangga No 120 Sleman Yogyakarta");
            listA.put("catatan", "Kuahnya jangan banyak");
            jsonArrayRoot.put(listA);

            /*
            // UNTUK SNACK BOX
            JSONObject listB = new JSONObject();
            listB.put("kategori", "Snack Box");
            listB.put("menu", "Snack Box A");
            listB.put("kuantitas", 120);
            listB.put("subtotal", 15000000);

            JSONArray jsonArrayListB = new JSONArray();

            JSONObject detailMenuObjectA = new JSONObject();
            detailMenuObjectA.put("namaItem", "Snack Manis");
            JSONArray arrayPilihanA = new JSONArray();
            arrayPilihanA.put("Kue Lumpur");
            detailMenuObjectA.put("pilihan", arrayPilihanA);
            jsonArrayListB.put(detailMenuObjectA);

            JSONObject detailMenuObjectB = new JSONObject();
            detailMenuObjectB.put("namaItem", "Snack Gurih");
            JSONArray arrayPilihanB = new JSONArray();
            arrayPilihanB.put("Semar Mendem");
            detailMenuObjectB.put("pilihan", arrayPilihanB);
            jsonArrayListB.put(detailMenuObjectA);

            JSONObject detailMenuObjectC = new JSONObject();
            detailMenuObjectC.put("namaItem", "Klethikan");
            JSONArray arrayPilihanC = new JSONArray();
            arrayPilihanC.put("Kacang Telur");
            detailMenuObjectC.put("pilihan", arrayPilihanC);
            jsonArrayListB.put(detailMenuObjectC);

            listB.put("detailMenu", jsonArrayListB);

            listB.put("tanggalPengiriman", "25-08-2018");
            listB.put("jamPengiriman", "12:30");
            listB.put("atasNama", "Tia Ayu");
            listB.put("kontak", "081234567890");
            listB.put("alamat", "Jalan Mangga No 120 Sleman Yogyakarta");
            listB.put("catatan", "Kuahnya jangan banyak");
            jsonArrayRoot.put(listB);
            */

            // UNTUK BUFFET DAN PONDOKAN
            JSONObject listC = new JSONObject();
            listC.put("kategori", "Buffet");
            listC.put("menu", "Buffet A");
            listC.put("kuantitas", 120);
            listC.put("subtotal", 15000000);

            JSONArray jsonArrayListC = new JSONArray();

            JSONObject detailMenuObjectBuffetA = new JSONObject();
            detailMenuObjectBuffetA.put("namaItem", "Nasi Putih");
            JSONArray arrayPilihanBuffetA = new JSONArray();
            arrayPilihanBuffetA.put("Nasi Putih");
            detailMenuObjectBuffetA.put("pilihan", arrayPilihanBuffetA);
            jsonArrayListC.put(detailMenuObjectBuffetA);

            JSONObject detailMenuObjectBuffetB = new JSONObject();
            detailMenuObjectBuffetB.put("namaItem", "Aneka Soup");
            JSONArray arrayPilihanBuffetB = new JSONArray();
            arrayPilihanBuffetB.put("Soup Timio");
            arrayPilihanBuffetB.put("Soup Tom Yam");
            arrayPilihanBuffetB.put("Soup Ayam Roll");
            detailMenuObjectBuffetB.put("pilihan", arrayPilihanBuffetB);
            jsonArrayListC.put(detailMenuObjectBuffetB);

            JSONObject detailMenuObjectBuffetC = new JSONObject();
            detailMenuObjectBuffetC.put("namaItem", "Masakan Ayam");
            JSONArray arrayPilihanBuffetC = new JSONArray();
            arrayPilihanBuffetC.put("Chicken Kungpao");
            arrayPilihanBuffetC.put("Ayam Bumbu Bali");
            detailMenuObjectBuffetC.put("pilihan", arrayPilihanBuffetC);
            jsonArrayListC.put(detailMenuObjectBuffetC);

            JSONObject detailMenuObjectBuffetD = new JSONObject();
            detailMenuObjectBuffetD.put("namaItem", "Masakan Daging");
            JSONArray arrayPilihanBuffetD = new JSONArray();
            arrayPilihanBuffetD.put("Tumis Daging");
            arrayPilihanBuffetD.put("Sapi Lada Hitam");
            arrayPilihanBuffetD.put("Rolade Saos Steak");
            detailMenuObjectBuffetD.put("pilihan", arrayPilihanBuffetD);
            jsonArrayListC.put(detailMenuObjectBuffetD);

            JSONObject detailMenuObjectBuffetE = new JSONObject();
            detailMenuObjectBuffetE.put("namaItem", "Masakan Sayuran");
            JSONArray arrayPilihanBuffetE = new JSONArray();
            arrayPilihanBuffetE.put("Cap Cay");
            arrayPilihanBuffetE.put("Cah Ayam Brokoli");
            arrayPilihanBuffetE.put("Rolade Saos Steak");
            arrayPilihanBuffetE.put("Tumis Posol Cabai Hijau");
            detailMenuObjectBuffetE.put("pilihan", arrayPilihanBuffetE);
            jsonArrayListC.put(detailMenuObjectBuffetE);

            JSONObject detailMenuObjectBuffetF = new JSONObject();
            detailMenuObjectBuffetF.put("namaItem", "Kerupuk");
            JSONArray arrayPilihanBuffetF = new JSONArray();
            arrayPilihanBuffetF.put("Kerupuk");
            detailMenuObjectBuffetF.put("pilihan", arrayPilihanBuffetF);
            jsonArrayListC.put(detailMenuObjectBuffetF);

            JSONObject detailMenuObjectBuffetG = new JSONObject();
            detailMenuObjectBuffetG.put("namaItem", "Buah Iris");
            JSONArray arrayPilihanBuffetG = new JSONArray();
            arrayPilihanBuffetG.put("Buah Iris");
            detailMenuObjectBuffetG.put("pilihan", arrayPilihanBuffetG);
            jsonArrayListC.put(detailMenuObjectBuffetG);

            JSONObject detailMenuObjectBuffetH = new JSONObject();
            detailMenuObjectBuffetH.put("namaItem", "Air Mineral, Teh dan Es Serek");
            JSONArray arrayPilihanBuffetH = new JSONArray();
            arrayPilihanBuffetH.put("Air Mineral, Teh dan Es Serek");
            detailMenuObjectBuffetH.put("pilihan", arrayPilihanBuffetH);
            jsonArrayListC.put(detailMenuObjectBuffetH);

            JSONObject detailMenuObjectBuffetI = new JSONObject();
            detailMenuObjectBuffetI.put("namaItem", "Es Cream dan Pudding");
            JSONArray arrayPilihanBuffetI = new JSONArray();
            arrayPilihanBuffetI.put("Es Cream dan Pudding");
            detailMenuObjectBuffetI.put("pilihan", arrayPilihanBuffetI);
            jsonArrayListC.put(detailMenuObjectBuffetI);

            listC.put("detailMenu", jsonArrayListC);

            listC.put("tanggalPengiriman", "25-08-2018");
            listC.put("jamPengiriman", "12:30");
            listC.put("atasNama", "Tia Ayu");
            listC.put("kontak", "081234567890");
            listC.put("alamat", "Jalan Mangga No 120 Sleman Yogyakarta");
            listC.put("catatan", "Kuahnya jangan banyak");
            jsonArrayRoot.put(listC);

            jsonRoot.put("detailPemesanan", jsonArrayRoot);

            Log.e(TAG, "JSON Untuk Dikirim: " + jsonRoot.toString());
            jsonString = jsonRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new GoUpdate().execute();
    }

    private class GoUpdate extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PutPemesananActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            // Untuk sementara update berdasarkan pemesan
            // kalau bisa sih berdasarkan kode transaksi
            String url = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/pemesanan?apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI&q={\"pemesan\":\"mubarok\"}";
            response = sh.postUpdateAPI(url, jsonString);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            TextView tv_result = findViewById(R.id.tv_result);
            tv_result.setText(String.valueOf(response));
        }

    }
}
