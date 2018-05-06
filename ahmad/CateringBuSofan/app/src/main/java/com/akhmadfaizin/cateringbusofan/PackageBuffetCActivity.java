package com.akhmadfaizin.cateringbusofan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
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

public class PackageBuffetCActivity extends AppCompatActivity {

    private String TAG = PackageBuffetCActivity.class.getSimpleName();
    List<List<PackageChoice>> listChoices;  // Store List Of Packages Choices available
    private ProgressDialog pDialog;

    private ListView listViewA,
            listViewB ,
            listViewC,
            listViewD,
            listViewE,
            listViewF,
            listViewG,
            listViewH,
            listViewI,
            listViewJ,
            listViewK,
            listViewL;
    private TextView titleA,
            titleB,
            titleC,
            titleD,
            titleE,
            titleF,
            titleG,
            titleH,
            titleI,
            titleJ,
            titleK,
            titleL,
            pricePorsi;
    private int defaultPrice;
    private HashMap<Integer, PackageChoice> selectedBoxA,
                                            selectedBoxB,
                                            selectedBoxC,
                                            selectedBoxD,
                                            selectedBoxE,
                                            selectedBoxF,
                                            selectedBoxG,
                                            selectedBoxH,
                                            selectedBoxI,
                                            selectedBoxJ,
                                            selectedBoxK,
                                            selectedBoxL;
    private LinkedHashMap<String, HashMap<Integer, PackageChoice>> collectionSelected = new LinkedHashMap<String, HashMap<Integer, PackageChoice>>();
    private HashMap<Integer, Integer> additionalPrice; // To keep update the new price per porsi
    private HashMap<String, Object> packageData;    // Get One Data just For That Package

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_buffet_c);

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

        // Execute AsyncTask To Parsing JSON for Snack B
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
            pDialog = new ProgressDialog(PackageBuffetCActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // URL to get contacts JSON Snack B
            String url = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/menu?q={%22category%22:%22Buffet%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    JSONArray root = new JSONArray(jsonStr);
                    JSONObject jsonObject = root.getJSONObject(0);
                    JSONArray jsonArray = jsonObject.getJSONArray("menu");
                    // The Index Number 2 is for Buffet C
                    JSONObject jsonObjectPackage = jsonArray.getJSONObject(2);

                    String package_name = jsonObjectPackage.getString("nama_menu");
                    int harga_default = jsonObjectPackage.getInt("harga_default");
                    String deskripsi = jsonObjectPackage.getString("deskripsi");
                    String url_img = jsonObjectPackage.getString("url_img");

                    JSONArray jsonMenuArray = jsonObjectPackage.getJSONArray("detail_menu");
                    List<String> detail_menu = new ArrayList<>();
                    for (int i = 0; i < jsonMenuArray.length(); i++) {
                        detail_menu.add(jsonMenuArray.getString(i));
                    }

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
            Picasso.with(PackageBuffetCActivity.this)
                    .load(String.valueOf(packageData.get("url_img")))
                    .fetch();

            // Getting The Choice Package JSON
            String urla = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Nasi%20Putih%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urlb = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Aneka%20Soup%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urlc = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Masakan%20Ayam%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urld = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Masakan%20Daging%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urle = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Masakan%20Seafood%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urlf = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Masakan%20Sayuran%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urlg = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Masakan%20Pelengkap%20Aneka%20Sayur%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urlh = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Aneka%20Penyegar%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urli = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Kerupuk%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urlj = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Buah%20Iris%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urlk = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Air%20Mineral,%20Teh%20dan%20Es%20Serek%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urll = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Es%20Cream%20dan%20Pudding%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";

            // Making a request to url and getting response
            String jsonStr_a = sh.makeServiceCall(urla);
            String jsonStr_b = sh.makeServiceCall(urlb);
            String jsonStr_c = sh.makeServiceCall(urlc);
            String jsonStr_d = sh.makeServiceCall(urld);
            String jsonStr_e = sh.makeServiceCall(urle);
            String jsonStr_f = sh.makeServiceCall(urlf);
            String jsonStr_g = sh.makeServiceCall(urlg);
            String jsonStr_h = sh.makeServiceCall(urlh);
            String jsonStr_i = sh.makeServiceCall(urli);
            String jsonStr_j = sh.makeServiceCall(urlj);
            String jsonStr_k = sh.makeServiceCall(urlk);
            String jsonStr_l = sh.makeServiceCall(urll);

            // List to containe the json response
            List<String> jsonList = new ArrayList<>();
            jsonList.add(jsonStr_a);
            jsonList.add(jsonStr_b);
            jsonList.add(jsonStr_c);
            jsonList.add(jsonStr_d);
            jsonList.add(jsonStr_e);
            jsonList.add(jsonStr_f);
            jsonList.add(jsonStr_g);
            jsonList.add(jsonStr_h);
            jsonList.add(jsonStr_i);
            jsonList.add(jsonStr_j);
            jsonList.add(jsonStr_k);
            jsonList.add(jsonStr_l);


            Log.e(TAG, "Response from url: " + jsonStr_a);
            Log.e(TAG, "Response from url: " + jsonStr_b);
            Log.e(TAG, "Response from url: " + jsonStr_c);
            Log.e(TAG, "Response from url: " + jsonStr_d);
            Log.e(TAG, "Response from url: " + jsonStr_e);
            Log.e(TAG, "Response from url: " + jsonStr_f);
            Log.e(TAG, "Response from url: " + jsonStr_g);
            Log.e(TAG, "Response from url: " + jsonStr_h);
            Log.e(TAG, "Response from url: " + jsonStr_i);
            Log.e(TAG, "Response from url: " + jsonStr_j);
            Log.e(TAG, "Response from url: " + jsonStr_k);
            Log.e(TAG, "Response from url: " + jsonStr_l);

            if (jsonStr_a != null && jsonStr_b != null && jsonStr_c != null && jsonStr_d != null && jsonStr_e != null) {
                try {
                    for(int i = 0; i < jsonList.size(); i++) {

                        JSONArray root = new JSONArray(jsonList.get(i));
                        JSONObject jsonObject = root.getJSONObject(0);

                        JSONArray jsonArray = jsonObject.getJSONArray("pilihan");

                        List<PackageChoice> choices = new ArrayList<>();

                        for(int j = 0; j < jsonArray.length(); j++) {
                            JSONObject p = jsonArray.getJSONObject(j);

                            String nama = p.getString("nama");
                            String deskripsi = p.getString("deskripsi");
                            int harga = p.getInt("harga");
                            String urlImg = p.getString("urlImg");

                            choices.add(new PackageChoice(false, nama, deskripsi, harga, urlImg));
                        }

                        listChoices.add(choices);
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
            Picasso.with(PackageBuffetCActivity.this)
                    .load(String.valueOf(packageData.get("url_img")))
                    .into(iv_package);

            // Set Package Name
            TextView tv_package_name = findViewById(R.id.tv_package_name);
            tv_package_name.setText(String.valueOf(packageData.get("package_name")));

            // Showing Some Text When AsyncTask is done
            TextView tv_note_harga_per_porsi = findViewById(R.id.tv_note_harga_per_porsi);
            tv_note_harga_per_porsi.setVisibility(View.VISIBLE);

            // Set Harga Default
            defaultPrice = (Integer) packageData.get("harga_default");
            String strDefaultPrice = String.format("%,d", defaultPrice);

            pricePorsi = findViewById(R.id.tv_harga_default);
            pricePorsi.setText("Rp " + strDefaultPrice);

            // Set Deskripsi
            TextView tv_deskripsi = findViewById(R.id.tv_deskripsi);
            tv_deskripsi.setText(String.valueOf(packageData.get("deskripsi")));

            listViewA = (ListView) findViewById(R.id.lv_package_a);
            listViewB = (ListView) findViewById(R.id.lv_package_b);
            listViewC = (ListView) findViewById(R.id.lv_package_c);
            listViewD = (ListView) findViewById(R.id.lv_package_d);
            listViewE = (ListView) findViewById(R.id.lv_package_e);
            listViewF = (ListView) findViewById(R.id.lv_package_f);
            listViewG = (ListView) findViewById(R.id.lv_package_g);
            listViewH = (ListView) findViewById(R.id.lv_package_h);
            listViewI = (ListView) findViewById(R.id.lv_package_i);
            listViewJ = (ListView) findViewById(R.id.lv_package_j);
            listViewK = (ListView) findViewById(R.id.lv_package_k);
            listViewL = (ListView) findViewById(R.id.lv_package_l);

            // Getting The Package Choices
            List<String> menu = (List<String>) packageData.get("detail_menu");

            titleA = findViewById(R.id.tv_user_title_a);
            titleB = findViewById(R.id.tv_user_title_b);
            titleC = findViewById(R.id.tv_user_title_c);
            titleD = findViewById(R.id.tv_user_title_d);
            titleE = findViewById(R.id.tv_user_title_e);
            titleF = findViewById(R.id.tv_user_title_f);
            titleG = findViewById(R.id.tv_user_title_g);
            titleH = findViewById(R.id.tv_user_title_h);
            titleI = findViewById(R.id.tv_user_title_i);
            titleJ = findViewById(R.id.tv_user_title_j);
            titleK = findViewById(R.id.tv_user_title_k);
            titleL = findViewById(R.id.tv_user_title_l);

            titleA.setText(menu.get(0));
            titleA.setVisibility(View.VISIBLE); // Set to visible because It is invisible when doing AsyncTask
            titleB.setText(menu.get(1));
            titleC.setText(menu.get(2));
            titleD.setText(menu.get(3));
            titleE.setText(menu.get(4));
            titleF.setText(menu.get(5));
            titleG.setText(menu.get(6));
            titleH.setText(menu.get(7));
            titleI.setText(menu.get(8));
            titleJ.setText(menu.get(9));
            titleK.setText(menu.get(10));
            titleL.setText(menu.get(11));

            // Setting Additional Price To Keep Track The Choices That Made
            additionalPrice = new HashMap<Integer, Integer>();
            additionalPrice.put(0, defaultPrice);
            additionalPrice.put(1, 0);
            additionalPrice.put(2, 0);
            additionalPrice.put(3, 0);
            additionalPrice.put(4, 0);
            additionalPrice.put(5, 0);
            additionalPrice.put(6, 0);
            additionalPrice.put(7, 0);
            additionalPrice.put(8, 0);
            additionalPrice.put(9, 0);
            additionalPrice.put(10, 0);
            additionalPrice.put(11, 0);
            additionalPrice.put(12, 0);

            // Initialize Selected Box
            selectedBoxA = new HashMap<Integer, PackageChoice>();
            selectedBoxB = new HashMap<Integer, PackageChoice>();
            selectedBoxC = new HashMap<Integer, PackageChoice>();
            selectedBoxD = new HashMap<Integer, PackageChoice>();
            selectedBoxE = new HashMap<Integer, PackageChoice>();
            selectedBoxF = new HashMap<Integer, PackageChoice>();
            selectedBoxG = new HashMap<Integer, PackageChoice>();
            selectedBoxH = new HashMap<Integer, PackageChoice>();
            selectedBoxI = new HashMap<Integer, PackageChoice>();
            selectedBoxJ = new HashMap<Integer, PackageChoice>();
            selectedBoxK = new HashMap<Integer, PackageChoice>();
            selectedBoxL = new HashMap<Integer, PackageChoice>();

            // Setting Adapter For Each ListView
            final PackageCheckboxAdapter adapterA = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(0));
            listViewA.setAdapter(adapterA);
            final PackageCheckboxAdapter adapterB = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(1));
            listViewB.setAdapter(adapterB);
            final PackageCheckboxAdapter adapterC = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(2));
            listViewC.setAdapter(adapterC);
            final PackageCheckboxAdapter adapterD = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(3));
            listViewD.setAdapter(adapterD);
            final PackageCheckboxAdapter adapterE = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(4));
            listViewE.setAdapter(adapterE);
            final PackageCheckboxAdapter adapterF = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(5));
            listViewF.setAdapter(adapterF);
            final PackageCheckboxAdapter adapterG = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(6));
            listViewG.setAdapter(adapterG);
            final PackageCheckboxAdapter adapterH = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(7));
            listViewH.setAdapter(adapterH);
            final PackageCheckboxAdapter adapterI = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(8));
            listViewI.setAdapter(adapterI);
            final PackageCheckboxAdapter adapterJ = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(9));
            listViewJ.setAdapter(adapterJ);
            final PackageCheckboxAdapter adapterK = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(10));
            listViewK.setAdapter(adapterK);
            final PackageCheckboxAdapter adapterL = new PackageCheckboxAdapter(PackageBuffetCActivity.this, listChoices.get(11));
            listViewL.setAdapter(adapterL);

            ListUtils.setDynamicHeight(listViewA);
            ListUtils.setDynamicHeight(listViewB);
            ListUtils.setDynamicHeight(listViewC);
            ListUtils.setDynamicHeight(listViewD);
            ListUtils.setDynamicHeight(listViewE);
            ListUtils.setDynamicHeight(listViewF);
            ListUtils.setDynamicHeight(listViewG);
            ListUtils.setDynamicHeight(listViewH);
            ListUtils.setDynamicHeight(listViewI);
            ListUtils.setDynamicHeight(listViewJ);
            ListUtils.setDynamicHeight(listViewK);
            ListUtils.setDynamicHeight(listViewL);

            // HANDLING LISTVIEW NO 1
            listViewA.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(0).get(i);

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxA.remove(i);
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxA.put(i, model);
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxA.values()) {
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(1,  sumP);

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(0).set(i, model);

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterA.updateRecords(listChoices.get(0));

                    return true;
                }
            });

            listViewA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(0).get(i);

                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            // HANDLING LISTVIEW NO 2
            listViewB.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(1).get(i);

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxB.remove(i);
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxB.put(i, model);
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxB.values()) {
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(2,  sumP);

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(1).set(i, model);

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterB.updateRecords(listChoices.get(1));

                    return true;
                }
            });

            listViewB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(1).get(i);
                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            // HANDLING LISTVIEW NO 3
            listViewC.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(2).get(i);    // YANG DIUBAH

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxC.remove(i);                 // YANG DIUBAH
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxC.put(i, model);             // YANG DIUBAH
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxC.values()) {     // YANG DIUBAH
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(3,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(2).set(i, model);           // YANG DIUBAH

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterC.updateRecords(listChoices.get(2));         // YANG DIUBAH

                    return true;
                }
            });

            listViewC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(2).get(i);
                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            // HANDLING LISTVIEW NO 4
            listViewD.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(3).get(i);    // YANG DIUBAH

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxD.remove(i);                 // YANG DIUBAH
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxD.put(i, model);             // YANG DIUBAH
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxD.values()) {     // YANG DIUBAH
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(4,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(3).set(i, model);           // YANG DIUBAH

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterD.updateRecords(listChoices.get(3));         // YANG DIUBAH

                    return true;
                }
            });

            listViewD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(3).get(i);
                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            // HANDLING LISTVIEW NO 5
            listViewE.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(4).get(i);    // YANG DIUBAH

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxE.remove(i);                 // YANG DIUBAH
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxE.put(i, model);             // YANG DIUBAH
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxE.values()) {     // YANG DIUBAH
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(5,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(4).set(i, model);           // YANG DIUBAH

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterE.updateRecords(listChoices.get(4));         // YANG DIUBAH

                    return true;
                }
            });

            listViewE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(4).get(i);
                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            // HANDLING LISTVIEW NO 6
            listViewF.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(5).get(i);    // YANG DIUBAH

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxF.remove(i);                 // YANG DIUBAH
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxF.put(i, model);             // YANG DIUBAH
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxF.values()) {     // YANG DIUBAH
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(6,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(5).set(i, model);           // YANG DIUBAH

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterF.updateRecords(listChoices.get(5));         // YANG DIUBAH

                    return true;
                }
            });

            listViewF.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(5).get(i);
                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            // HANDLING LISTVIEW NO 7
            listViewG.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(6).get(i);    // YANG DIUBAH

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxG.remove(i);                 // YANG DIUBAH
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxG.put(i, model);             // YANG DIUBAH
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxG.values()) {     // YANG DIUBAH
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(7,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(6).set(i, model);           // YANG DIUBAH

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterG.updateRecords(listChoices.get(6));         // YANG DIUBAH

                    return true;
                }
            });

            listViewG.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(6).get(i);
                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            // HANDLING LISTVIEW NO 8
            listViewH.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(7).get(i);    // YANG DIUBAH

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxH.remove(i);                 // YANG DIUBAH
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxH.put(i, model);             // YANG DIUBAH
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxH.values()) {     // YANG DIUBAH
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(8,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(7).set(i, model);           // YANG DIUBAH

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterH.updateRecords(listChoices.get(7));         // YANG DIUBAH

                    return true;
                }
            });

            listViewH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(7).get(i);
                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            // HANDLING LISTVIEW NO 9
            listViewI.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(8).get(i);    // YANG DIUBAH

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxI.remove(i);                 // YANG DIUBAH
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxI.put(i, model);             // YANG DIUBAH
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxI.values()) {     // YANG DIUBAH
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(9,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(8).set(i, model);           // YANG DIUBAH

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterI.updateRecords(listChoices.get(8));         // YANG DIUBAH

                    return true;
                }
            });

            listViewI.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(8).get(i);
                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            // HANDLING LISTVIEW NO 10
            listViewJ.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(9).get(i);    // YANG DIUBAH

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxJ.remove(i);                 // YANG DIUBAH
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxJ.put(i, model);             // YANG DIUBAH
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxJ.values()) {     // YANG DIUBAH
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(10,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(9).set(i, model);           // YANG DIUBAH

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterJ.updateRecords(listChoices.get(9));         // YANG DIUBAH

                    return true;
                }
            });

            listViewJ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(9).get(i);
                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            // HANDLING LISTVIEW NO 11
            listViewK.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(10).get(i);    // YANG DIUBAH

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxK.remove(i);                 // YANG DIUBAH
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxK.put(i, model);             // YANG DIUBAH
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxK.values()) {     // YANG DIUBAH
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(11,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(10).set(i, model);           // YANG DIUBAH

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterK.updateRecords(listChoices.get(10));         // YANG DIUBAH

                    return true;
                }
            });

            listViewK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(10).get(i);
                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            // HANDLING LISTVIEW NO 12
            listViewL.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(11).get(i);    // YANG DIUBAH

                    if (model.isSelected()) {
                        model.setSelected(false);
                        // Remove The Choice in the Hashmap
                        selectedBoxL.remove(i);                 // YANG DIUBAH
                    }
                    else {
                        model.setSelected(true);
                        // Put The Choice in the Hashmap
                        selectedBoxL.put(i, model);             // YANG DIUBAH
                    }

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxL.values()) {     // YANG DIUBAH
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    additionalPrice.put(12,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : additionalPrice.values()) {
                        sum += s;
                    }

                    String strSumPrice = String.format("%,d", sum);
                    pricePorsi.setText("Rp " + strSumPrice);

                    String messageAdd = "Total per Porsi Berubah Menjadi \n Rp " + strSumPrice;

                    Snackbar snackbar = Snackbar.make(scrollView, messageAdd, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    listChoices.get(11).set(i, model);           // YANG DIUBAH

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterL.updateRecords(listChoices.get(11));         // YANG DIUBAH

                    return true;
                }
            });

            listViewL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(11).get(i);
                    Intent intent = new Intent(getApplicationContext(), DetailItemActivity.class);
                    intent.putExtra("NAMA", model.getNama());
                    intent.putExtra("HARGA", model.getHarga());
                    intent.putExtra("DESKRIPSI", model.getDeskripsi());
                    intent.putExtra("URLIMG", model.getUrlImg());

                    startActivity(intent);
                }
            });

            Button btnProcess = findViewById(R.id.btn_process);
            btnProcess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Checking if all true using checkAllRadioButtonSelected method
                    Boolean isAllRadioButtonSelected = checkAllRadioButtonSelected(listChoices);

                    // Handle The Result
                    if(isAllRadioButtonSelected) {
                        // String message = "";

                        // int finalPrice = defaultPrice;

                        collectionSelected.put(titleA.getText().toString(), selectedBoxA);
                        collectionSelected.put(titleB.getText().toString(), selectedBoxB);
                        collectionSelected.put(titleC.getText().toString(), selectedBoxC);
                        collectionSelected.put(titleD.getText().toString(), selectedBoxD);
                        collectionSelected.put(titleE.getText().toString(), selectedBoxE);
                        collectionSelected.put(titleF.getText().toString(), selectedBoxF);
                        collectionSelected.put(titleG.getText().toString(), selectedBoxG);
                        collectionSelected.put(titleH.getText().toString(), selectedBoxH);
                        collectionSelected.put(titleI.getText().toString(), selectedBoxI);
                        collectionSelected.put(titleJ.getText().toString(), selectedBoxJ);
                        collectionSelected.put(titleK.getText().toString(), selectedBoxK);
                        collectionSelected.put(titleL.getText().toString(), selectedBoxL);

                        PackageSelect.setNamaKategori("Buffet");
                        PackageSelect.setNamaPackage(String.valueOf(packageData.get("package_name")));
                        PackageSelect.setDefaultPrice((Integer) packageData.get("harga_default"));
                        PackageSelect.setCollectionSelected(collectionSelected);

                        Intent i = new Intent(getApplicationContext(), PackageDeliveryDetailActivity.class);
                        startActivity(i);


                        /*
                        for (LinkedHashMap.Entry<String, HashMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                            String key = entry.getKey();
                            HashMap<Integer, PackageChoice> value = entry.getValue();

                            message += ( "== " + key + " ==\n");


                            for (PackageChoice p : value.values()) {
                                message += (p.getNama() + " - " + p.getHarga() + "\n");
                                finalPrice += p.getHarga();
                            }
                        }

                        // CATATAN : finalPricenya masih ga cocok
                        message = message + "\n" + "Total Price Per Item : " + String.valueOf(finalPrice);
                        Toast.makeText(PackageBuffetCActivity.this, message, Toast.LENGTH_SHORT).show();
                        */
                    }
                    else {
                        String message = "Semua Menu Harus Dipilih";
                        Snackbar snackbar = Snackbar.make(scrollView, message, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
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

    /*
        Method To Check If All Radio Button is Selected
     */
    public Boolean checkAllRadioButtonSelected(List<List<PackageChoice>> listChoices) {
        List<Boolean> resultSelected = new ArrayList<>();

        for (int i = 0; i < listChoices.size(); i++) {
            Boolean isSelected = false;
            for (int j = 0; j < listChoices.get(i).size(); j++) {
                if(listChoices.get(i).get(j).isSelected()) {
                    isSelected = true;
                }
            }
            resultSelected.add(isSelected);
        }

        Boolean returnValue = true;
        for(boolean b : resultSelected) {
            if(!b) {
                returnValue = false;
            }
        }

        return returnValue;
    }

}