package com.akhmadfaizin.cateringbusofan;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GantiPasswordActivity extends AppCompatActivity {
    private RelativeLayout rl_ubahpassword;
    private EditText et_password_lama,
                    et_password_baru,
                    et_rpassword_baru;
    private ImageButton btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);

        getSupportActionBar().setTitle("Ganti Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rl_ubahpassword = findViewById(R.id.rl_ubahpassword);


        et_password_lama = findViewById(R.id.et_password_lama);
        et_password_lama.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    String password = et_password_lama.getText().toString().trim();

                    if(password.length() > 0) {
                        if(password.length() < 5) {
                            Toast.makeText(GantiPasswordActivity.this, "Panjang Password Lama Minimal 5 Huruf", Toast.LENGTH_SHORT).show();
                            et_password_lama.setText("");
                        }
                    }
                }
            }
        });

        et_password_lama.addTextChangedListener(new TextWatcher() {
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
                    et_password_lama.setText(result);
                    et_password_lama.setSelection(result.length());
                    // alert the user
                    Toast.makeText(GantiPasswordActivity.this, "Password Lama Tidak Boleh Mengandung Spasi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        et_password_baru = findViewById(R.id.et_password_baru);
        et_password_baru.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    String password = et_password_baru.getText().toString().trim();

                    if(password.length() > 0) {
                        if(password.length() < 5) {
                            Toast.makeText(GantiPasswordActivity.this, "Panjang Password Baru Minimal 5 Huruf", Toast.LENGTH_SHORT).show();
                            et_password_baru.setText("");
                        }
                    }
                }
            }
        });

        et_password_baru.addTextChangedListener(new TextWatcher() {
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
                    et_password_baru.setText(result);
                    et_password_baru.setSelection(result.length());
                    // alert the user
                    Toast.makeText(GantiPasswordActivity.this, "Password Baru Tidak Boleh Mengandung Spasi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        et_rpassword_baru = findViewById(R.id.et_rpassword_baru);
        et_rpassword_baru.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    String password = et_password_baru.getText().toString().trim();
                    String rpassword = et_rpassword_baru.getText().toString().trim();

                    if(rpassword.length() > 0) {
                        if(rpassword.length() < 5) {
                            Toast.makeText(GantiPasswordActivity.this, "Panjang Ulang Password Baru Minimal 5 Huruf", Toast.LENGTH_SHORT).show();
                            et_rpassword_baru.setText("");
                        } else {
                            if(password.length() < 5) {
                                Toast.makeText(GantiPasswordActivity.this, "Password Baru Tidak Sama", Toast.LENGTH_SHORT).show();
                                et_rpassword_baru.setText("");
                            } else {
                                if(!password.equals(rpassword)) {
                                    Toast.makeText(GantiPasswordActivity.this, "Password Baru Tidak Sama", Toast.LENGTH_SHORT).show();
                                    et_rpassword_baru.setText("");
                                }
                            }
                        }

                    }
                }
            }
        });

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et_password_lama.getText().toString()) || TextUtils.isEmpty(et_password_baru.getText().toString()) || TextUtils.isEmpty(et_rpassword_baru.getText().toString())) {
                    Toast.makeText(GantiPasswordActivity.this, "Semua Harus Diisi", Toast.LENGTH_SHORT).show();
                } else if(et_password_lama.getText().toString().trim().length() < 5) {
                    Toast.makeText(GantiPasswordActivity.this, "Panjang Password Lama Minimal 5 Huruf", Toast.LENGTH_SHORT).show();
                    et_password_lama.setText("");
                } else if(!et_password_baru.getText().toString().trim().equals(et_rpassword_baru.getText().toString().trim())) {
                    Toast.makeText(GantiPasswordActivity.this, "Password Baru Tidak Sama", Toast.LENGTH_SHORT).show();
                    et_rpassword_baru.setText("");
                } else {
                    String password_lama = md5(et_password_lama.getText().toString().trim());
                    String password_baru = md5(et_password_baru.getText().toString().trim());

                    Intent i = new Intent(getApplicationContext(), PutUbahPasswordActivity.class);
                    i.putExtra("password_lama", password_lama);
                    i.putExtra("password_baru", password_baru);
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
