package com.akhmadfaizin.cateringbusofan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailMenuCardActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_qty;
    String menuId, menuName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu_card);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#792525"));
        }

        // Setting Action Bar Title
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            menuId = extras.getString("menuId");

            menuName = "Nama Makanan ke-" + menuId;
        }

        getSupportActionBar().setTitle(menuName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // CATATAN : INI BELUM SAMA SETTING JUDUL MAKANANNYA LHO, BARU ACTION BAR TITLE DOANG


        LinearLayout containerLayout = (LinearLayout) findViewById(R.id.container);

        for (int i = 0; i < 10; i++) {
            TextView dynaText = new TextView(this);

            dynaText.setGravity(Gravity.LEFT);
            dynaText.setText(i + ". Makanan Ke-" + i);
            dynaText.setTextSize(18);
            dynaText.setTextColor(Color.parseColor("#616161"));
            dynaText.setTag("" + i);
            dynaText.setOnClickListener(this);

            containerLayout.addView(dynaText);
        }

        et_qty = findViewById(R.id.et_qty);
        et_qty.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (et_qty.getText().toString().matches("^0") )
                {
                    // Not allowed
                    Toast.makeText(DetailMenuCardActivity.this, "Tidak Boleh Awalan 0", Toast.LENGTH_SHORT).show();
                    et_qty.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    public void onClick(View v) {
        Intent i = new Intent(this, DetailItemActivity.class);
        i.putExtra("itemId", v.getTag().toString());
        startActivity(i);
    }

    public void doQty(View view) {
        et_qty = findViewById(R.id.et_qty);

        Toast.makeText(this, "Quantity : " + et_qty.getText().toString(), Toast.LENGTH_SHORT).show();
    }

}
