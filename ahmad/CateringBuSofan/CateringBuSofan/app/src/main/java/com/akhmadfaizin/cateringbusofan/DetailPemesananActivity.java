package com.akhmadfaizin.cateringbusofan;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class DetailPemesananActivity extends AppCompatActivity {
    private String TAG = DetailPemesananActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private String id = "";
    private LinearLayout ll_detail_pemesanan;

    private String mKodeTransaksi,
            mNama,
            mUrlFoto,
            mPengaprove,
            mTanggalPemesanan,
            mApproval,
            mAlasan;
    private int mTotalBayar,
            mDp;
    private List<DetailPemesanan> listDetailPemesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan);

        id = getIntent().getStringExtra("kodeTransaksi");

        // Setting Color of Status Bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#792525"));
        }

        // Set The Initialize Action Bar title
        getSupportActionBar().setTitle("Loading ...");

        ll_detail_pemesanan = findViewById(R.id.ll_detail_pemesanan);
        listDetailPemesanan = new ArrayList<>();

        new GetDetailPemesanan().execute();
    }

    private class GetDetailPemesanan extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailPemesananActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = getString(R.string.base_url) + "pemesanan/" + id;

            // Making a request to url and getting response
            String jsonStr = sh.goGetApi(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    JSONObject p = new JSONObject(jsonStr);

                    String kodeTransaksi = p.getString("id");
                    String nama = p.getString("pemesan");
                    String urlFoto = p.getString("urlPhoto");
                    String pengaprove = p.getString("pengaprove");
                    String tanggalPemesanan = p.getString("tanggalPemesanan");
                    String approval = p.getString("approval");
                    String alasan = p.getString("alasan");
                    int totalBayar = p.getInt("totalBayar");
                    int dp = p.getInt("dp");

                    JSONArray arrayDetailPemesanan = p.getJSONArray("detailPemesanan");
                    for(int i = 0; i < arrayDetailPemesanan.length(); i ++){
                        //Initialize New Object
                        DetailPemesanan dpObject = new DetailPemesanan();

                        JSONObject objectMenu = arrayDetailPemesanan.getJSONObject(i);
                        String kategori = objectMenu.getString("kategori");
                        String paket = objectMenu.getString("menu");
                        int kuantitas = objectMenu.getInt("kuantitas");
                        int subTotal = objectMenu.getInt("subtotal");

                        JSONArray arrayDetailMenu = objectMenu.getJSONArray("detailMenu");

                        LinkedHashMap<String, List<String>> listDetailPilihan = new LinkedHashMap<>();
                        if(kategori.equals("Nasi Box")) {
                            // Perlakuan Khusus Untuk Kategori Nasi Box

                            List<String> pilihan = new ArrayList<>();
                            for (int j = 0; j < arrayDetailMenu.length(); j++) {
                                String item = arrayDetailMenu.getString(j);
                                pilihan.add(item);
                            }
                            listDetailPilihan.put("Berisi", pilihan);

                        } else {
                            // Perlakuan Untuk Kategori Lainnya
                            for (int j = 0; j < arrayDetailMenu.length(); j++) {
                                JSONObject objectDetailMenu = arrayDetailMenu.getJSONObject(j);
                                String namaItem = objectDetailMenu.getString("namaItem");

                                JSONArray arrayPilihan = objectDetailMenu.getJSONArray("pilihan");
                                List<String> pilihan = new ArrayList<>();
                                for (int k = 0; k < arrayPilihan.length(); k ++) {
                                    String item = arrayPilihan.getString(k);
                                    pilihan.add(item);
                                }
                                listDetailPilihan.put(namaItem, pilihan);
                            }

                        }

                        String tanggalPengiriman = objectMenu.getString("tanggalPengiriman");
                        String jamPengiriman = objectMenu.getString("jamPengiriman");
                        String atasNama = objectMenu.getString("atasNama");
                        String kontak = objectMenu.getString("kontak");
                        String alamat = objectMenu.getString("alamat");
                        String catatan = objectMenu.getString("catatan");

                        dpObject.setKategori(kategori);
                        dpObject.setPaket(paket);
                        dpObject.setKuantitas(kuantitas);
                        dpObject.setSubTotal(subTotal);
                        dpObject.setTanggalPengiriman(tanggalPengiriman);
                        dpObject.setJamPengiriman(jamPengiriman);
                        dpObject.setAtasNama(atasNama);
                        dpObject.setKontak(kontak);
                        dpObject.setAlamat(alamat);
                        dpObject.setCatatan(catatan);
                        dpObject.setListDetailPilihan(listDetailPilihan);

                        listDetailPemesanan.add(dpObject);
                    }

                    // Setting value ke Detail Pemesanan Selected
                    mKodeTransaksi = kodeTransaksi;
                    mNama = nama;
                    mUrlFoto = urlFoto;
                    mPengaprove = pengaprove;
                    mTanggalPemesanan = tanggalPemesanan;
                    mApproval = approval;
                    mAlasan = alasan;
                    mTotalBayar = totalBayar;
                    mDp = dp;

                    //Log.e(TAG, "Jumlah Pesanan = " + String.valueOf(pesananItem.size()));

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
            } else {
                //Log.e(TAG, "Couldn't get json from server.");
                Log.e(TAG, "json from server is either null or [ ].");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            // Set The Action Bar Title
            getSupportActionBar().setTitle("Detail Pemesanan");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            ScrollView scrollView = findViewById(R.id.detail_pemesanan_scrollview);
            scrollView.setVisibility(View.VISIBLE);

            TextView tv_kodePemesanan = findViewById(R.id.tv_kodePemesanan);
            tv_kodePemesanan.setText(mKodeTransaksi);

            TextView tv_namaPemesan = findViewById(R.id.tv_nama_pemesan);
            tv_namaPemesan.setText(mNama);

            TextView tv_totalBayar = findViewById(R.id.tv_totalBayar);
            tv_totalBayar.setText("Rp " + String.format("%,d", mTotalBayar));

            TextView tv_dp = findViewById(R.id.tv_dp);
            tv_dp.setText("Rp " + String.format("%,d", mDp));

            TextView tv_Approval = findViewById(R.id.tv_approval);
            tv_Approval.setText(mApproval);

            TextView tv_alasan = findViewById(R.id.tv_alasan);
            tv_alasan.setText(mAlasan);

            for(int i = 0; i < listDetailPemesanan.size(); i++) {
                DetailPemesanan d = listDetailPemesanan.get(i);
                String kategori = d.getKategori();
                String paket = d.getPaket();
                int kuantitas = d.getKuantitas();
                int subTotal = d.getSubTotal();
                String tanggalPengiriman = d.getTanggalPengiriman();
                String jamPengiriman = d.getJamPengiriman();
                String atasNama = d.getAtasNama();
                String kontak = d.getKontak();
                String alamat = d.getAlamat();

                String catatan = "";
                if(TextUtils.isEmpty(d.getCatatan())) {
                    catatan = "-";
                } else {
                    catatan = d.getCatatan();
                }

                setGarisVertical();
                setBoldTitleTextView("Kategori");
                setNormalTextView(kategori);
                setBoldTitleTextView("Paket");
                setNormalTextView(paket);
                setBoldTitleTextView("Kuantitas");
                setNormalTextView(String.format("%,d", kuantitas));
                setBoldTitleTextView("Sub Total");
                setNormalTextView("Rp " + String.format("%,d", subTotal));

                if(!kategori.equals("Nasi Box")) {
                    setBoldTitleTextView("\n\nPaket Terdiri Dari :");
                }

                LinkedHashMap<String, List<String>> listDetailPilihan = d.getListDetailPilihan();
                for (LinkedHashMap.Entry<String, List<String>> entry : listDetailPilihan.entrySet()) {
                    String key = entry.getKey();
                    if(kategori.equals("Nasi Box")) {
                        setBoldTitleTextView("\n\n" + key);
                    } else {
                        setBoldTitleTextView(key);
                    }


                    List<String> value = entry.getValue();

                    for (int k = 0; k < value.size(); k++) {
                        String item = value.get(k);
                        setNormalTextView(item);
                    }
                }

                setBoldTitleTextView("\n\nAtas Nama");
                setNormalTextView(atasNama);
                setBoldTitleTextView("Kontak");
                setNormalTextView(kontak);
                setBoldTitleTextView("Tanggal Pengiriman");
                setNormalTextView(getTanggalIndonesiaFormat(tanggalPengiriman));
                setBoldTitleTextView("Jam Pengiriman");
                setNormalTextView(jamPengiriman);
                setBoldTitleTextView("Alamat");
                setNormalTextView(alamat);
                setBoldTitleTextView("Catatan");
                setNormalTextView(catatan);

            }


        }
    }

    public String getTanggalIndonesiaFormat(String s) {
        // Split Tanggal, Bulan, Tahun
        String[] jsonDateSplit = s.split("-");
        int jsonDateDay = Integer.valueOf(jsonDateSplit[0]);
        int jsonDateMonth = Integer.valueOf(jsonDateSplit[1]) - 1;
        int jsonDateYear = Integer.valueOf(jsonDateSplit[2]);
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.YEAR, jsonDateYear);
        myCalendar.set(Calendar.MONTH, jsonDateMonth);
        myCalendar.set(Calendar.DAY_OF_MONTH, jsonDateDay);

        String viewFormat = "dd MMMM yyyy";
        Locale id = new Locale("in", "ID");
        SimpleDateFormat sdfView = new SimpleDateFormat(viewFormat, id);

        String dateIndonesiaFormat = sdfView.format(myCalendar.getTime());

        return dateIndonesiaFormat;
    }

    public void setNormalTextView(String s) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(this);
        tv.setText(s);
        tv.setTextSize(22);
        tv.setTextColor(Color.parseColor("#616161"));
        tv.setLayoutParams(lp);
        ll_detail_pemesanan.addView(tv);
    }

    public void setBoldTitleTextView(String s) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 16, 0, 0);
        TextView tv = new TextView(this);
        tv.setText(s);
        tv.setTextSize(22);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextColor(Color.parseColor("#000000"));
        tv.setLayoutParams(lp);
        ll_detail_pemesanan.addView(tv);
    }

    public void setGarisVertical() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
        lp.setMargins(0,28,0, 20);
        View view = new View(this);
        view.setBackgroundColor(Color.parseColor("#A49595"));
        view.setLayoutParams(lp);
        ll_detail_pemesanan.addView(view);
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
