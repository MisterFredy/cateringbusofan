package com.akhmadfaizin.cateringbusofan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class UbahProfileActivity extends AppCompatActivity {
    private RelativeLayout rl_ubahprofile;
    private EditText et_fullname,
                    et_phone,
                    et_alamat;
    private RadioGroup rg_gender;
    private ImageButton btn_submit;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profile);

        getSupportActionBar().setTitle("Ubah Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        rl_ubahprofile = findViewById(R.id.rl_ubahprofile);

        et_fullname = findViewById(R.id.et_fullname);
        et_fullname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    String fullname = et_fullname.getText().toString().trim();
                    fullname = fullname.replaceAll("\\s","");

                    if(fullname.length() > 0) {
                        if(fullname.length() < 3) {
                            Snackbar snackbar = Snackbar.make(rl_ubahprofile, "Panjang Nama Minimal 3 Huruf\nTidak Termasuk Spasi", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            et_fullname.setText("");
                        }
                    }
                }
            }
        });

        et_fullname.setText(sp.getString("fullname", null));

        et_fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_fullname.getText().toString().matches("^ ") )
                {
                    // Not allowed
                    Snackbar snackbar = Snackbar.make(rl_ubahprofile, "Tidak Boleh Awalan Spasi", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    et_fullname.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_phone = findViewById(R.id.et_phone);
        et_phone.setText(sp.getString("no_hp", null));
        et_phone.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(count > 0) {
                    if(!et_phone.getText().toString().startsWith("0")) {
                        // Not allowed
                        Snackbar snackbar = Snackbar.make(rl_ubahprofile, "Harus Didahului 0", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        et_phone.setText("");
                    }
                }

            }
            @Override
            public void afterTextChanged(Editable arg0) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        rg_gender = findViewById(R.id.rg_gender);
        gender = sp.getString("jenis_kelamin", null);

        if(gender.equals("Laki-Laki")) {
            rg_gender.check(R.id.rb_laki);
        } else {
            rg_gender.check(R.id.rb_perempuan);
        }

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch(checkedId) {
                    case R.id.rb_laki:
                        gender = "Laki-Laki";
                        break;
                    case R.id.rb_perempuan:
                        gender = "Perempuan";
                        break;
                }
            }
        });

        et_alamat = findViewById(R.id.et_alamat);
        et_alamat.setText(sp.getString("alamat", null));

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et_phone.getText().toString()) || TextUtils.isEmpty(et_alamat.getText().toString())) {
                    Snackbar snackbar = Snackbar.make(rl_ubahprofile, "Semua Harus Diisi", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if(et_fullname.getText().toString().trim().replaceAll("\\s","").length() < 3) {
                    Snackbar snackbar = Snackbar.make(rl_ubahprofile, "Panjang Nama Minimal 3 Huruf\nTidak Termasuk Spasi", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    et_fullname.setText("");
                } else {
                    String fullname = et_fullname.getText().toString().trim();
                    String phone = et_phone.getText().toString();
                    String jenis_kelamin = gender;
                    String alamat = et_alamat.getText().toString();

                    Intent i = new Intent(getApplicationContext(), PutUbahProfileActivity.class);
                    i.putExtra("fullname", fullname);
                    i.putExtra("phone", phone);
                    i.putExtra("jenis_kelamin", jenis_kelamin);
                    i.putExtra("alamat", alamat);
                    startActivity(i);
                }
            }
        });

    }
}