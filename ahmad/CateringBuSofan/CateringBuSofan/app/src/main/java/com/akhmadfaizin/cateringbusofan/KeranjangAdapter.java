package com.akhmadfaizin.cateringbusofan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by root on 5/17/18.
 */

public class KeranjangAdapter extends BaseAdapter {
    private String TAG = KeranjangAdapter.class.getSimpleName();
    Activity activity;
    List<Keranjang> users;
    LayoutInflater inflater;

    public KeranjangAdapter(Activity activity) {
        this.activity = activity;
    }

    public KeranjangAdapter(Activity activity, List<Keranjang> users) {
        this.activity   = activity;
        this.users      = users;

        inflater        = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null){

            view = inflater.inflate(R.layout.lv_keranjang_item, viewGroup, false);

            holder = new ViewHolder();

            holder.tvKategori = view.findViewById(R.id.tv_kategori);
            holder.tvPaket = view.findViewById(R.id.tv_paket);
            holder.tvHargaPorsi = view.findViewById(R.id.tv_harga_porsi);
            holder.tvKuantitasPorsi = view.findViewById(R.id.tv_kuantitas_porsi);
            holder.tvSubTotal = view.findViewById(R.id.tv_sub_total);
            holder.rlPopUpMenu = view.findViewById(R.id.rl_popUpMenu);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        final Keranjang model = users.get(i);

        holder.tvKategori.setText(model.getKategori());
        holder.tvPaket.setText(model.getPaket());
        holder.tvHargaPorsi.setText("Rp " + String.format("%,d", model.getPerPorsi()) + " / porsi");
        holder.tvKuantitasPorsi.setText(String.format("%,d", model.getKuantitasPorsi()) + " porsi");
        holder.tvSubTotal.setText("Rp " + String.format("%,d", model.getSubTotal()));

        holder.rlPopUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.getMenuInflater().inflate(R.menu.keranjang_poupup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        // Jika terpilih Edit
                        if(menuItem.getItemId() == R.id.edit) {
                            switch(model.getPaket()) {
                                case "Nasi Box A":
                                    Intent intentNasiA = new Intent(activity, PackageNasiAActivity.class);
                                    intentNasiA.putExtra("NECESSITY", "EDIT");
                                    activity.startActivity(intentNasiA);
                                    break;
                                case "Nasi Box B":
                                    Intent intentNasiB = new Intent(activity, PackageNasiBActivity.class);
                                    intentNasiB.putExtra("NECESSITY", "EDIT");
                                    activity.startActivity(intentNasiB);
                                    break;
                                case "Nasi Box C":
                                    Intent intentNasiC = new Intent(activity, PackageNasiCActivity.class);
                                    intentNasiC.putExtra("NECESSITY", "EDIT");
                                    activity.startActivity(intentNasiC);
                                    break;
                                case "Nasi Box D":
                                    Intent intentNasiD = new Intent(activity, PackageNasiDActivity.class);
                                    intentNasiD.putExtra("NECESSITY", "EDIT");
                                    activity.startActivity(intentNasiD);
                                    break;
                                case "Snack Box A":
                                    Intent intentSnackA = new Intent(activity, PackageSnackAActivity.class);
                                    intentSnackA.putExtra("NECESSITY", "EDIT");
                                    activity.startActivity(intentSnackA);
                                    break;
                                case "Snack Box B":
                                    Intent intentSnackB = new Intent(activity, PackageSnackBActivity.class);
                                    intentSnackB.putExtra("NECESSITY", "EDIT");
                                    activity.startActivity(intentSnackB);
                                    break;
                                case "Buffet A":
                                    Intent intentBuffetA = new Intent(activity, PackageBuffetAActivity.class);
                                    intentBuffetA.putExtra("NECESSITY", "EDIT");
                                    activity.startActivity(intentBuffetA);
                                    break;
                                case "Buffet B":
                                    Intent intentBuffetB = new Intent(activity, PackageBuffetBActivity.class);
                                    intentBuffetB.putExtra("NECESSITY", "EDIT");
                                    activity.startActivity(intentBuffetB);
                                    break;
                                case "Buffet C":
                                    Intent intentBuffetC = new Intent(activity, PackageBuffetCActivity.class);
                                    intentBuffetC.putExtra("NECESSITY", "EDIT");
                                    activity.startActivity(intentBuffetC);
                                    break;
                                case "Pondokan":
                                    Intent intentPondokan = new Intent(activity, PackagePondokanActivity.class);
                                    intentPondokan.putExtra("NECESSITY", "EDIT");
                                    activity.startActivity(intentPondokan);
                                    break;
                            }
                        }

                        // Jika terpilih Delete
                        if(menuItem.getItemId() == R.id.delete) {
                            // Build an AlertDialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                            // Set a title for Alert Dialog
                            builder.setTitle("Konfirmasi");

                            // Ask the final question
                            builder.setMessage("Anda Yakin Ingin Menghapus " + model.getPaket() +" ?");

                            // Set the alert dialog yes button click listener
                            builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch(model.getPaket()) {
                                        case "Nasi Box A":
                                            PackageNasiASelect.reset();
                                            break;
                                        case "Nasi Box B":
                                            PackageNasiBSelect.reset();
                                            break;
                                        case "Nasi Box C":
                                            PackageNasiCSelect.reset();
                                            break;
                                        case "Nasi Box D":
                                            PackageNasiDSelect.reset();
                                            break;
                                        case "Snack Box A":
                                            PackageSnackASelect.reset();
                                            break;
                                        case "Snack Box B":
                                            PackageSnackBSelect.reset();
                                            break;
                                        case "Buffet A":
                                            PackageBuffetASelect.reset();
                                            break;
                                        case "Buffet B":
                                            PackageBuffetBSelect.reset();
                                            break;
                                        case "Buffet C":
                                            PackageBuffetCSelect.reset();
                                            break;
                                        case "Pondokan":
                                            PackagePondokanSelect.reset();
                                            break;
                                    }

                                    // Refresh Keranjang Activity
                                    activity.finish();
                                    activity.startActivity(activity.getIntent());
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

                        return true;
                    }
                });
                popup.show();
            }
        });

        return view;

    }

    class ViewHolder{

        TextView tvKategori;
        TextView tvPaket;
        TextView tvHargaPorsi;
        TextView tvKuantitasPorsi;
        TextView tvSubTotal;
        RelativeLayout rlPopUpMenu;
    }
}
