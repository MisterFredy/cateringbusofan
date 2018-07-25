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
import android.widget.RelativeLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    private RelativeLayout rl_login;
    private EditText et_username,
                    et_password;
    private ImageButton btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rl_login = findViewById(R.id.rl_login);
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
                            Snackbar snackbar = Snackbar.make(rl_login, "Panjang Username Minimal 5 Huruf", Snackbar.LENGTH_LONG);
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
                            Snackbar snackbar = Snackbar.make(rl_login, "Panjang Password Minimal 5 Huruf", Snackbar.LENGTH_LONG);
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
                    Snackbar snackbar = Snackbar.make(rl_login, "Password Tidak Boleh Mengandung Spasi", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et_username.getText().toString()) || TextUtils.isEmpty(et_password.getText().toString())) {
                    Snackbar snackbar = Snackbar.make(rl_login, "Semua Harus Diisi", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if(et_username.getText().toString().trim().length() < 5) {
                    Snackbar snackbar = Snackbar.make(rl_login, "Panjang Username Minimal 5 Huruf", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    et_username.setText("");
                } else if(et_password.getText().toString().trim().length() < 5) {
                    Snackbar snackbar = Snackbar.make(rl_login, "Panjang Password Minimal 5 Huruf", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    et_password.setText("");
                } else {
                    String username = et_username.getText().toString().trim();
                    String password = md5(et_password.getText().toString().trim());

                    Intent i = new Intent(getApplicationContext(), LoginAuthActivity.class);
                    i.putExtra("username", username);
                    i.putExtra("password", password);
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
