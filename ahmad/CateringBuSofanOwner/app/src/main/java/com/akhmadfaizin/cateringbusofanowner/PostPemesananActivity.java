package com.akhmadfaizin.cateringbusofanowner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.TreeMap;

public class PostPemesananActivity extends AppCompatActivity {
    private String TAG = PostPemesananActivity.class.getSimpleName();
    private String jsonString = "";
    Boolean response = false;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_pemesanan);

        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        String pemesan = sp.getString("username", null);
        String urlphoto = "https://image.ibb.co/n3Y6PS/baksoc.jpg";
        String pengaprove = "";
        String tanggalPemesanan = getCurrentDate();
        String approval = "Belum Disetujui";
        int totalBayar = getIntent().getIntExtra("totalBayar", 0);
        int dp = 0;
        String status = "1";

        // Create The Json
        try {
            JSONObject jsonRoot = new JSONObject();
            jsonRoot.put("pemesan", pemesan);
            jsonRoot.put("urlPhoto", urlphoto);
            jsonRoot.put("pengaprove", pengaprove);
            jsonRoot.put("tanggalPemesanan", tanggalPemesanan);
            jsonRoot.put("approval", approval);
            jsonRoot.put("totalBayar", totalBayar);
            jsonRoot.put("dp", dp);
            jsonRoot.put("status", status);

            JSONArray jsonArrayRoot = getDetailPemesananJSONArray();
            jsonRoot.put("detailPemesanan", jsonArrayRoot);

            Log.e(TAG, "JSON Untuk Dikirim: " + jsonRoot.toString());
            jsonString = jsonRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new GoPost().execute();

    }

    private class GoPost extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PostPemesananActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            // Selain untuk POST, url ini juga bisa buat GET, jadi bisa buat ngecek
            // perubahan setelah POST
            String url = getString(R.string.base_url) + "pemesanan";

            response = sh.postInsertAPI(url, jsonString);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            TextView tv_result = findViewById(R.id.tv_result);
            String message = "";
            if(response) {
                message = "Order Berhasil Disubmit";

                // Reset Semua Static Class
                PackageNasiASelect.reset();
                PackageNasiBSelect.reset();
                PackageNasiCSelect.reset();
                PackageNasiDSelect.reset();
                PackageSnackASelect.reset();
                PackageSnackBSelect.reset();
                PackageBuffetASelect.reset();
                PackageBuffetBSelect.reset();
                PackageBuffetCSelect.reset();
                PackagePondokanSelect.reset();
            }else{
                message = "Order Gagal Disubmit";
            }

            tv_result.setText(message);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(i);
    }

    // Get Current Date and Format it the return the string
    public String getCurrentDate() {
        Date currentTime = Calendar.getInstance().getTime();
        String jsonFormat = "dd-MM-yyyy";
        SimpleDateFormat sdfJson = new SimpleDateFormat(jsonFormat, Locale.US);

        return sdfJson.format(currentTime);
    }

    public JSONArray getDetailPemesananJSONArray() {
        JSONArray jsonArrayRoot = new JSONArray();

        // NASI BOX A
        if(PackageNasiASelect.getNamaPackage() != null && !PackageNasiASelect.getNamaPackage().isEmpty()) {
            LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = PackageNasiASelect.getCollectionSelected();

            try {
                JSONObject listObject = new JSONObject();
                listObject.put("kategori", PackageNasiASelect.getNamaKategori());
                listObject.put("menu", PackageNasiASelect.getNamaPackage());
                listObject.put("kuantitas", PackageNasiASelect.getKuantitas());
                listObject.put("subtotal", PackageNasiASelect.getSubTotalPrice());

                JSONArray jsonArrayList = new JSONArray();

                for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                    String key = entry.getKey();
                    TreeMap<Integer, PackageChoice> value = entry.getValue();

                    for (PackageChoice p : value.values()) {
                        jsonArrayList.put(p.getNama());
                    }

                }

                listObject.put("detailMenu", jsonArrayList);
                listObject.put("tanggalPengiriman", PackageNasiASelect.getTanggalPengiriman());
                listObject.put("jamPengiriman", PackageNasiASelect.getJamPengiriman());
                listObject.put("atasNama", PackageNasiASelect.getAtasNama());
                listObject.put("kontak", PackageNasiASelect.getKontak());
                listObject.put("alamat", PackageNasiASelect.getAlamat());
                listObject.put("catatan", PackageNasiASelect.getCatatan());
                jsonArrayRoot.put(listObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // NASI BOX B
        if(PackageNasiBSelect.getNamaPackage() != null && !PackageNasiBSelect.getNamaPackage().isEmpty()) {
            LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = PackageNasiBSelect.getCollectionSelected();

            try {
                JSONObject listObject = new JSONObject();
                listObject.put("kategori", PackageNasiBSelect.getNamaKategori());
                listObject.put("menu", PackageNasiBSelect.getNamaPackage());
                listObject.put("kuantitas", PackageNasiBSelect.getKuantitas());
                listObject.put("subtotal", PackageNasiBSelect.getSubTotalPrice());

                JSONArray jsonArrayList = new JSONArray();

                for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                    String key = entry.getKey();
                    TreeMap<Integer, PackageChoice> value = entry.getValue();

                    for (PackageChoice p : value.values()) {
                        jsonArrayList.put(p.getNama());
                    }

                }

                listObject.put("detailMenu", jsonArrayList);
                listObject.put("tanggalPengiriman", PackageNasiBSelect.getTanggalPengiriman());
                listObject.put("jamPengiriman", PackageNasiBSelect.getJamPengiriman());
                listObject.put("atasNama", PackageNasiBSelect.getAtasNama());
                listObject.put("kontak", PackageNasiBSelect.getKontak());
                listObject.put("alamat", PackageNasiBSelect.getAlamat());
                listObject.put("catatan", PackageNasiBSelect.getCatatan());
                jsonArrayRoot.put(listObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // NASI BOX C
        if(PackageNasiCSelect.getNamaPackage() != null && !PackageNasiCSelect.getNamaPackage().isEmpty()) {
            LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = PackageNasiCSelect.getCollectionSelected();

            try {
                JSONObject listObject = new JSONObject();
                listObject.put("kategori", PackageNasiCSelect.getNamaKategori());
                listObject.put("menu", PackageNasiCSelect.getNamaPackage());
                listObject.put("kuantitas", PackageNasiCSelect.getKuantitas());
                listObject.put("subtotal", PackageNasiCSelect.getSubTotalPrice());

                JSONArray jsonArrayList = new JSONArray();

                for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                    String key = entry.getKey();
                    TreeMap<Integer, PackageChoice> value = entry.getValue();

                    for (PackageChoice p : value.values()) {
                        jsonArrayList.put(p.getNama());
                    }

                }

                listObject.put("detailMenu", jsonArrayList);
                listObject.put("tanggalPengiriman", PackageNasiCSelect.getTanggalPengiriman());
                listObject.put("jamPengiriman", PackageNasiCSelect.getJamPengiriman());
                listObject.put("atasNama", PackageNasiCSelect.getAtasNama());
                listObject.put("kontak", PackageNasiCSelect.getKontak());
                listObject.put("alamat", PackageNasiCSelect.getAlamat());
                listObject.put("catatan", PackageNasiCSelect.getCatatan());
                jsonArrayRoot.put(listObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // NASI BOX D
        if(PackageNasiDSelect.getNamaPackage() != null && !PackageNasiDSelect.getNamaPackage().isEmpty()) {
            LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = PackageNasiDSelect.getCollectionSelected();

            try {
                JSONObject listObject = new JSONObject();
                listObject.put("kategori", PackageNasiDSelect.getNamaKategori());
                listObject.put("menu", PackageNasiDSelect.getNamaPackage());
                listObject.put("kuantitas", PackageNasiDSelect.getKuantitas());
                listObject.put("subtotal", PackageNasiDSelect.getSubTotalPrice());

                JSONArray jsonArrayList = new JSONArray();

                for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                    String key = entry.getKey();
                    TreeMap<Integer, PackageChoice> value = entry.getValue();

                    for (PackageChoice p : value.values()) {
                        jsonArrayList.put(p.getNama());
                    }

                }

                listObject.put("detailMenu", jsonArrayList);
                listObject.put("tanggalPengiriman", PackageNasiDSelect.getTanggalPengiriman());
                listObject.put("jamPengiriman", PackageNasiDSelect.getJamPengiriman());
                listObject.put("atasNama", PackageNasiDSelect.getAtasNama());
                listObject.put("kontak", PackageNasiDSelect.getKontak());
                listObject.put("alamat", PackageNasiDSelect.getAlamat());
                listObject.put("catatan", PackageNasiDSelect.getCatatan());
                jsonArrayRoot.put(listObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // SNACK A
        if (PackageSnackASelect.getNamaPackage() != null && !PackageSnackASelect.getNamaPackage().isEmpty()) {
            LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = PackageSnackASelect.getCollectionSelected();

            try {

                JSONObject list = new JSONObject();
                list.put("kategori", PackageSnackASelect.getNamaKategori());
                list.put("menu", PackageSnackASelect.getNamaPackage());
                list.put("kuantitas", PackageSnackASelect.getKuantitas());
                list.put("subtotal", PackageSnackASelect.getSubTotalPrice());

                JSONArray jsonArrayList = new JSONArray();

                for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                    String key = entry.getKey();
                    TreeMap<Integer, PackageChoice> value = entry.getValue();

                    JSONObject detailMenuObject = new JSONObject();
                    detailMenuObject.put("namaItem", key);

                    JSONArray arrayPilihan = new JSONArray();
                    for (PackageChoice p : value.values()) {
                        arrayPilihan.put(p.getNama());
                    }
                    detailMenuObject.put("pilihan", arrayPilihan);
                    jsonArrayList.put(detailMenuObject);
                }

                list.put("detailMenu", jsonArrayList);
                list.put("tanggalPengiriman", PackageSnackASelect.getTanggalPengiriman());
                list.put("jamPengiriman", PackageSnackASelect.getJamPengiriman());
                list.put("atasNama", PackageSnackASelect.getAtasNama());
                list.put("kontak", PackageSnackASelect.getKontak());
                list.put("alamat", PackageSnackASelect.getAlamat());
                list.put("catatan", PackageSnackASelect.getCatatan());
                jsonArrayRoot.put(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // SNACK B
        if (PackageSnackBSelect.getNamaPackage() != null && !PackageSnackBSelect.getNamaPackage().isEmpty()) {
            LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = PackageSnackBSelect.getCollectionSelected();

            try {

                JSONObject list = new JSONObject();
                list.put("kategori", PackageSnackBSelect.getNamaKategori());
                list.put("menu", PackageSnackBSelect.getNamaPackage());
                list.put("kuantitas", PackageSnackBSelect.getKuantitas());
                list.put("subtotal", PackageSnackBSelect.getSubTotalPrice());

                JSONArray jsonArrayList = new JSONArray();

                for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                    String key = entry.getKey();
                    TreeMap<Integer, PackageChoice> value = entry.getValue();

                    JSONObject detailMenuObject = new JSONObject();
                    detailMenuObject.put("namaItem", key);

                    JSONArray arrayPilihan = new JSONArray();
                    for (PackageChoice p : value.values()) {
                        arrayPilihan.put(p.getNama());
                    }
                    detailMenuObject.put("pilihan", arrayPilihan);
                    jsonArrayList.put(detailMenuObject);
                }

                list.put("detailMenu", jsonArrayList);
                list.put("tanggalPengiriman", PackageSnackBSelect.getTanggalPengiriman());
                list.put("jamPengiriman", PackageSnackBSelect.getJamPengiriman());
                list.put("atasNama", PackageSnackBSelect.getAtasNama());
                list.put("kontak", PackageSnackBSelect.getKontak());
                list.put("alamat", PackageSnackBSelect.getAlamat());
                list.put("catatan", PackageSnackBSelect.getCatatan());
                jsonArrayRoot.put(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // BUFFET A
        if (PackageBuffetASelect.getNamaPackage() != null && !PackageBuffetASelect.getNamaPackage().isEmpty()) {
            LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = PackageBuffetASelect.getCollectionSelected();

            try {

                JSONObject list = new JSONObject();
                list.put("kategori", PackageBuffetASelect.getNamaKategori());
                list.put("menu", PackageBuffetASelect.getNamaPackage());
                list.put("kuantitas", PackageBuffetASelect.getKuantitas());
                list.put("subtotal", PackageBuffetASelect.getSubTotalPrice());

                JSONArray jsonArrayList = new JSONArray();

                for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                    String key = entry.getKey();
                    TreeMap<Integer, PackageChoice> value = entry.getValue();

                    JSONObject detailMenuObject = new JSONObject();
                    detailMenuObject.put("namaItem", key);

                    JSONArray arrayPilihan = new JSONArray();
                    for (PackageChoice p : value.values()) {
                        arrayPilihan.put(p.getNama());
                    }
                    detailMenuObject.put("pilihan", arrayPilihan);
                    jsonArrayList.put(detailMenuObject);
                }

                list.put("detailMenu", jsonArrayList);
                list.put("tanggalPengiriman", PackageBuffetASelect.getTanggalPengiriman());
                list.put("jamPengiriman", PackageBuffetASelect.getJamPengiriman());
                list.put("atasNama", PackageBuffetASelect.getAtasNama());
                list.put("kontak", PackageBuffetASelect.getKontak());
                list.put("alamat", PackageBuffetASelect.getAlamat());
                list.put("catatan", PackageBuffetASelect.getCatatan());
                jsonArrayRoot.put(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // BUFFET B
        if (PackageBuffetBSelect.getNamaPackage() != null && !PackageBuffetBSelect.getNamaPackage().isEmpty()) {
            LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = PackageBuffetBSelect.getCollectionSelected();

            try {

                JSONObject list = new JSONObject();
                list.put("kategori", PackageBuffetBSelect.getNamaKategori());
                list.put("menu", PackageBuffetBSelect.getNamaPackage());
                list.put("kuantitas", PackageBuffetBSelect.getKuantitas());
                list.put("subtotal", PackageBuffetBSelect.getSubTotalPrice());

                JSONArray jsonArrayList = new JSONArray();

                for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                    String key = entry.getKey();
                    TreeMap<Integer, PackageChoice> value = entry.getValue();

                    JSONObject detailMenuObject = new JSONObject();
                    detailMenuObject.put("namaItem", key);

                    JSONArray arrayPilihan = new JSONArray();
                    for (PackageChoice p : value.values()) {
                        arrayPilihan.put(p.getNama());
                    }
                    detailMenuObject.put("pilihan", arrayPilihan);
                    jsonArrayList.put(detailMenuObject);
                }

                list.put("detailMenu", jsonArrayList);
                list.put("tanggalPengiriman", PackageBuffetBSelect.getTanggalPengiriman());
                list.put("jamPengiriman", PackageBuffetBSelect.getJamPengiriman());
                list.put("atasNama", PackageBuffetBSelect.getAtasNama());
                list.put("kontak", PackageBuffetBSelect.getKontak());
                list.put("alamat", PackageBuffetBSelect.getAlamat());
                list.put("catatan", PackageBuffetBSelect.getCatatan());
                jsonArrayRoot.put(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // BUFFET C
        if (PackageBuffetCSelect.getNamaPackage() != null && !PackageBuffetCSelect.getNamaPackage().isEmpty()) {
            LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = PackageBuffetCSelect.getCollectionSelected();

            try {

                JSONObject list = new JSONObject();
                list.put("kategori", PackageBuffetCSelect.getNamaKategori());
                list.put("menu", PackageBuffetCSelect.getNamaPackage());
                list.put("kuantitas", PackageBuffetCSelect.getKuantitas());
                list.put("subtotal", PackageBuffetCSelect.getSubTotalPrice());

                JSONArray jsonArrayList = new JSONArray();

                for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                    String key = entry.getKey();
                    TreeMap<Integer, PackageChoice> value = entry.getValue();

                    JSONObject detailMenuObject = new JSONObject();
                    detailMenuObject.put("namaItem", key);

                    JSONArray arrayPilihan = new JSONArray();
                    for (PackageChoice p : value.values()) {
                        arrayPilihan.put(p.getNama());
                    }
                    detailMenuObject.put("pilihan", arrayPilihan);
                    jsonArrayList.put(detailMenuObject);
                }

                list.put("detailMenu", jsonArrayList);
                list.put("tanggalPengiriman", PackageBuffetCSelect.getTanggalPengiriman());
                list.put("jamPengiriman", PackageBuffetCSelect.getJamPengiriman());
                list.put("atasNama", PackageBuffetCSelect.getAtasNama());
                list.put("kontak", PackageBuffetCSelect.getKontak());
                list.put("alamat", PackageBuffetCSelect.getAlamat());
                list.put("catatan", PackageBuffetCSelect.getCatatan());
                jsonArrayRoot.put(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // PONDOKAN
        if (PackagePondokanSelect.getNamaPackage() != null && !PackagePondokanSelect.getNamaPackage().isEmpty()) {
            LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = PackagePondokanSelect.getCollectionSelected();

            try {

                JSONObject list = new JSONObject();
                list.put("kategori", PackagePondokanSelect.getNamaKategori());
                list.put("menu", PackagePondokanSelect.getNamaPackage());
                list.put("kuantitas", PackagePondokanSelect.getKuantitas());
                list.put("subtotal", PackagePondokanSelect.getSubTotalPrice());

                JSONArray jsonArrayList = new JSONArray();

                for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                    String key = entry.getKey();
                    TreeMap<Integer, PackageChoice> value = entry.getValue();

                    JSONObject detailMenuObject = new JSONObject();
                    detailMenuObject.put("namaItem", key);

                    JSONArray arrayPilihan = new JSONArray();
                    for (PackageChoice p : value.values()) {
                        arrayPilihan.put(p.getNama());
                    }
                    detailMenuObject.put("pilihan", arrayPilihan);
                    jsonArrayList.put(detailMenuObject);
                }

                list.put("detailMenu", jsonArrayList);
                list.put("tanggalPengiriman", PackagePondokanSelect.getTanggalPengiriman());
                list.put("jamPengiriman", PackagePondokanSelect.getJamPengiriman());
                list.put("atasNama", PackagePondokanSelect.getAtasNama());
                list.put("kontak", PackagePondokanSelect.getKontak());
                list.put("alamat", PackagePondokanSelect.getAlamat());
                list.put("catatan", PackagePondokanSelect.getCatatan());
                jsonArrayRoot.put(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return jsonArrayRoot;
    }
}
