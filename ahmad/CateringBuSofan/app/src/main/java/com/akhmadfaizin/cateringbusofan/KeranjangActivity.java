package com.akhmadfaizin.cateringbusofan;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class KeranjangActivity extends AppCompatActivity {
    private Boolean isEmpty;
    private TextView tvTotal;
    private Button btnSubmitOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#792525"));
        }

        getSupportActionBar().setTitle("Keranjang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Check if Keranjang Empty
        isEmpty = isSelectedEmpty();

        if(isEmpty) {
            setContentView(R.layout.activity_keranjang_kosong);
        } else {
            setContentView(R.layout.activity_keranjang);
            final NonScrollListView non_scroll_list = (NonScrollListView) findViewById(R.id.lv_keranjang);
            tvTotal = findViewById(R.id.tv_total);
            btnSubmitOrder = findViewById(R.id.btn_submit_order);

            List<Keranjang> keranjangItem = new ArrayList<>();
            keranjangItem = getKeranjangItem();

            final KeranjangAdapter adapterA = new KeranjangAdapter(this, keranjangItem);
            non_scroll_list.setAdapter(adapterA);

            final int total = getTotal(keranjangItem);

            tvTotal.setText("Rp " + String.format("%,d", total));

            btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Build an AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(KeranjangActivity.this);

                    // Set a title for Alert Dialog
                    builder.setTitle("Konfirmasi");

                    // Ask the final question
                    builder.setMessage("Anda Yakin Ingin Submit Order ?");

                    // Set the alert dialog yes button click listener
                    builder.setPositiveButton("Submit Order", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getApplicationContext(), PostPemesananActivity.class);
                            i.putExtra("totalBayar", total);
                            startActivity(i);
                        }
                    });

                    // Set the alert dialog no button click listener
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    // Display the alert dialog on interface
                    dialog.show();

                }
            });
        }

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(i);
    }

    public Boolean isSelectedEmpty() {
        Boolean isEmpty = true;

        if(PackageNasiASelect.getNamaPackage() != null && !PackageNasiASelect.getNamaPackage().isEmpty()) {
            isEmpty = false;
        } else if (PackageNasiBSelect.getNamaPackage() != null && !PackageNasiBSelect.getNamaPackage().isEmpty()) {
            isEmpty = false;
        } else if (PackageNasiCSelect.getNamaPackage() != null && !PackageNasiCSelect.getNamaPackage().isEmpty()) {
            isEmpty = false;
        } else if (PackageNasiDSelect.getNamaPackage() != null && !PackageNasiDSelect.getNamaPackage().isEmpty()) {
            isEmpty = false;
        } else if (PackageSnackASelect.getNamaPackage() != null && !PackageSnackASelect.getNamaPackage().isEmpty()) {
            isEmpty = false;
        } else if (PackageSnackBSelect.getNamaPackage() != null && !PackageSnackBSelect.getNamaPackage().isEmpty()) {
            isEmpty = false;
        } else if (PackageBuffetASelect.getNamaPackage() != null && !PackageBuffetASelect.getNamaPackage().isEmpty()) {
            isEmpty = false;
        } else if (PackageBuffetBSelect.getNamaPackage() != null && !PackageBuffetBSelect.getNamaPackage().isEmpty()) {
            isEmpty = false;
        } else if (PackageBuffetCSelect.getNamaPackage() != null && !PackageBuffetCSelect.getNamaPackage().isEmpty()) {
            isEmpty = false;
        } else if (PackagePondokanSelect.getNamaPackage() != null && !PackagePondokanSelect.getNamaPackage().isEmpty()) {
            isEmpty = false;
        }

        return isEmpty;
    }

    public List<Keranjang> getKeranjangItem() {
        List<Keranjang> keranjangItem = new ArrayList<>();

        if(PackageNasiASelect.getNamaPackage() != null && !PackageNasiASelect.getNamaPackage().isEmpty()) {
            String namaKategori = PackageNasiASelect.getNamaKategori();
            String namaPackage = PackageNasiASelect.getNamaPackage();
            int perPorsi = PackageNasiASelect.getFinalPrice();
            int kuantitas = PackageNasiASelect.getKuantitas();
            int subTotal = PackageNasiASelect.getSubTotalPrice();

            keranjangItem.add(new Keranjang(namaKategori, namaPackage, perPorsi, kuantitas, subTotal, R.drawable.paket_nasi_a));
        }

        if (PackageNasiBSelect.getNamaPackage() != null && !PackageNasiBSelect.getNamaPackage().isEmpty()) {
            String namaKategori = PackageNasiBSelect.getNamaKategori();
            String namaPackage = PackageNasiBSelect.getNamaPackage();
            int perPorsi = PackageNasiBSelect.getFinalPrice();
            int kuantitas = PackageNasiBSelect.getKuantitas();
            int subTotal = PackageNasiBSelect.getSubTotalPrice();

            keranjangItem.add(new Keranjang(namaKategori, namaPackage, perPorsi, kuantitas, subTotal, R.drawable.paket_nasi_b));
        }

        if (PackageNasiCSelect.getNamaPackage() != null && !PackageNasiCSelect.getNamaPackage().isEmpty()) {
            String namaKategori = PackageNasiCSelect.getNamaKategori();
            String namaPackage = PackageNasiCSelect.getNamaPackage();
            int perPorsi = PackageNasiCSelect.getFinalPrice();
            int kuantitas = PackageNasiCSelect.getKuantitas();
            int subTotal = PackageNasiCSelect.getSubTotalPrice();

            keranjangItem.add(new Keranjang(namaKategori, namaPackage, perPorsi, kuantitas, subTotal, R.drawable.paket_nasi_c));
        }

        if (PackageNasiDSelect.getNamaPackage() != null && !PackageNasiDSelect.getNamaPackage().isEmpty()) {
            String namaKategori = PackageNasiDSelect.getNamaKategori();
            String namaPackage = PackageNasiDSelect.getNamaPackage();
            int perPorsi = PackageNasiDSelect.getFinalPrice();
            int kuantitas = PackageNasiDSelect.getKuantitas();
            int subTotal = PackageNasiDSelect.getSubTotalPrice();

            keranjangItem.add(new Keranjang(namaKategori, namaPackage, perPorsi, kuantitas, subTotal, R.drawable.paket_nasi_d));
        }

        if (PackageSnackASelect.getNamaPackage() != null && !PackageSnackASelect.getNamaPackage().isEmpty()) {
            String namaKategori = PackageSnackASelect.getNamaKategori();
            String namaPackage = PackageSnackASelect.getNamaPackage();
            int perPorsi = PackageSnackASelect.getFinalPrice();
            int kuantitas = PackageSnackASelect.getKuantitas();
            int subTotal = PackageSnackASelect.getSubTotalPrice();

            keranjangItem.add(new Keranjang(namaKategori, namaPackage, perPorsi, kuantitas, subTotal, R.drawable.paket_snack_a));
        }

        if (PackageSnackBSelect.getNamaPackage() != null && !PackageSnackBSelect.getNamaPackage().isEmpty()) {
            String namaKategori = PackageSnackBSelect.getNamaKategori();
            String namaPackage = PackageSnackBSelect.getNamaPackage();
            int perPorsi = PackageSnackBSelect.getFinalPrice();
            int kuantitas = PackageSnackBSelect.getKuantitas();
            int subTotal = PackageSnackBSelect.getSubTotalPrice();

            keranjangItem.add(new Keranjang(namaKategori, namaPackage, perPorsi, kuantitas, subTotal, R.drawable.paket_snack_b));
        }

        if (PackageBuffetASelect.getNamaPackage() != null && !PackageBuffetASelect.getNamaPackage().isEmpty()) {
            String namaKategori = PackageBuffetASelect.getNamaKategori();
            String namaPackage = PackageBuffetASelect.getNamaPackage();
            int perPorsi = PackageBuffetASelect.getFinalPrice();
            int kuantitas = PackageBuffetASelect.getKuantitas();
            int subTotal = PackageBuffetASelect.getSubTotalPrice();

            keranjangItem.add(new Keranjang(namaKategori, namaPackage, perPorsi, kuantitas, subTotal, R.drawable.paket_buffet_a));
        }

        if (PackageBuffetBSelect.getNamaPackage() != null && !PackageBuffetBSelect.getNamaPackage().isEmpty()) {
            String namaKategori = PackageBuffetBSelect.getNamaKategori();
            String namaPackage = PackageBuffetBSelect.getNamaPackage();
            int perPorsi = PackageBuffetBSelect.getFinalPrice();
            int kuantitas = PackageBuffetBSelect.getKuantitas();
            int subTotal = PackageBuffetBSelect.getSubTotalPrice();

            keranjangItem.add(new Keranjang(namaKategori, namaPackage, perPorsi, kuantitas, subTotal, R.drawable.paket_buffet_b));
        }

        if (PackageBuffetCSelect.getNamaPackage() != null && !PackageBuffetCSelect.getNamaPackage().isEmpty()) {
            String namaKategori = PackageBuffetCSelect.getNamaKategori();
            String namaPackage = PackageBuffetCSelect.getNamaPackage();
            int perPorsi = PackageBuffetCSelect.getFinalPrice();
            int kuantitas = PackageBuffetCSelect.getKuantitas();
            int subTotal = PackageBuffetCSelect.getSubTotalPrice();

            keranjangItem.add(new Keranjang(namaKategori, namaPackage, perPorsi, kuantitas, subTotal, R.drawable.paket_buffet_c));
        }

        if (PackagePondokanSelect.getNamaPackage() != null && !PackagePondokanSelect.getNamaPackage().isEmpty()) {
            String namaKategori = PackagePondokanSelect.getNamaKategori();
            String namaPackage = PackagePondokanSelect.getNamaPackage();
            int perPorsi = PackagePondokanSelect.getFinalPrice();
            int kuantitas = PackagePondokanSelect.getKuantitas();
            int subTotal = PackagePondokanSelect.getSubTotalPrice();

            keranjangItem.add(new Keranjang(namaKategori, namaPackage, perPorsi, kuantitas, subTotal, R.drawable.paket_pondokan));
        }

        return keranjangItem;
    }

    public int getTotal(List<Keranjang> keranjangItem) {
        int total = 0;
        for(int i = 0; i < keranjangItem.size(); i++) {
            total += keranjangItem.get(i).getSubTotal();
        }

        return total;
    }


}
