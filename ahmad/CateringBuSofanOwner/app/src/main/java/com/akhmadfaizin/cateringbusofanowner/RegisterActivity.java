package com.akhmadfaizin.cateringbusofanowner;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {
    private LinearLayout ll_register;
    private EditText et_username,
                    et_password,
                    et_rpassword,
                    et_phone,
                    et_alamat;
    private RadioGroup rg_gender;
    private ImageButton btn_daftar;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ll_register = findViewById(R.id.ll_register);

        //For Hiding ActionBar
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();

        et_username = findViewById(R.id.et_username);
        et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    String username = et_username.getText().toString().trim();

                    if(username.length() > 0) {
                        if(username.length() < 5) {
                            Snackbar snackbar = Snackbar.make(ll_register, "Panjang Username Minimal 5 Huruf", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            et_username.setText("");
                        }
                    }
                }
            }
        });

        et_password = findViewById(R.id.et_password);
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    String password = et_password.getText().toString().trim();

                    if(password.length() > 0) {
                        if(password.length() < 5) {
                            Snackbar snackbar = Snackbar.make(ll_register, "Panjang Password Minimal 5 Huruf", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            et_password.setText("");
                        }
                    }
                }
            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String result = editable.toString().replaceAll(" ", "");
                if (!editable.toString().equals(result)) {
                    et_password.setText(result);
                    et_password.setSelection(result.length());
                    // alert the user
                    Snackbar snackbar = Snackbar.make(ll_register, "Password Tidak Boleh Mengandung Spasi", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        et_rpassword = findViewById(R.id.et_rpassword);
        et_rpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    String password = et_password.getText().toString().trim();
                    String rpassword = et_rpassword.getText().toString().trim();

                    if(rpassword.length() > 0) {
                        if(rpassword.length() < 5) {
                            Snackbar snackbar = Snackbar.make(ll_register, "Panjang Ulang Password Minimal 5 Huruf", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            et_rpassword.setText("");
                        } else {
                            if(password.length() < 5) {
                                Snackbar snackbar = Snackbar.make(ll_register, "Password Tidak Sama", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                et_rpassword.setText("");
                            } else {
                                if(!password.equals(rpassword)) {
                                    Snackbar snackbar = Snackbar.make(ll_register, "Password Tidak Sama", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    et_rpassword.setText("");
                                }
                            }
                        }

                    }
                }
            }
        });

        et_phone = findViewById(R.id.et_phone);
        et_phone.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(count > 0) {
                    if(!et_phone.getText().toString().startsWith("0")) {
                        // Not allowed
                        Snackbar snackbar = Snackbar.make(ll_register, "Harus Didahului 0", Snackbar.LENGTH_SHORT);
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
        btn_daftar = findViewById(R.id.btn_daftar);
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et_username.getText().toString()) || TextUtils.isEmpty(et_password.getText().toString()) || TextUtils.isEmpty(et_rpassword.getText().toString()) || TextUtils.isEmpty(et_phone.getText().toString()) || TextUtils.isEmpty(et_alamat.getText().toString())) {
                    Snackbar snackbar = Snackbar.make(ll_register, "Semua Harus Diisi", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if(rg_gender.getCheckedRadioButtonId() == -1) {
                    Snackbar snackbar = Snackbar.make(ll_register, "Jenis Kelamin Harus dipilih", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if(et_username.getText().toString().trim().length() < 5) {
                    Snackbar snackbar = Snackbar.make(ll_register, "Panjang Username Minimal 5 Huruf", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    et_username.setText("");
                } else if(!et_password.getText().toString().trim().equals(et_rpassword.getText().toString().trim())) {
                    Snackbar snackbar = Snackbar.make(ll_register, "Password Tidak Sama", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    et_rpassword.setText("");
                } else {
                    String username = et_username.getText().toString().trim();
                    String password = md5(et_password.getText().toString().trim());
                    String phone = et_phone.getText().toString();
                    String jenis_kelamin = gender;
                    String alamat = et_alamat.getText().toString();

                    Intent i = new Intent(getApplicationContext(), PostRegisterActivity.class);
                    i.putExtra("username", username);
                    i.putExtra("password", password);
                    i.putExtra("phone", phone);
                    i.putExtra("jenis_kelamin", jenis_kelamin);
                    i.putExtra("alamat", alamat);
                    startActivity(i);
                }
            }
        });

    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
