package com.akhmadfaizin.cateringbusofanowner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.TreeMap;

public class TesPassingIntentActivity extends AppCompatActivity {
    private String namaKategori,
            namaPackage,
            tanggalPengiriman,
            jamPengiriman,
            atasNama,
            kontak,
            alamat,
            catatan;
    private int kuantitas,
                subTotalPrice,
                hargaDefault;
    private LinkedHashMap<String, TreeMap<Integer, PackageChoice>> collectionSelected = new LinkedHashMap<>();
    private String printResult = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes_passing_intent);

        EditText et_result_pass_intent = findViewById(R.id.et_result_pass_intent);

        /*
        switch(getIntent().getStringExtra("NamaPackage")) {
            case "Nasi Box A":
                namaKategori = PackageNasiASelect.getNamaKategori();
                namaPackage = PackageNasiASelect.getNamaPackage();
                kuantitas = PackageNasiASelect.getKuantitas();
                subTotalPrice = PackageNasiASelect.getSubTotalPrice();
                tanggalPengiriman = PackageNasiASelect.getTanggalPengiriman();
                jamPengiriman = PackageNasiASelect.getJamPengiriman();
                atasNama = PackageNasiASelect.getAtasNama();
                kontak = PackageNasiASelect.getKontak();
                alamat = PackageNasiASelect.getAlamat();
                catatan = PackageNasiASelect.getCatatan();
                collectionSelected = PackageNasiASelect.getCollectionSelected();
                break;
            case "Nasi Box B":
                namaKategori = PackageNasiBSelect.getNamaKategori();
                namaPackage = PackageNasiBSelect.getNamaPackage();
                kuantitas = PackageNasiBSelect.getKuantitas();
                subTotalPrice = PackageNasiBSelect.getSubTotalPrice();
                tanggalPengiriman = PackageNasiBSelect.getTanggalPengiriman();
                jamPengiriman = PackageNasiBSelect.getJamPengiriman();
                atasNama = PackageNasiBSelect.getAtasNama();
                kontak = PackageNasiBSelect.getKontak();
                alamat = PackageNasiBSelect.getAlamat();
                catatan = PackageNasiBSelect.getCatatan();
                collectionSelected = PackageNasiBSelect.getCollectionSelected();
                break;
            case "Nasi Box C":
                namaKategori = PackageNasiCSelect.getNamaKategori();
                namaPackage = PackageNasiCSelect.getNamaPackage();
                kuantitas = PackageNasiCSelect.getKuantitas();
                subTotalPrice = PackageNasiCSelect.getSubTotalPrice();
                tanggalPengiriman = PackageNasiCSelect.getTanggalPengiriman();
                jamPengiriman = PackageNasiCSelect.getJamPengiriman();
                atasNama = PackageNasiCSelect.getAtasNama();
                kontak = PackageNasiCSelect.getKontak();
                alamat = PackageNasiCSelect.getAlamat();
                catatan = PackageNasiCSelect.getCatatan();
                collectionSelected = PackageNasiCSelect.getCollectionSelected();
                break;
            case "Nasi Box D":
                namaKategori = PackageNasiDSelect.getNamaKategori();
                namaPackage = PackageNasiDSelect.getNamaPackage();
                kuantitas = PackageNasiDSelect.getKuantitas();
                subTotalPrice = PackageNasiDSelect.getSubTotalPrice();
                tanggalPengiriman = PackageNasiDSelect.getTanggalPengiriman();
                jamPengiriman = PackageNasiDSelect.getJamPengiriman();
                atasNama = PackageNasiDSelect.getAtasNama();
                kontak = PackageNasiDSelect.getKontak();
                alamat = PackageNasiDSelect.getAlamat();
                catatan = PackageNasiDSelect.getCatatan();
                collectionSelected = PackageNasiDSelect.getCollectionSelected();
                break;
            case "Snack Box A":
                namaKategori = PackageSnackASelect.getNamaKategori();
                namaPackage = PackageSnackASelect.getNamaPackage();
                kuantitas = PackageSnackASelect.getKuantitas();
                subTotalPrice = PackageSnackASelect.getSubTotalPrice();
                tanggalPengiriman = PackageSnackASelect.getTanggalPengiriman();
                jamPengiriman = PackageSnackASelect.getJamPengiriman();
                atasNama = PackageSnackASelect.getAtasNama();
                kontak = PackageSnackASelect.getKontak();
                alamat = PackageSnackASelect.getAlamat();
                catatan = PackageSnackASelect.getCatatan();
                collectionSelected = PackageSnackASelect.getCollectionSelected();
                break;
            case "Snack Box B":
                namaKategori = PackageSnackBSelect.getNamaKategori();
                namaPackage = PackageSnackBSelect.getNamaPackage();
                kuantitas = PackageSnackBSelect.getKuantitas();
                subTotalPrice = PackageSnackBSelect.getSubTotalPrice();
                tanggalPengiriman = PackageSnackBSelect.getTanggalPengiriman();
                jamPengiriman = PackageSnackBSelect.getJamPengiriman();
                atasNama = PackageSnackBSelect.getAtasNama();
                kontak = PackageSnackBSelect.getKontak();
                alamat = PackageSnackBSelect.getAlamat();
                catatan = PackageSnackBSelect.getCatatan();
                collectionSelected = PackageSnackBSelect.getCollectionSelected();
                break;
            case "Buffet A":
                namaKategori = PackageBuffetASelect.getNamaKategori();
                namaPackage = PackageBuffetASelect.getNamaPackage();
                kuantitas = PackageBuffetASelect.getKuantitas();
                subTotalPrice = PackageBuffetASelect.getSubTotalPrice();
                tanggalPengiriman = PackageBuffetASelect.getTanggalPengiriman();
                jamPengiriman = PackageBuffetASelect.getJamPengiriman();
                atasNama = PackageBuffetASelect.getAtasNama();
                kontak = PackageBuffetASelect.getKontak();
                alamat = PackageBuffetASelect.getAlamat();
                catatan = PackageBuffetASelect.getCatatan();
                collectionSelected = PackageBuffetASelect.getCollectionSelected();
                break;
            case "Buffet B":
                namaKategori = PackageBuffetBSelect.getNamaKategori();
                namaPackage = PackageBuffetBSelect.getNamaPackage();
                kuantitas = PackageBuffetBSelect.getKuantitas();
                subTotalPrice = PackageBuffetBSelect.getSubTotalPrice();
                tanggalPengiriman = PackageBuffetBSelect.getTanggalPengiriman();
                jamPengiriman = PackageBuffetBSelect.getJamPengiriman();
                atasNama = PackageBuffetBSelect.getAtasNama();
                kontak = PackageBuffetBSelect.getKontak();
                alamat = PackageBuffetBSelect.getAlamat();
                catatan = PackageBuffetBSelect.getCatatan();
                collectionSelected = PackageBuffetBSelect.getCollectionSelected();
                break;
            case "Buffet C":
                namaKategori = PackageBuffetCSelect.getNamaKategori();
                namaPackage = PackageBuffetCSelect.getNamaPackage();
                kuantitas = PackageBuffetCSelect.getKuantitas();
                subTotalPrice = PackageBuffetCSelect.getSubTotalPrice();
                tanggalPengiriman = PackageBuffetCSelect.getTanggalPengiriman();
                jamPengiriman = PackageBuffetCSelect.getJamPengiriman();
                atasNama = PackageBuffetCSelect.getAtasNama();
                kontak = PackageBuffetCSelect.getKontak();
                alamat = PackageBuffetCSelect.getAlamat();
                catatan = PackageBuffetCSelect.getCatatan();
                collectionSelected = PackageBuffetCSelect.getCollectionSelected();
                break;
            case "Pondokan":
                namaKategori = PackagePondokanSelect.getNamaKategori();
                namaPackage = PackagePondokanSelect.getNamaPackage();
                kuantitas = PackagePondokanSelect.getKuantitas();
                subTotalPrice = PackagePondokanSelect.getSubTotalPrice();
                tanggalPengiriman = PackagePondokanSelect.getTanggalPengiriman();
                jamPengiriman = PackagePondokanSelect.getJamPengiriman();
                atasNama = PackagePondokanSelect.getAtasNama();
                kontak = PackagePondokanSelect.getKontak();
                alamat = PackagePondokanSelect.getAlamat();
                catatan = PackagePondokanSelect.getCatatan();
                collectionSelected = PackagePondokanSelect.getCollectionSelected();
                break;
        }
        */


        /* PRETELEN HASHMAP SEMULA
        for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
            String key = entry.getKey();
            TreeMap<Integer, PackageChoice> value = entry.getValue();

            printResult += ( "== " + key + " ==\n");

            for (PackageChoice p : value.values()) {
                printResult += (p.getNama() + " - " + p.getHarga() + "\n");
            }
        }
        */

        collectionSelected = PackageBuffetASelect.getCollectionSelected();

        JSONArray jsonArrayRoot = new JSONArray();
        try {

            JSONObject list = new JSONObject();
            list.put("kategori", PackageBuffetASelect.getNamaKategori());
            list.put("menu", PackageBuffetASelect.getNamaPackage());
            list.put("kuantitas", PackageBuffetASelect.getKuantitas());
            list.put("subtotal", PackageBuffetASelect.getSubTotalPrice());

            JSONArray jsonArrayList = new JSONArray();

            for (LinkedHashMap.Entry<String, TreeMap<Integer, PackageChoice>> entry : collectionSelected.entrySet()) {
                String key = entry.getKey();
                TreeMap<Integer, PackageChoice> value = entry.getValue();

                JSONObject detailMenuObject = new JSONObject();
                detailMenuObject.put("namaItem", key);

                JSONArray arrayPilihan = new JSONArray();
                for (PackageChoice p : value.values()) {
                    arrayPilihan.put(p.getNama());
                }
                detailMenuObject.put("pilihan", arrayPilihan);
                jsonArrayList.put(detailMenuObject);
            }

            list.put("detailMenu", jsonArrayList);
            list.put("tanggalPengiriman", PackageBuffetASelect.getTanggalPengiriman());
            list.put("jamPengiriman", PackageBuffetASelect.getJamPengiriman());
            list.put("atasNama", PackageBuffetASelect.getAtasNama());
            list.put("kontak", PackageBuffetASelect.getKontak());
            list.put("alamat", PackageBuffetASelect.getAlamat());
            list.put("catatan", PackageBuffetASelect.getCatatan());
            jsonArrayRoot.put(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        printResult = jsonArrayRoot.toString();
        et_result_pass_intent.setText(printResult);

    }
}
