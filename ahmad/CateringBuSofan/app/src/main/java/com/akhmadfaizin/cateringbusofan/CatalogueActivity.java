package com.akhmadfaizin.cateringbusofan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CatalogueActivity extends AppCompatActivity {
    private static final Set<Integer> values = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 6, 7, 9, 10, 11, 13));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#792525"));
        }

        getSupportActionBar().setTitle("Catalogue");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ScrollView sv_catalogue = findViewById(R.id.sv_catalogue);
        sv_catalogue.smoothScrollTo(0, 0);

        final NonScrollListView non_scroll_list = (NonScrollListView) findViewById(R.id.lv_nonscroll_catalogue);

        final ArrayList<Object> list = new ArrayList<>();
        list.add(new String("Nasi Box"));
        list.add(new Catalogue("Nasi Box A", R.drawable.sate));
        list.add(new Catalogue("Nasi Box B", R.drawable.sate));
        list.add(new Catalogue("Nasi Box C", R.drawable.sate));
        list.add(new Catalogue("Nasi Box D", R.drawable.sate));
        list.add(new String("Snack Box"));
        list.add(new Catalogue("Snack Box A", R.drawable.sate));
        list.add(new Catalogue("Snack Box B", R.drawable.sate));
        list.add(new String("Buffet"));
        list.add(new Catalogue("Buffet A", R.drawable.sate));
        list.add(new Catalogue("Buffet B", R.drawable.sate));
        list.add(new Catalogue("Buffet C", R.drawable.sate));
        list.add(new String("Pondokan"));
        list.add(new Catalogue("Pondokan", R.drawable.sate));

        non_scroll_list.setAdapter(new CatalogueAdapter(this, list));

        non_scroll_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(values.contains(position)) {
                    switch(position) {
                        // 1, 2, 3, 4, 6, 7, 9, 10, 11, 13

                        case 1:
                            Intent intentNasiA = new Intent(getBaseContext(), PackageNasiAActivity.class);
                            startActivity(intentNasiA);
                            break;
                        case 2:
                            Intent intentNasiB = new Intent(getBaseContext(), PackageNasiBActivity.class);
                            startActivity(intentNasiB);
                            break;
                        case 3:
                            Intent intentNasiC = new Intent(getBaseContext(), PackageNasiCActivity.class);
                            startActivity(intentNasiC);
                            break;
                        case 4:
                            Intent intentNasiD = new Intent(getBaseContext(), PackageNasiDActivity.class);
                            startActivity(intentNasiD);
                            break;
                        case 6:
                            Intent intentSnackA = new Intent(getBaseContext(), PackageSnackAActivity.class);
                            startActivity(intentSnackA);
                            break;
                        case 7:
                            Intent intentSnackB = new Intent(getBaseContext(), PackageSnackBActivity.class);
                            startActivity(intentSnackB);
                            break;
                        case 9:
                            Intent intentBuffetA = new Intent(getBaseContext(), PackageBuffetAActivity.class);
                            startActivity(intentBuffetA);
                            break;
                        case 10:
                            Intent intentBuffetB = new Intent(getBaseContext(), PackageBuffetBActivity.class);
                            startActivity(intentBuffetB);
                            break;
                        case 11:
                            Intent intentBuffetC = new Intent(getBaseContext(), PackageBuffetCActivity.class);
                            startActivity(intentBuffetC);
                            break;
                        case 13:
                            Intent intentPondokan = new Intent(getBaseContext(), PackagePondokanActivity.class);
                            startActivity(intentPondokan);
                            break;
                    }


                }
            }
        });
    }
}