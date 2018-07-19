package com.akhmadfaizin.cateringbusofan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

public class PackageNasiAActivity extends AppCompatActivity {

    private String TAG = PackageNasiAActivity.class.getSimpleName();
    List<List<PackageChoice>> listChoices;  // Store List Of Packages Choices available
    private ProgressDialog pDialog;
    private String NECESSITY;

    private ListView listViewA;
    private TextView titleA,
            pricePorsi;
    private int defaultPrice, additionalPrice = 0;
    private TreeMap<Integer, PackageChoice> selectedBoxA;
    private LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = new LinkedHashMap<String, TreeMap<Integer, PackageChoice>>();
    private HashMap<String, Object> packageData;    // Get One Data just For That Package

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_nasi_a);

        NECESSITY = getIntent().getStringExtra("NECESSITY");

        // Setting Color of Status Bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#792525"));
        }

        // Set The Initialize Action Bar title
        getSupportActionBar().setTitle("Loading ...");

        packageData = new HashMap<>();
        listChoices = new ArrayList<>();

        // Execute AsyncTask To Parsing JSON for Snack A
        new GetPackage().execute();
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetPackage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PackageNasiAActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // URL to get JSON
            String url = getString(R.string.base_url) + "menu?category=Nasi%20Box";

            // Making a request to url and getting response
            String jsonStr = sh.goGetApi(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    JSONArray root = new JSONArray(jsonStr);
                    JSONObject jsonObject = root.getJSONObject(0);
                    JSONArray jsonArray = jsonObject.getJSONArray("menu");
                    // The Index Number 0 is for Nasi A
                    JSONObject jsonObjectPackage = jsonArray.getJSONObject(0);

                    String package_name = jsonObjectPackage.getString("nama_menu");
                    int harga_default = jsonObjectPackage.getInt("harga_default");
                    String deskripsi = jsonObjectPackage.getString("deskripsi");
                    String url_img = jsonObjectPackage.getString("url_img");

                    JSONArray jsonMenuArray = jsonObjectPackage.getJSONArray("detail_menu");
                    List<String> detail_menu = new ArrayList<>();
                    detail_menu.add("Berisi");  // Hanya satu khusus untuk Nasi Box

                    Log.e(TAG, "PANJANG DETAIL MENU = " + String.valueOf(detail_menu.size()));

                    // Save The Result
                    packageData.put("package_name", package_name);
                    packageData.put("harga_default", harga_default);
                    packageData.put("deskripsi", deskripsi);
                    packageData.put("url_img", url_img);
                    packageData.put("detail_menu", detail_menu);

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
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            // Fetching Image
            Picasso.with(PackageNasiAActivity.this)
                    .load(String.valueOf(packageData.get("url_img")))
                    .fetch();

            // Getting The Choice Package JSON
            String urla = getString(R.string.base_url) + "item?namaItem=Nasi%20Putih%20Box";
            String urlb = getString(R.string.base_url) + "item?namaItem=Masakan%20Ayam%20Box";
            String urlc = getString(R.string.base_url) + "item?namaItem=Masakan%20Sayuran%20Box";
            String urld = getString(R.string.base_url) + "item?namaItem=Masakan%20Pelengkap";
            String urle = getString(R.string.base_url) + "item?namaItem=Pudding";
            String urlf = getString(R.string.base_url) + "item?namaItem=Air%20Mineral%20Box";
            String urlg = getString(R.string.base_url) + "item?namaItem=Kerupuk%20Box";

            // Making a request to url and getting response
            String jsonStr_a = sh.goGetApi(urla);
            String jsonStr_b = sh.goGetApi(urlb);
            String jsonStr_c = sh.goGetApi(urlc);
            String jsonStr_d = sh.goGetApi(urld);
            String jsonStr_e = sh.goGetApi(urle);
            String jsonStr_f = sh.goGetApi(urlf);
            String jsonStr_g = sh.goGetApi(urlg);

            // List to contain the json response
            List<String> jsonList = new ArrayList<>();
            jsonList.add(jsonStr_a);
            jsonList.add(jsonStr_b);
            jsonList.add(jsonStr_c);
            jsonList.add(jsonStr_d);
            jsonList.add(jsonStr_e);
            jsonList.add(jsonStr_f);
            jsonList.add(jsonStr_g);

            Log.e(TAG, "Response from url: " + jsonStr_a);
            Log.e(TAG, "Response from url: " + jsonStr_b);
            Log.e(TAG, "Response from url: " + jsonStr_c);
            Log.e(TAG, "Response from url: " + jsonStr_d);
            Log.e(TAG, "Response from url: " + jsonStr_e);
            Log.e(TAG, "Response from url: " + jsonStr_f);
            Log.e(TAG, "Response from url: " + jsonStr_g);

            if (jsonStr_a != null && jsonStr_b != null && jsonStr_c != null && jsonStr_d != null && jsonStr_e != null  && jsonStr_f != null && jsonStr_g != null) {
                try {

                    List<PackageChoice> choices = new ArrayList<>();
                    for(int i = 0; i < jsonList.size(); i++) {

                        JSONArray root = new JSONArray(jsonList.get(i));
                        JSONObject jsonObject = root.getJSONObject(0);

                        String nama = jsonObject.getString("namaItem");
                        String deskripsi = jsonObject.getString("deskripsi");
                        int harga = jsonObject.getInt("harga");
                        String urlImg = jsonObject.getString("urlImg");

                        choices.add(new PackageChoice(true, nama, deskripsi, harga, urlImg));

                    }
                    listChoices.add(choices);

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
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            final ScrollView scrollView = findViewById(R.id.menu_package_scrollview);
            // To Scroll to the Top when Opening The Activity
            scrollView.smoothScrollTo(0, 0);

            // Set The Action Bar Title
            getSupportActionBar().setTitle(String.valueOf(packageData.get("package_name")));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Setting The Package Image
            ImageView iv_package = findViewById(R.id.iv_package);
            String urlfoto = getString(R.string.base_url) + "makanan/" + String.valueOf(packageData.get("url_img"));
            Picasso.with(PackageNasiAActivity.this)
                    .load(urlfoto)
                    .into(iv_package);

            // Set Package Name
            TextView tv_package_name = findViewById(R.id.tv_package_name);
            tv_package_name.setText(String.valueOf(packageData.get("package_name")));

            // Showing Some Text When AsyncTask is done
            TextView tv_note_harga_per_porsi = findViewById(R.id.tv_note_harga_per_porsi);
            tv_note_harga_per_porsi.setVisibility(View.VISIBLE);

            // Set Additional Price
            for (int i = 0; i < listChoices.get(0).size(); i++) {
                additionalPrice += listChoices.get(0).get(i).getHarga();
            }

            // Set Harga Porsi
            defaultPrice = (Integer) packageData.get("harga_default");
            String strDefaultPrice = String.format("%,d", (defaultPrice + additionalPrice));

            pricePorsi = findViewById(R.id.tv_harga_default);
            pricePorsi.setText("Rp " + strDefaultPrice);

            // Set Deskripsi
            TextView tv_deskripsi = findViewById(R.id.tv_deskripsi);
            tv_deskripsi.setText(String.valueOf(packageData.get("deskripsi")));

            listViewA = (ListView) findViewById(R.id.lv_package_a);

            // Getting The Package Choices
            List<String> menu = (List<String>) packageData.get("detail_menu");

            titleA = findViewById(R.id.tv_user_title_a);

            titleA.setText(menu.get(0));
            titleA.setVisibility(View.VISIBLE); // Set to visible because It is invisible when doing AsyncTask

            // Initialize Selected Box
            selectedBoxA = new TreeMap<Integer, PackageChoice>();

            // Setting Adapter For Each ListView
            final PackageNoActionAdapter adapterA = new PackageNoActionAdapter(PackageNasiAActivity.this, listChoices.get(0));
            listViewA.setAdapter(adapterA);

            ListUtils.setDynamicHeight(listViewA);

            listViewA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(0).get(i);

                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    String urlfoto = getString(R.string.base_url) + "makanan/" + model.getUrlImg();
                    intent.putExtra("URLIMG", urlfoto);

                    startActivity(intent);
                }
            });

            Button btnProcess = findViewById(R.id.btn_process);
            btnProcess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    for (int i = 0; i < listChoices.get(0).size(); i++) {
                        PackageChoice model = listChoices.get(0).get(i);
                        selectedBoxA.put(i, model);
                    }

                    collectionSelected.put(titleA.getText().toString(), selectedBoxA);

                    PackageNasiASelect.setTemporaryCollectionSelected(collectionSelected);

                    Intent i = new Intent(getApplicationContext(), PackageInputDetailPengirimanActivity.class);
                    i.putExtra("NamaKategori", "Nasi Box");
                    i.putExtra("NamaPackage", String.valueOf(packageData.get("package_name")));
                    i.putExtra("DefaultPrice", (Integer) packageData.get("harga_default"));
                    i.putExtra("FinalPrice", (defaultPrice + additionalPrice));
                    i.putExtra("NECESSITY", getIntent().getStringExtra("NECESSITY"));
                    startActivity(i);
                }
            });


        }

    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

}