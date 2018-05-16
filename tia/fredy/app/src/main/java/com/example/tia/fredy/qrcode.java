package com.example.tia.fredy;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class qrcode extends AppCompatActivity {
    private ListView listViewA, listViewB;
    private TextView titleA, titleB;
    private int preSelectedIndexA = -1, preSelectedIndexB = -1;
    private String choiceA, choiceB;

    List<List<UserModel>> listChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        getSupportActionBar().setTitle("Presensi QrCode");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff485D8C));

        listViewA = (ListView) findViewById(R.id.lv_listview_a);
        listViewB = (ListView) findViewById(R.id.lv_listview_b);

        titleA = findViewById(R.id.tv_user_title_a);
        titleB = findViewById(R.id.tv_user_title_b);

        titleA.setText("Jabatan");    // Ganti aja sesuai punyamu, warna backgroundnya jg bisa diganti di xmlnya
        titleB.setText("Kehadiran");   // Ganti aja sesuai punyamu, warna backgroundnya jg bisa diganti di xmlnya

        final List<UserModel> choicesA = new ArrayList<>();
        choicesA.add(new UserModel(false, "Karyawan Dapur"));
        choicesA.add(new UserModel(false, "Karyawan Pramusaji"));

        final List<UserModel> choicesB = new ArrayList<>();
        choicesB.add(new UserModel(false, "Tanggal Masuk"));
        choicesB.add(new UserModel(false, "Tanggal Keluar"));

        listChoices = new ArrayList<>();
        listChoices.add(choicesA);
        listChoices.add(choicesB);

        final CustomAdapter adapterA = new CustomAdapter(this, listChoices.get(0));
        listViewA.setAdapter(adapterA);
        final CustomAdapter adapterB = new CustomAdapter(this, listChoices.get(1));
        listViewB.setAdapter(adapterB);

        ListUtils.setDynamicHeight(listViewA);
        ListUtils.setDynamicHeight(listViewB);

        // HANDLING LISTVIEW NO 1
        listViewA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get Model From Item that Selected
                UserModel model = listChoices.get(0).get(i);

                // Get The Name
                choiceA = listChoices.get(0).get(i).getUserName();

                // Change the isSelected state
                model.setSelected(true);

                listChoices.get(0).set(i, model);

                if (preSelectedIndexA > -1 && preSelectedIndexA != i){

                    UserModel preRecord = listChoices.get(0).get(preSelectedIndexA);
                    preRecord.setSelected(false);

                    listChoices.get(0).set(preSelectedIndexA, preRecord);

                }

                preSelectedIndexA = i;

                //Now update adapter so we are going to make a update method in adapter
                //Now declare adapter final to access in inner method
                adapterA.updateRecords(listChoices.get(0));
            }
        });

        // HANDLING LISTVIEW NO 2
        listViewB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get Model From Item that Selected
                UserModel model = listChoices.get(1).get(i);

                // Get The Name
                choiceB = listChoices.get(1).get(i).getUserName();

                // Change the isSelected state
                model.setSelected(true);

                listChoices.get(1).set(i, model);

                if (preSelectedIndexB > -1 && preSelectedIndexB != i){

                    UserModel preRecord = listChoices.get(1).get(preSelectedIndexB);
                    preRecord.setSelected(false);

                    listChoices.get(1).set(preSelectedIndexB, preRecord);
                }

                preSelectedIndexB = i;

                //Now update adapter so we are going to make a update method in adapter
                //Now declare adapter final to access in inner method
                adapterB.updateRecords(listChoices.get(1));

            }
        });

        // Menghandle Button Lanjut
        Button btn_lanjut = findViewById(R.id.btn_lanjut);
        btn_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking if all true using checkAllRadioButtonSelected method
                Boolean isAllRadioButtonSelected = checkAllRadioButtonSelected(listChoices);

                // Handle The Result
                if(isAllRadioButtonSelected) {      // Jika Semuanya Sudah Dipilih

                    if(choiceA == "Karyawan Dapur" && choiceB == "Tanggal Masuk") {
                        // Lakukan Sesuatu, Mungkin Pindah Ke Activity Lain

                    } else if(choiceA == "Karyawan Dapur" && choiceB == "Tanggal Keluar") {
                        // Lakukan Sesuatu, Mungkin Pindah Ke Activity Lain
                    } else if(choiceA == "Karyawan Pramusaji" && choiceB == "Tanggal Masuk") {
                        // Lakukan Sesuatu, Mungkin Pindah Ke Activity Lain
                    } else if(choiceA == "Karyawan Dapur" && choiceB == "Tanggal Keluar") {
                        // Lakukan Sesuatu, Mungkin Pindah Ke Activity Lain
                    }

                } else {                           // Jika Ada Yang Belum Dipilih
                    String message = "Silahkan Pilih Masing-Masing Satu";
                    Toast.makeText(qrcode.this, message, Toast.LENGTH_SHORT).show();
                }

            }
        });
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
    public Boolean checkAllRadioButtonSelected(List<List<UserModel>> listChoices) {
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
