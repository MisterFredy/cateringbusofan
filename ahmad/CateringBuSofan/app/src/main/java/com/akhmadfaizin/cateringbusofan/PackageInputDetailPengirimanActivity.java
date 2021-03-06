package com.akhmadfaizin.cateringbusofan;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PackageInputDetailPengirimanActivity extends AppCompatActivity {
    private LinearLayout ll_inputdetailpengiriman;
    private TextView tv_kategori,
                    tv_paket;
    private EditText et_porsi,
                    et_nama,
                    et_tanggal_pengiriman,
                    et_jam_pengiriman,
                    et_phone,
                    et_alamat,
                    et_catatan;
    private ImageButton btn_keranjang;
    private String datePostFormat;
    Calendar myCalendar = Calendar.getInstance();
    private int jsonDateDay, jsonDateMonth, jsonDateYear;
    private String jsonDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_input_detail_pengiriman);

        ll_inputdetailpengiriman = findViewById(R.id.ll_inputdetailpengiriman);
        tv_kategori = findViewById(R.id.tv_kategori);
        tv_paket = findViewById(R.id.tv_paket);
        et_nama = findViewById(R.id.et_nama);
        et_alamat = findViewById(R.id.et_alamat);
        et_catatan = findViewById(R.id.et_catatan);
        btn_keranjang = findViewById(R.id.btn_kekeranjang);

        getSupportActionBar().setTitle("Detail Pengiriman");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_kategori.setText(getIntent().getStringExtra("NamaKategori"));
        tv_paket.setText(getIntent().getStringExtra("NamaPackage"));

        et_porsi = findViewById(R.id.et_porsi);
        et_porsi.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (et_porsi.getText().toString().matches("^0") )
                {
                    // Not allowed
                    Snackbar snackbar = Snackbar.make(ll_inputdetailpengiriman, "Tidak Boleh Awalan 0", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    et_porsi.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        et_porsi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    if(et_porsi.getText().toString().length() > 0) {
                        int porsi = Integer.valueOf(et_porsi.getText().toString());
                        if(porsi < 50) {
                            Snackbar snackbar = Snackbar.make(ll_inputdetailpengiriman, "Jumlah Pesanan minimal 50 porsi", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            et_porsi.setText("");
                        }
                    }

                }
            }
        });


        et_tanggal_pengiriman = findViewById(R.id.et_tanggal_pengiriman);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        et_tanggal_pengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(PackageInputDetailPengirimanActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        et_jam_pengiriman = findViewById(R.id.et_jam_pengiriman);
        et_jam_pengiriman.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(PackageInputDetailPengirimanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        et_jam_pengiriman.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Silahkan Pilih Waktu");
                mTimePicker.show();

            }
        });

        et_phone = findViewById(R.id.et_phone);
        et_phone.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(count > 0) {
                    if(!et_phone.getText().toString().startsWith("0")) {
                        // Not allowed
                        Snackbar snackbar = Snackbar.make(ll_inputdetailpengiriman, "Harus Didahului 0", Snackbar.LENGTH_SHORT);
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

        /* CODE YANG MEMPENGARUHI SESI EDIT */
        if(getIntent().getStringExtra("NECESSITY").equals("EDIT")) {
            switch(getIntent().getStringExtra("NamaPackage")) {

                case "Nasi Box A":
                    //Porsi
                    et_porsi.setText(String.valueOf(PackageNasiASelect.getKuantitas()));
                    //Atas Nama
                    et_nama.setText(PackageNasiASelect.getAtasNama());
                    //TanggalPengiriman
                    // Load String dari API
                    jsonDate = PackageNasiASelect.getTanggalPengiriman();
                    //Jam Pengiriman
                    et_jam_pengiriman.setText(PackageNasiASelect.getJamPengiriman());
                    //No Handphone
                    et_phone.setText(PackageNasiASelect.getKontak());
                    //Alamat
                    et_alamat.setText(PackageNasiASelect.getAlamat());
                    //Catatan
                    et_catatan.setText(PackageNasiASelect.getCatatan());
                    break;

                case "Nasi Box B":
                    //Porsi
                    et_porsi.setText(String.valueOf(PackageNasiBSelect.getKuantitas()));
                    //Atas Nama
                    et_nama.setText(PackageNasiBSelect.getAtasNama());
                    //TanggalPengiriman
                    // Load String dari API
                    jsonDate = PackageNasiBSelect.getTanggalPengiriman();
                    //Jam Pengiriman
                    et_jam_pengiriman.setText(PackageNasiBSelect.getJamPengiriman());
                    //No Handphone
                    et_phone.setText(PackageNasiBSelect.getKontak());
                    //Alamat
                    et_alamat.setText(PackageNasiBSelect.getAlamat());
                    //Catatan
                    et_catatan.setText(PackageNasiBSelect.getCatatan());
                    break;

                case "Nasi Box C":
                    //Porsi
                    et_porsi.setText(String.valueOf(PackageNasiCSelect.getKuantitas()));
                    //Atas Nama
                    et_nama.setText(PackageNasiCSelect.getAtasNama());
                    //TanggalPengiriman
                    // Load String dari API
                    jsonDate = PackageNasiCSelect.getTanggalPengiriman();
                    //Jam Pengiriman
                    et_jam_pengiriman.setText(PackageNasiCSelect.getJamPengiriman());
                    //No Handphone
                    et_phone.setText(PackageNasiCSelect.getKontak());
                    //Alamat
                    et_alamat.setText(PackageNasiCSelect.getAlamat());
                    //Catatan
                    et_catatan.setText(PackageNasiCSelect.getCatatan());
                    break;

                case "Nasi Box D":
                    //Porsi
                    et_porsi.setText(String.valueOf(PackageNasiDSelect.getKuantitas()));
                    //Atas Nama
                    et_nama.setText(PackageNasiDSelect.getAtasNama());
                    //TanggalPengiriman
                    // Load String dari API
                    jsonDate = PackageNasiDSelect.getTanggalPengiriman();
                    //Jam Pengiriman
                    et_jam_pengiriman.setText(PackageNasiDSelect.getJamPengiriman());
                    //No Handphone
                    et_phone.setText(PackageNasiDSelect.getKontak());
                    //Alamat
                    et_alamat.setText(PackageNasiDSelect.getAlamat());
                    //Catatan
                    et_catatan.setText(PackageNasiDSelect.getCatatan());
                    break;

                case "Snack Box A":
                    //Porsi
                    et_porsi.setText(String.valueOf(PackageSnackASelect.getKuantitas()));
                    //Atas Nama
                    et_nama.setText(PackageSnackASelect.getAtasNama());
                    //TanggalPengiriman
                    // Load String dari API
                    jsonDate = PackageSnackASelect.getTanggalPengiriman();
                    //Jam Pengiriman
                    et_jam_pengiriman.setText(PackageSnackASelect.getJamPengiriman());
                    //No Handphone
                    et_phone.setText(PackageSnackASelect.getKontak());
                    //Alamat
                    et_alamat.setText(PackageSnackASelect.getAlamat());
                    //Catatan
                    et_catatan.setText(PackageSnackASelect.getCatatan());
                    break;

                case "Snack Box B":
                    //Porsi
                    et_porsi.setText(String.valueOf(PackageSnackBSelect.getKuantitas()));
                    //Atas Nama
                    et_nama.setText(PackageSnackBSelect.getAtasNama());
                    //TanggalPengiriman
                    // Load String dari API
                    jsonDate = PackageSnackBSelect.getTanggalPengiriman();
                    //Jam Pengiriman
                    et_jam_pengiriman.setText(PackageSnackBSelect.getJamPengiriman());
                    //No Handphone
                    et_phone.setText(PackageSnackBSelect.getKontak());
                    //Alamat
                    et_alamat.setText(PackageSnackBSelect.getAlamat());
                    //Catatan
                    et_catatan.setText(PackageSnackBSelect.getCatatan());
                    break;

                case "Buffet A":
                    //Porsi
                    et_porsi.setText(String.valueOf(PackageBuffetASelect.getKuantitas()));
                    //Atas Nama
                    et_nama.setText(PackageBuffetASelect.getAtasNama());
                    //TanggalPengiriman
                    // Load String dari API
                    jsonDate = PackageBuffetASelect.getTanggalPengiriman();
                    //Jam Pengiriman
                    et_jam_pengiriman.setText(PackageBuffetASelect.getJamPengiriman());
                    //No Handphone
                    et_phone.setText(PackageBuffetASelect.getKontak());
                    //Alamat
                    et_alamat.setText(PackageBuffetASelect.getAlamat());
                    //Catatan
                    et_catatan.setText(PackageBuffetASelect.getCatatan());
                    break;

                case "Buffet B":
                    //Porsi
                    et_porsi.setText(String.valueOf(PackageBuffetBSelect.getKuantitas()));
                    //Atas Nama
                    et_nama.setText(PackageBuffetBSelect.getAtasNama());
                    //TanggalPengiriman
                    // Load String dari API
                    jsonDate = PackageBuffetBSelect.getTanggalPengiriman();
                    //Jam Pengiriman
                    et_jam_pengiriman.setText(PackageBuffetBSelect.getJamPengiriman());
                    //No Handphone
                    et_phone.setText(PackageBuffetBSelect.getKontak());
                    //Alamat
                    et_alamat.setText(PackageBuffetBSelect.getAlamat());
                    //Catatan
                    et_catatan.setText(PackageBuffetBSelect.getCatatan());
                    break;

                case "Buffet C":
                    //Porsi
                    et_porsi.setText(String.valueOf(PackageBuffetCSelect.getKuantitas()));
                    //Atas Nama
                    et_nama.setText(PackageBuffetCSelect.getAtasNama());
                    //TanggalPengiriman
                    // Load String dari API
                    jsonDate = PackageBuffetCSelect.getTanggalPengiriman();
                    //Jam Pengiriman
                    et_jam_pengiriman.setText(PackageBuffetCSelect.getJamPengiriman());
                    //No Handphone
                    et_phone.setText(PackageBuffetCSelect.getKontak());
                    //Alamat
                    et_alamat.setText(PackageBuffetCSelect.getAlamat());
                    //Catatan
                    et_catatan.setText(PackageBuffetCSelect.getCatatan());
                    break;

                case "Pondokan":
                    //Porsi
                    et_porsi.setText(String.valueOf(PackagePondokanSelect.getKuantitas()));
                    //Atas Nama
                    et_nama.setText(PackagePondokanSelect.getAtasNama());
                    //TanggalPengiriman
                    // Load String dari API
                    jsonDate = PackagePondokanSelect.getTanggalPengiriman();
                    //Jam Pengiriman
                    et_jam_pengiriman.setText(PackagePondokanSelect.getJamPengiriman());
                    //No Handphone
                    et_phone.setText(PackagePondokanSelect.getKontak());
                    //Alamat
                    et_alamat.setText(PackagePondokanSelect.getAlamat());
                    //Catatan
                    et_catatan.setText(PackagePondokanSelect.getCatatan());
                    break;
            }

            // Split Tanggal, Bulan, Tahun
            String[] jsonDateSplit = jsonDate.split("-");
            jsonDateDay = Integer.valueOf(jsonDateSplit[0]);
            jsonDateMonth = Integer.valueOf(jsonDateSplit[1]) - 1;
            jsonDateYear = Integer.valueOf(jsonDateSplit[2]);
            myCalendar.set(Calendar.YEAR, jsonDateYear);
            myCalendar.set(Calendar.MONTH, jsonDateMonth);
            myCalendar.set(Calendar.DAY_OF_MONTH, jsonDateDay);
            updateLabel();
        }

        btn_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(et_porsi.getText().toString()) || TextUtils.isEmpty(et_nama.getText().toString()) || TextUtils.isEmpty(et_tanggal_pengiriman.getText().toString()) || TextUtils.isEmpty(et_jam_pengiriman.getText().toString()) || TextUtils.isEmpty(et_phone.getText().toString()) || TextUtils.isEmpty(et_alamat.getText().toString())) {
                    Snackbar snackbar = Snackbar.make(ll_inputdetailpengiriman, "Semua Harus Diisi, kecuali Catatan", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if(Integer.valueOf(et_porsi.getText().toString()) < 50) {
                    Snackbar snackbar = Snackbar.make(ll_inputdetailpengiriman, "Jumlah Pesanan minimal 50 porsi", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    et_porsi.setText("");
                } else {
                    switch(getIntent().getStringExtra("NamaPackage")) {
                        case "Nasi Box A":
                            PackageNasiASelect.setNamaKategori(getIntent().getStringExtra("NamaKategori"));
                            PackageNasiASelect.setNamaPackage(getIntent().getStringExtra("NamaPackage"));
                            PackageNasiASelect.setDefaultPrice(getIntent().getIntExtra("DefaultPrice", 0));
                            PackageNasiASelect.setFinalPrice(getIntent().getIntExtra("FinalPrice", 0));
                            PackageNasiASelect.setKuantitas(Integer.valueOf(et_porsi.getText().toString()));
                            PackageNasiASelect.setSubTotalPrice(PackageNasiASelect.getFinalPrice() * PackageNasiASelect.getKuantitas());
                            PackageNasiASelect.setTanggalPengiriman(datePostFormat);
                            PackageNasiASelect.setJamPengiriman(et_jam_pengiriman.getText().toString());
                            PackageNasiASelect.setAtasNama(et_nama.getText().toString());
                            PackageNasiASelect.setKontak(et_phone.getText().toString());
                            PackageNasiASelect.setAlamat(et_alamat.getText().toString());
                            PackageNasiASelect.setCatatan(et_catatan.getText().toString());
                            PackageNasiASelect.setCollectionSelected(PackageNasiASelect.getTemporaryCollectionSelected());
                            break;
                        case "Nasi Box B":
                            PackageNasiBSelect.setNamaKategori(getIntent().getStringExtra("NamaKategori"));
                            PackageNasiBSelect.setNamaPackage(getIntent().getStringExtra("NamaPackage"));
                            PackageNasiBSelect.setDefaultPrice(getIntent().getIntExtra("DefaultPrice", 0));
                            PackageNasiBSelect.setFinalPrice(getIntent().getIntExtra("FinalPrice", 0));
                            PackageNasiBSelect.setKuantitas(Integer.valueOf(et_porsi.getText().toString()));
                            PackageNasiBSelect.setSubTotalPrice(PackageNasiBSelect.getFinalPrice() * PackageNasiBSelect.getKuantitas());
                            PackageNasiBSelect.setTanggalPengiriman(datePostFormat);
                            PackageNasiBSelect.setJamPengiriman(et_jam_pengiriman.getText().toString());
                            PackageNasiBSelect.setAtasNama(et_nama.getText().toString());
                            PackageNasiBSelect.setKontak(et_phone.getText().toString());
                            PackageNasiBSelect.setAlamat(et_alamat.getText().toString());
                            PackageNasiBSelect.setCatatan(et_catatan.getText().toString());
                            PackageNasiBSelect.setCollectionSelected(PackageNasiBSelect.getTemporaryCollectionSelected());
                            break;
                        case "Nasi Box C":
                            PackageNasiCSelect.setNamaKategori(getIntent().getStringExtra("NamaKategori"));
                            PackageNasiCSelect.setNamaPackage(getIntent().getStringExtra("NamaPackage"));
                            PackageNasiCSelect.setDefaultPrice(getIntent().getIntExtra("DefaultPrice", 0));
                            PackageNasiCSelect.setFinalPrice(getIntent().getIntExtra("FinalPrice", 0));
                            PackageNasiCSelect.setKuantitas(Integer.valueOf(et_porsi.getText().toString()));
                            PackageNasiCSelect.setSubTotalPrice(PackageNasiCSelect.getFinalPrice() * PackageNasiCSelect.getKuantitas());
                            PackageNasiCSelect.setTanggalPengiriman(datePostFormat);
                            PackageNasiCSelect.setJamPengiriman(et_jam_pengiriman.getText().toString());
                            PackageNasiCSelect.setAtasNama(et_nama.getText().toString());
                            PackageNasiCSelect.setKontak(et_phone.getText().toString());
                            PackageNasiCSelect.setAlamat(et_alamat.getText().toString());
                            PackageNasiCSelect.setCatatan(et_catatan.getText().toString());
                            PackageNasiCSelect.setCollectionSelected(PackageNasiCSelect.getTemporaryCollectionSelected());
                            break;
                        case "Nasi Box D":
                            PackageNasiDSelect.setNamaKategori(getIntent().getStringExtra("NamaKategori"));
                            PackageNasiDSelect.setNamaPackage(getIntent().getStringExtra("NamaPackage"));
                            PackageNasiDSelect.setDefaultPrice(getIntent().getIntExtra("DefaultPrice", 0));
                            PackageNasiDSelect.setFinalPrice(getIntent().getIntExtra("FinalPrice", 0));
                            PackageNasiDSelect.setKuantitas(Integer.valueOf(et_porsi.getText().toString()));
                            PackageNasiDSelect.setSubTotalPrice(PackageNasiDSelect.getFinalPrice() * PackageNasiDSelect.getKuantitas());
                            PackageNasiDSelect.setTanggalPengiriman(datePostFormat);
                            PackageNasiDSelect.setJamPengiriman(et_jam_pengiriman.getText().toString());
                            PackageNasiDSelect.setAtasNama(et_nama.getText().toString());
                            PackageNasiDSelect.setKontak(et_phone.getText().toString());
                            PackageNasiDSelect.setAlamat(et_alamat.getText().toString());
                            PackageNasiDSelect.setCatatan(et_catatan.getText().toString());
                            PackageNasiDSelect.setCollectionSelected(PackageNasiDSelect.getTemporaryCollectionSelected());
                            break;
                        case "Snack Box A":
                            PackageSnackASelect.setNamaKategori(getIntent().getStringExtra("NamaKategori"));
                            PackageSnackASelect.setNamaPackage(getIntent().getStringExtra("NamaPackage"));
                            PackageSnackASelect.setDefaultPrice(getIntent().getIntExtra("DefaultPrice", 0));
                            PackageSnackASelect.setFinalPrice(getIntent().getIntExtra("FinalPrice", 0));
                            PackageSnackASelect.setKuantitas(Integer.valueOf(et_porsi.getText().toString()));
                            PackageSnackASelect.setSubTotalPrice(PackageSnackASelect.getFinalPrice() * PackageSnackASelect.getKuantitas());
                            PackageSnackASelect.setTanggalPengiriman(datePostFormat);
                            PackageSnackASelect.setJamPengiriman(et_jam_pengiriman.getText().toString());
                            PackageSnackASelect.setAtasNama(et_nama.getText().toString());
                            PackageSnackASelect.setKontak(et_phone.getText().toString());
                            PackageSnackASelect.setAlamat(et_alamat.getText().toString());
                            PackageSnackASelect.setCatatan(et_catatan.getText().toString());
                            PackageSnackASelect.setCollectionSelected(PackageSnackASelect.getTemporaryCollectionSelected());
                            break;
                        case "Snack Box B":
                            PackageSnackBSelect.setNamaKategori(getIntent().getStringExtra("NamaKategori"));
                            PackageSnackBSelect.setNamaPackage(getIntent().getStringExtra("NamaPackage"));
                            PackageSnackBSelect.setDefaultPrice(getIntent().getIntExtra("DefaultPrice", 0));
                            PackageSnackBSelect.setFinalPrice(getIntent().getIntExtra("FinalPrice", 0));
                            PackageSnackBSelect.setKuantitas(Integer.valueOf(et_porsi.getText().toString()));
                            PackageSnackBSelect.setSubTotalPrice(PackageSnackBSelect.getFinalPrice() * PackageSnackBSelect.getKuantitas());
                            PackageSnackBSelect.setTanggalPengiriman(datePostFormat);
                            PackageSnackBSelect.setJamPengiriman(et_jam_pengiriman.getText().toString());
                            PackageSnackBSelect.setAtasNama(et_nama.getText().toString());
                            PackageSnackBSelect.setKontak(et_phone.getText().toString());
                            PackageSnackBSelect.setAlamat(et_alamat.getText().toString());
                            PackageSnackBSelect.setCatatan(et_catatan.getText().toString());
                            PackageSnackBSelect.setCollectionSelected(PackageSnackBSelect.getTemporaryCollectionSelected());
                            break;
                        case "Buffet A":
                            PackageBuffetASelect.setNamaKategori(getIntent().getStringExtra("NamaKategori"));
                            PackageBuffetASelect.setNamaPackage(getIntent().getStringExtra("NamaPackage"));
                            PackageBuffetASelect.setDefaultPrice(getIntent().getIntExtra("DefaultPrice", 0));
                            PackageBuffetASelect.setFinalPrice(getIntent().getIntExtra("FinalPrice", 0));
                            PackageBuffetASelect.setKuantitas(Integer.valueOf(et_porsi.getText().toString()));
                            PackageBuffetASelect.setSubTotalPrice(PackageBuffetASelect.getFinalPrice() * PackageBuffetASelect.getKuantitas());
                            PackageBuffetASelect.setTanggalPengiriman(datePostFormat);
                            PackageBuffetASelect.setJamPengiriman(et_jam_pengiriman.getText().toString());
                            PackageBuffetASelect.setAtasNama(et_nama.getText().toString());
                            PackageBuffetASelect.setKontak(et_phone.getText().toString());
                            PackageBuffetASelect.setAlamat(et_alamat.getText().toString());
                            PackageBuffetASelect.setCatatan(et_catatan.getText().toString());
                            PackageBuffetASelect.setCollectionSelected(PackageBuffetASelect.getTemporaryCollectionSelected());
                            break;
                        case "Buffet B":
                            PackageBuffetBSelect.setNamaKategori(getIntent().getStringExtra("NamaKategori"));
                            PackageBuffetBSelect.setNamaPackage(getIntent().getStringExtra("NamaPackage"));
                            PackageBuffetBSelect.setDefaultPrice(getIntent().getIntExtra("DefaultPrice", 0));
                            PackageBuffetBSelect.setFinalPrice(getIntent().getIntExtra("FinalPrice", 0));
                            PackageBuffetBSelect.setKuantitas(Integer.valueOf(et_porsi.getText().toString()));
                            PackageBuffetBSelect.setSubTotalPrice(PackageBuffetBSelect.getFinalPrice() * PackageBuffetBSelect.getKuantitas());
                            PackageBuffetBSelect.setTanggalPengiriman(datePostFormat);
                            PackageBuffetBSelect.setJamPengiriman(et_jam_pengiriman.getText().toString());
                            PackageBuffetBSelect.setAtasNama(et_nama.getText().toString());
                            PackageBuffetBSelect.setKontak(et_phone.getText().toString());
                            PackageBuffetBSelect.setAlamat(et_alamat.getText().toString());
                            PackageBuffetBSelect.setCatatan(et_catatan.getText().toString());
                            PackageBuffetBSelect.setCollectionSelected(PackageBuffetBSelect.getTemporaryCollectionSelected());
                            break;
                        case "Buffet C":
                            PackageBuffetCSelect.setNamaKategori(getIntent().getStringExtra("NamaKategori"));
                            PackageBuffetCSelect.setNamaPackage(getIntent().getStringExtra("NamaPackage"));
                            PackageBuffetCSelect.setDefaultPrice(getIntent().getIntExtra("DefaultPrice", 0));
                            PackageBuffetCSelect.setFinalPrice(getIntent().getIntExtra("FinalPrice", 0));
                            PackageBuffetCSelect.setKuantitas(Integer.valueOf(et_porsi.getText().toString()));
                            PackageBuffetCSelect.setSubTotalPrice(PackageBuffetCSelect.getFinalPrice() * PackageBuffetCSelect.getKuantitas());
                            PackageBuffetCSelect.setTanggalPengiriman(datePostFormat);
                            PackageBuffetCSelect.setJamPengiriman(et_jam_pengiriman.getText().toString());
                            PackageBuffetCSelect.setAtasNama(et_nama.getText().toString());
                            PackageBuffetCSelect.setKontak(et_phone.getText().toString());
                            PackageBuffetCSelect.setAlamat(et_alamat.getText().toString());
                            PackageBuffetCSelect.setCatatan(et_catatan.getText().toString());
                            PackageBuffetCSelect.setCollectionSelected(PackageBuffetCSelect.getTemporaryCollectionSelected());
                            break;
                        case "Pondokan":
                            PackagePondokanSelect.setNamaKategori(getIntent().getStringExtra("NamaKategori"));
                            PackagePondokanSelect.setNamaPackage(getIntent().getStringExtra("NamaPackage"));
                            PackagePondokanSelect.setDefaultPrice(getIntent().getIntExtra("DefaultPrice", 0));
                            PackagePondokanSelect.setFinalPrice(getIntent().getIntExtra("FinalPrice", 0));
                            PackagePondokanSelect.setKuantitas(Integer.valueOf(et_porsi.getText().toString()));
                            PackagePondokanSelect.setSubTotalPrice(PackagePondokanSelect.getFinalPrice() * PackagePondokanSelect.getKuantitas());
                            PackagePondokanSelect.setTanggalPengiriman(datePostFormat);
                            PackagePondokanSelect.setJamPengiriman(et_jam_pengiriman.getText().toString());
                            PackagePondokanSelect.setAtasNama(et_nama.getText().toString());
                            PackagePondokanSelect.setKontak(et_phone.getText().toString());
                            PackagePondokanSelect.setAlamat(et_alamat.getText().toString());
                            PackagePondokanSelect.setCatatan(et_catatan.getText().toString());
                            PackagePondokanSelect.setCollectionSelected(PackagePondokanSelect.getTemporaryCollectionSelected());
                            break;
                    }

                    Intent i = new Intent(getApplicationContext(), KeranjangActivity.class);
                    i.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity(i);
                }

            }
        });
    }

    // Update Label Tanggal
    private void updateLabel() {
        String viewFormat = "dd MMMM yyyy";
        Locale id = new Locale("in", "ID");
        SimpleDateFormat sdfView = new SimpleDateFormat(viewFormat, id);

        String jsonFormat = "dd-MM-yyyy";
        SimpleDateFormat sdfJson = new SimpleDateFormat(jsonFormat, Locale.US);

        datePostFormat = sdfJson.format(myCalendar.getTime());
        et_tanggal_pengiriman.setText(sdfView.format(myCalendar.getTime()));
    }

    /*
        Making The Up Button behavior like Back Button
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

}
