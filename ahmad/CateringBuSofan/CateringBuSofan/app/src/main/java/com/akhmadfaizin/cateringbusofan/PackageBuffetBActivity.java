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
import java.util.Map;
import java.util.TreeMap;

public class PackageBuffetBActivity extends AppCompatActivity {

    private String TAG = PackageBuffetBActivity.class.getSimpleName();
    List<List<PackageChoice>> listChoices;  // Store List Of Packages Choices available
    private ProgressDialog pDialog;
    private String NECESSITY;

    private ListView listViewA,
            listViewB ,
            listViewC,
            listViewD,
            listViewE,
            listViewF,
            listViewG,
            listViewH,
            listViewI,
            listViewJ;
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
            pricePorsi;
    private int defaultPrice;
    private TreeMap<Integer, PackageChoice> selectedBoxA,
            selectedBoxB,
            selectedBoxC,
            selectedBoxD,
            selectedBoxE,
            selectedBoxF,
            selectedBoxG,
            selectedBoxH,
            selectedBoxI,
            selectedBoxJ;
    private LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = new LinkedHashMap<String, TreeMap<Integer, PackageChoice>>();
    private HashMap<Integer, Integer> finalPrice; // To keep update the new price per porsi
    private HashMap<String, Object> packageData;    // Get One Data just For That Package

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_buffet_b);

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
            pDialog = new ProgressDialog(PackageBuffetBActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // URL to get contacts JSON Snack B
            String url = getString(R.string.base_url) + "menu?category=Buffet";

            // Making a request to url and getting response
            String jsonStr = sh.goGetApi(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    JSONArray root = new JSONArray(jsonStr);
                    JSONObject jsonObject = root.getJSONObject(0);
                    JSONArray jsonArray = jsonObject.getJSONArray("menu");
                    // The Index Number 1 is for Buffet B
                    JSONObject jsonObjectPackage = jsonArray.getJSONObject(1);

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
            Picasso.with(PackageBuffetBActivity.this)
                    .load(String.valueOf(packageData.get("url_img")))
                    .fetch();

            // Getting The Choice Package JSON
            String urla = getString(R.string.base_url) + "item?namaItem=Nasi%20Putih";
            String urlb = getString(R.string.base_url) + "item?namaItem=Aneka%20Soup";
            String urlc = getString(R.string.base_url) + "item?namaItem=Masakan%20Ayam";
            String urld = getString(R.string.base_url) + "item?namaItem=Masakan%20Daging";
            String urle = getString(R.string.base_url) + "item?namaItem=Masakan%20Seafood";
            String urlf = getString(R.string.base_url) + "item?namaItem=Masakan%20Sayuran";
            String urlg = getString(R.string.base_url) + "item?namaItem=Kerupuk";
            String urlh = getString(R.string.base_url) + "item?namaItem=Buah%20Iris";
            String urli = getString(R.string.base_url) + "item?namaItem=Air%20Mineral,%20Teh%20dan%20Es%20Serek";
            String urlj = getString(R.string.base_url) + "item?namaItem=Es%20Cream%20dan%20Pudding";

            // Making a request to url and getting response
            String jsonStr_a = sh.goGetApi(urla);
            String jsonStr_b = sh.goGetApi(urlb);
            String jsonStr_c = sh.goGetApi(urlc);
            String jsonStr_d = sh.goGetApi(urld);
            String jsonStr_e = sh.goGetApi(urle);
            String jsonStr_f = sh.goGetApi(urlf);
            String jsonStr_g = sh.goGetApi(urlg);
            String jsonStr_h = sh.goGetApi(urlh);
            String jsonStr_i = sh.goGetApi(urli);
            String jsonStr_j = sh.goGetApi(urlj);

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

            if (jsonStr_a != null && jsonStr_b != null && jsonStr_c != null && jsonStr_d != null && jsonStr_e != null && jsonStr_f != null && jsonStr_g != null && jsonStr_h != null && jsonStr_i != null && jsonStr_j != null) {
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
            String urlfoto = getString(R.string.base_url) + "makanan/" + String.valueOf(packageData.get("url_img"));
            Picasso.with(PackageBuffetBActivity.this)
                    .load(urlfoto)
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

            // Setting Additional Price To Keep Track The Choices That Made
            finalPrice = new HashMap<Integer, Integer>();
            finalPrice.put(0, defaultPrice);
            finalPrice.put(1, 0);
            finalPrice.put(2, 0);
            finalPrice.put(3, 0);
            finalPrice.put(4, 0);
            finalPrice.put(5, 0);
            finalPrice.put(6, 0);
            finalPrice.put(7, 0);
            finalPrice.put(8, 0);
            finalPrice.put(9, 0);
            finalPrice.put(10, 0);

            // Initialize Selected Box
            selectedBoxA = new TreeMap<Integer, PackageChoice>();
            selectedBoxB = new TreeMap<Integer, PackageChoice>();
            selectedBoxC = new TreeMap<Integer, PackageChoice>();
            selectedBoxD = new TreeMap<Integer, PackageChoice>();
            selectedBoxE = new TreeMap<Integer, PackageChoice>();
            selectedBoxF = new TreeMap<Integer, PackageChoice>();
            selectedBoxG = new TreeMap<Integer, PackageChoice>();
            selectedBoxH = new TreeMap<Integer, PackageChoice>();
            selectedBoxI = new TreeMap<Integer, PackageChoice>();
            selectedBoxJ = new TreeMap<Integer, PackageChoice>();

            if(NECESSITY.equals("EDIT")) {
                // Setting The SelectedBox
                selectedBoxA = PackageBuffetBSelect.getCollectionSelected().get(menu.get(0));
                selectedBoxB = PackageBuffetBSelect.getCollectionSelected().get(menu.get(1));
                selectedBoxC = PackageBuffetBSelect.getCollectionSelected().get(menu.get(2));
                selectedBoxD = PackageBuffetBSelect.getCollectionSelected().get(menu.get(3));
                selectedBoxE = PackageBuffetBSelect.getCollectionSelected().get(menu.get(4));
                selectedBoxF = PackageBuffetBSelect.getCollectionSelected().get(menu.get(5));
                selectedBoxG = PackageBuffetBSelect.getCollectionSelected().get(menu.get(6));
                selectedBoxH = PackageBuffetBSelect.getCollectionSelected().get(menu.get(7));
                selectedBoxI = PackageBuffetBSelect.getCollectionSelected().get(menu.get(8));
                selectedBoxJ = PackageBuffetBSelect.getCollectionSelected().get(menu.get(9));

                /*
                 * START OF Get The Sum Of SelectedBox Hashmap
                 */
                int sumA = 0;
                for (PackageChoice p : selectedBoxA.values()) {
                    sumA += p.getHarga();
                }
                finalPrice.put(1,  sumA);

                int sumB = 0;
                for (PackageChoice p : selectedBoxB.values()) {
                    sumB += p.getHarga();
                }
                finalPrice.put(2,  sumB);

                int sumC = 0;
                for (PackageChoice p : selectedBoxC.values()) {
                    sumC += p.getHarga();
                }
                finalPrice.put(3,  sumC);

                int sumD = 0;
                for (PackageChoice p : selectedBoxD.values()) {
                    sumD += p.getHarga();
                }
                finalPrice.put(4,  sumD);

                int sumE = 0;
                for (PackageChoice p : selectedBoxE.values()) {
                    sumE += p.getHarga();
                }
                finalPrice.put(5,  sumE);

                int sumF = 0;
                for (PackageChoice p : selectedBoxF.values()) {
                    sumF += p.getHarga();
                }
                finalPrice.put(6,  sumF);

                int sumG = 0;
                for (PackageChoice p : selectedBoxG.values()) {
                    sumG += p.getHarga();
                }
                finalPrice.put(7,  sumG);

                int sumH = 0;
                for (PackageChoice p : selectedBoxH.values()) {
                    sumH += p.getHarga();
                }
                finalPrice.put(8,  sumH);

                int sumI = 0;
                for (PackageChoice p : selectedBoxI.values()) {
                    sumI += p.getHarga();
                }
                finalPrice.put(9,  sumI);

                int sumJ = 0;
                for (PackageChoice p : selectedBoxJ.values()) {
                    sumJ += p.getHarga();
                }
                finalPrice.put(10,  sumJ);
                /*
                 * END OF Get The Sum Of SelectedBox Hashmap
                 */

                // Get The Updated Sums
                int sumAll = 0;
                for (int s : finalPrice.values()) {
                    sumAll += s;
                }

                String strSumPrice = String.format("%,d", sumAll);
                pricePorsi.setText("Rp " + strSumPrice);

                // UBAH listChoices sesuai dengan pilihan di TreeMap
                for(int i = 0; i < listChoices.size(); i++) {
                    for(int j = 0; j < listChoices.get(i).size(); j++) {
                        // Get Value of a TreeMap based on Key that was saved on menu List
                        TreeMap<Integer, PackageChoice> selectedBox = PackageBuffetBSelect.getCollectionSelected().get(menu.get(i));

                        for(Map.Entry<Integer, PackageChoice> entry : selectedBox.entrySet()) {
                            String listNama = listChoices.get(i).get(j).getNama();
                            String mapNama = entry.getValue().getNama();
                            if(listNama.equals(mapNama)) {
                                listChoices.get(i).get(j).setSelected(true);
                            }
                        }
                    }
                }
            }

            // Setting Adapter For Each ListView
            final PackageCheckboxAdapter adapterA = new PackageCheckboxAdapter(PackageBuffetBActivity.this, listChoices.get(0));
            listViewA.setAdapter(adapterA);
            final PackageCheckboxAdapter adapterB = new PackageCheckboxAdapter(PackageBuffetBActivity.this, listChoices.get(1));
            listViewB.setAdapter(adapterB);
            final PackageCheckboxAdapter adapterC = new PackageCheckboxAdapter(PackageBuffetBActivity.this, listChoices.get(2));
            listViewC.setAdapter(adapterC);
            final PackageCheckboxAdapter adapterD = new PackageCheckboxAdapter(PackageBuffetBActivity.this, listChoices.get(3));
            listViewD.setAdapter(adapterD);
            final PackageCheckboxAdapter adapterE = new PackageCheckboxAdapter(PackageBuffetBActivity.this, listChoices.get(4));
            listViewE.setAdapter(adapterE);
            final PackageCheckboxAdapter adapterF = new PackageCheckboxAdapter(PackageBuffetBActivity.this, listChoices.get(5));
            listViewF.setAdapter(adapterF);
            final PackageCheckboxAdapter adapterG = new PackageCheckboxAdapter(PackageBuffetBActivity.this, listChoices.get(6));
            listViewG.setAdapter(adapterG);
            final PackageCheckboxAdapter adapterH = new PackageCheckboxAdapter(PackageBuffetBActivity.this, listChoices.get(7));
            listViewH.setAdapter(adapterH);
            final PackageCheckboxAdapter adapterI = new PackageCheckboxAdapter(PackageBuffetBActivity.this, listChoices.get(8));
            listViewI.setAdapter(adapterI);
            final PackageCheckboxAdapter adapterJ = new PackageCheckboxAdapter(PackageBuffetBActivity.this, listChoices.get(9));
            listViewJ.setAdapter(adapterJ);

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
                    finalPrice.put(1,  sumP);

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : finalPrice.values()) {
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
                    String urlfoto = getString(R.string.base_url) + "makanan/" + model.getUrlImg();
                    intent.putExtra("URLIMG", urlfoto);

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
                    finalPrice.put(2,  sumP);

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : finalPrice.values()) {
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
                    String urlfoto = getString(R.string.base_url) + "makanan/" + model.getUrlImg();
                    intent.putExtra("URLIMG", urlfoto);

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
                    finalPrice.put(3,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : finalPrice.values()) {
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
                    String urlfoto = getString(R.string.base_url) + "makanan/" + model.getUrlImg();
                    intent.putExtra("URLIMG", urlfoto);

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
                    finalPrice.put(4,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : finalPrice.values()) {
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
                    String urlfoto = getString(R.string.base_url) + "makanan/" + model.getUrlImg();
                    intent.putExtra("URLIMG", urlfoto);

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
                    finalPrice.put(5,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : finalPrice.values()) {
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
                    String urlfoto = getString(R.string.base_url) + "makanan/" + model.getUrlImg();
                    intent.putExtra("URLIMG", urlfoto);

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
                    finalPrice.put(6,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : finalPrice.values()) {
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
                    String urlfoto = getString(R.string.base_url) + "makanan/" + model.getUrlImg();
                    intent.putExtra("URLIMG", urlfoto);

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
                    finalPrice.put(7,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : finalPrice.values()) {
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
                    String urlfoto = getString(R.string.base_url) + "makanan/" + model.getUrlImg();
                    intent.putExtra("URLIMG", urlfoto);

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
                    finalPrice.put(8,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : finalPrice.values()) {
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
                    String urlfoto = getString(R.string.base_url) + "makanan/" + model.getUrlImg();
                    intent.putExtra("URLIMG", urlfoto);

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
                    finalPrice.put(9,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : finalPrice.values()) {
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
                    String urlfoto = getString(R.string.base_url) + "makanan/" + model.getUrlImg();
                    intent.putExtra("URLIMG", urlfoto);

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
                    finalPrice.put(10,  sumP);          // YANG DIUBAH

                    // Get The Updated Sums
                    int sum = 0;
                    for (int s : finalPrice.values()) {
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
                    String urlfoto = getString(R.string.base_url) + "makanan/" + model.getUrlImg();
                    intent.putExtra("URLIMG", urlfoto);

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

                        int finalSum = 0;
                        for (int s : finalPrice.values()) {
                            finalSum += s;
                        }

                        PackageBuffetBSelect.setTemporaryCollectionSelected(collectionSelected);

                        Intent i = new Intent(getApplicationContext(), PackageInputDetailPengirimanActivity.class);
                        i.putExtra("NamaKategori", "Buffet");
                        i.putExtra("NamaPackage", String.valueOf(packageData.get("package_name")));
                        i.putExtra("DefaultPrice", (Integer) packageData.get("harga_default"));
                        i.putExtra("FinalPrice", finalSum);
                        i.putExtra("NECESSITY", getIntent().getStringExtra("NECESSITY"));
                        startActivity(i);

                    }
                    else {
                        String message = "Semua Menu Harus Dipilih Minimal Satu";
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