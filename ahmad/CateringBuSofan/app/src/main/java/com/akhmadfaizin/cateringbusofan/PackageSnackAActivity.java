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
import java.util.TreeMap;

public class PackageSnackAActivity extends AppCompatActivity {

    private String TAG = PackageSnackAActivity.class.getSimpleName();
    List<List<PackageChoice>> listChoices;  // Store List Of Packages Choices available
    private ProgressDialog pDialog;

    private ListView listViewA,
            listViewB ,
            listViewC,
            listViewD;
    private TextView titleA,
            titleB,
            titleC,
            titleD,
            pricePorsi;
    private TreeMap<Integer, PackageChoice> selectedBoxA,
            selectedBoxB,
            selectedBoxC,
            selectedBoxD;
    private int preSelectedIndexA = -1,
            preSelectedIndexB = -1,
            preSelectedIndexC = -1,
            preSelectedIndexD = -1,
            defaultPrice;
    private LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = new LinkedHashMap<String, TreeMap<Integer, PackageChoice>>();
    private HashMap<Integer, Integer> finalPrice; // To keep update the new price per porsi
    private HashMap<String, Object> packageData;    // Get One Data just For That Package

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_snack_a);

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
            pDialog = new ProgressDialog(PackageSnackAActivity.this);
            pDialog.setMessage("Tunggu Sebentar...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // URL to get contacts JSON Snack A
            String url = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/menu?q={%22category%22:%22Snack%20Box%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";

            // Making a request to url and getting response
            String jsonStr = sh.goGetApi(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    JSONArray root = new JSONArray(jsonStr);
                    JSONObject jsonObject = root.getJSONObject(0);
                    JSONArray jsonArray = jsonObject.getJSONArray("menu");
                    // The Index Number 0 is for Snack A
                    JSONObject jsonObjectPackage = jsonArray.getJSONObject(0);

                    String package_name = jsonObjectPackage.getString("nama_menu");
                    int harga_default = jsonObjectPackage.getInt("harga_default");
                    String deskripsi = jsonObjectPackage.getString("deskripsi");
                    String url_img = jsonObjectPackage.getString("url_img");

                    JSONArray jsonMenuArray = jsonObjectPackage.getJSONArray("detail_menu");
                    List<String> detail_menu = new ArrayList<>();
                    for (int i = 0; i < jsonMenuArray.length(); i++) {
                        detail_menu.add(jsonMenuArray.getString(i));
                    }

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

            // Getting The Choice Package JSON
            String urla = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Snack%20Manis%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urlb = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Snack%20Gurih%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urlc = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Kletikan%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";
            String urld = "https://api.mlab.com/api/1/databases/cateringbusofan/collections/item?q={%22namaItem%22:%22Air%20Mineral%22}&apiKey=x12MbBjL_GcDU4cpE6VDnZ-Ghj3qvvMI";

            // Making a request to url and getting response
            String jsonStr_a = sh.goGetApi(urla);
            String jsonStr_b = sh.goGetApi(urlb);
            String jsonStr_c = sh.goGetApi(urlc);
            String jsonStr_d = sh.goGetApi(urld);

            // List to containe the json response
            List<String> jsonList = new ArrayList<>();
            jsonList.add(jsonStr_a);
            jsonList.add(jsonStr_b);
            jsonList.add(jsonStr_c);
            jsonList.add(jsonStr_d);

            Log.e(TAG, "Response from url: " + jsonStr_a);
            Log.e(TAG, "Response from url: " + jsonStr_b);
            Log.e(TAG, "Response from url: " + jsonStr_c);
            Log.e(TAG, "Response from url: " + jsonStr_d);

            if (jsonStr_a != null && jsonStr_b != null && jsonStr_c != null && jsonStr_d != null) {
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
            Picasso.with(PackageSnackAActivity.this)
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

            // Getting The Package Choices
            List<String> menu = (List<String>) packageData.get("detail_menu");

            titleA = findViewById(R.id.tv_user_title_a);
            titleB = findViewById(R.id.tv_user_title_b);
            titleC = findViewById(R.id.tv_user_title_c);
            titleD = findViewById(R.id.tv_user_title_d);

            titleA.setText(menu.get(0));
            titleA.setVisibility(View.VISIBLE); // Set to visible because It is invisible when doing AsyncTask
            titleB.setText(menu.get(1));
            titleC.setText(menu.get(2));
            titleD.setText(menu.get(3));

            // Setting Additional Price To Keep Track The Choices That Made
            finalPrice = new HashMap<Integer, Integer>();
            finalPrice.put(0, defaultPrice);
            finalPrice.put(1, 0);
            finalPrice.put(2, 0);
            finalPrice.put(3, 0);
            finalPrice.put(4, 0);

            // Initialize Selected Box
            selectedBoxA = new TreeMap<Integer, PackageChoice>();
            selectedBoxB = new TreeMap<Integer, PackageChoice>();
            selectedBoxC = new TreeMap<Integer, PackageChoice>();
            selectedBoxD = new TreeMap<Integer, PackageChoice>();

            // Setting Adapter For Each ListView
            final PackageRadioAdapter adapterA = new PackageRadioAdapter(PackageSnackAActivity.this, listChoices.get(0));
            listViewA.setAdapter(adapterA);
            final PackageRadioAdapter adapterB = new PackageRadioAdapter(PackageSnackAActivity.this, listChoices.get(1));
            listViewB.setAdapter(adapterB);
            final PackageRadioAdapter adapterC = new PackageRadioAdapter(PackageSnackAActivity.this, listChoices.get(2));
            listViewC.setAdapter(adapterC);
            final PackageRadioAdapter adapterD = new PackageRadioAdapter(PackageSnackAActivity.this, listChoices.get(3));
            listViewD.setAdapter(adapterD);

            ListUtils.setDynamicHeight(listViewA);
            ListUtils.setDynamicHeight(listViewB);
            ListUtils.setDynamicHeight(listViewC);
            ListUtils.setDynamicHeight(listViewD);

            // HANDLING LISTVIEW NO 1
            listViewA.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Get Model From Item that Selected
                    PackageChoice model = listChoices.get(0).get(i);

                    selectedBoxA.clear();
                    selectedBoxA.put(i, model);

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

                    // Change the isSelected state
                    model.setSelected(true);

                    listChoices.get(0).set(i, model);

                    if (preSelectedIndexA > -1 && preSelectedIndexA != i){

                        PackageChoice preRecord = listChoices.get(0).get(preSelectedIndexA);
                        preRecord.setSelected(false);

                        listChoices.get(0).set(preSelectedIndexA, preRecord);
                    }

                    preSelectedIndexA = i;

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

                    selectedBoxB.clear();
                    selectedBoxB.put(i, model);

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

                    // Change the isSelected state
                    model.setSelected(true);

                    listChoices.get(1).set(i, model);

                    if (preSelectedIndexB > -1 && preSelectedIndexB != i){

                        PackageChoice preRecord = listChoices.get(1).get(preSelectedIndexB);
                        preRecord.setSelected(false);

                        listChoices.get(1).set(preSelectedIndexB, preRecord);
                    }

                    preSelectedIndexB = i;

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
                    PackageChoice model = listChoices.get(2).get(i);

                    selectedBoxC.clear();
                    selectedBoxC.put(i, model);

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxC.values()) {
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    finalPrice.put(3,  sumP);

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

                    // Change the isSelected state
                    model.setSelected(true);

                    listChoices.get(2).set(i, model);

                    if (preSelectedIndexC > -1 && preSelectedIndexC != i){

                        PackageChoice preRecord = listChoices.get(2).get(preSelectedIndexC);
                        preRecord.setSelected(false);

                        listChoices.get(2).set(preSelectedIndexC, preRecord);
                    }

                    preSelectedIndexC = i;

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterC.updateRecords(listChoices.get(2));

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
                    PackageChoice model = listChoices.get(3).get(i);

                    selectedBoxD.clear();
                    selectedBoxD.put(i, model);

                    // Get The Sum Of SelectedBox Hashmap
                    int sumP = 0;
                    for (PackageChoice p : selectedBoxD.values()) {
                        sumP += p.getHarga();
                    }

                    // Update The Additional HashMap
                    finalPrice.put(4,  sumP);

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

                    // Change the isSelected state
                    model.setSelected(true);

                    listChoices.get(3).set(i, model);

                    if (preSelectedIndexD > -1 && preSelectedIndexD != i){

                        PackageChoice preRecord = listChoices.get(3).get(preSelectedIndexD);
                        preRecord.setSelected(false);

                        listChoices.get(3).set(preSelectedIndexD, preRecord);
                    }

                    preSelectedIndexD = i;

                    //Now update adapter so we are going to make a update method in adapter
                    //Now declare adapter final to access in inner method
                    adapterD.updateRecords(listChoices.get(3));

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

                        int finalSum = 0;
                        for (int s : finalPrice.values()) {
                            finalSum += s;
                        }

                        PackageSnackASelect.setCollectionSelected(collectionSelected);

                        Intent i = new Intent(getApplicationContext(), PackageInputDetailPengirimanActivity.class);
                        i.putExtra("NamaKategori", "Snack Box");
                        i.putExtra("NamaPackage", String.valueOf(packageData.get("package_name")));
                        i.putExtra("DefaultPrice", (Integer) packageData.get("harga_default"));
                        i.putExtra("FinalPrice", finalSum);
                        startActivity(i);

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