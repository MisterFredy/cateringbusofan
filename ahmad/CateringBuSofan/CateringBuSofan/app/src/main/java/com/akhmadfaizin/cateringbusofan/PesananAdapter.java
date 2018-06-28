package com.akhmadfaizin.cateringbusofan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PesananAdapter extends BaseAdapter {
    private String TAG = PesananAdapter.class.getSimpleName();
    Activity activity;
    List<Pesanan> users;
    LayoutInflater inflater;

    public PesananAdapter(Activity activity, List<Pesanan> users) {
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

            view = inflater.inflate(R.layout.lv_pesanan_item, viewGroup, false);

            holder = new ViewHolder();

            holder.tvKodeTransaksi = view.findViewById(R.id.tv_kodetransaksi);
            holder.tvFullName = view.findViewById(R.id.tv_fullname);
            holder.tvTanggalPemesanan = view.findViewById(R.id.tv_tanggalPemesanan);
            holder.tvApproval = view.findViewById(R.id.tv_approval);
            holder.ivPhoto = view.findViewById(R.id.iv_photo);
            holder.rlPopUpMenu = view.findViewById(R.id.rl_popUpMenu);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        final Pesanan model = users.get(i);

        holder.tvKodeTransaksi.setText(model.getKodeTransaksi());
        holder.tvFullName.setText(model.getFullName());
        holder.tvTanggalPemesanan.setText(getTanggalIndonesiaFormat(model.getTanggalPemesanan()));
        holder.tvApproval.setText(model.getApproval());
        holder.tvApproval.setTextColor(Color.parseColor("#D0CACA"));
        String urlfoto = activity.getString(R.string.base_url) + "profile/" + model.getUrlPhoto();
        Picasso.with(activity)
                .load(urlfoto)
                .resize(60, 60)
                .into(holder.ivPhoto);

        // Penyesuaian PopUp Menu
        if(model.getApproval().equals("Belum Disetujui")) {
            // Menu Popup ada tiga
            holder.rlPopUpMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.getMenuInflater().inflate(R.menu.pesanan_tiga_poupup_menu, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            // Jika terpilih Detail
                            if(menuItem.getItemId() == R.id.detail) {
                                Intent i = new Intent(activity, DetailPemesananActivity.class);
                                i.putExtra("kodeTransaksi", model.getKodeTransaksi());
                                activity.startActivity(i);
                            }

                            // Jika terpilih Bukti Pembayaran
                            if(menuItem.getItemId() == R.id.bukti) {
                                Intent i = new Intent(activity, BuktiPembayaranActivity.class);
                                i.putExtra("kodeTransaksi", model.getKodeTransaksi());
                                i.putExtra("totalBayar", model.getTotalBayar());
                                i.putExtra("dp", model.getDp());
                                activity.startActivity(i);
                            }

                            // Jika terpilih Batal
                            if(menuItem.getItemId() == R.id.batal) {
                                // Build an AlertDialog
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                                // Set a title for Alert Dialog
                                builder.setTitle("Konfirmasi");

                                // Ask the final question
                                builder.setMessage("Batalkan Pemesanan dengan dengan kode pemesanan " + model.getKodeTransaksi() + " ?");

                                // Set the alert dialog yes button click listener
                                builder.setPositiveButton("Batalkan Pemesanan", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(activity, DeletePemesananActivity.class);
                                        i.putExtra("kodeTransaksi", model.getKodeTransaksi());
                                        activity.startActivity(i);
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
        } else if(model.getApproval().equals("Sudah Disetujui")) {
            holder.tvApproval.setTextColor(Color.parseColor("#32FF84"));
            // Menu PopUp ada Dua
            holder.rlPopUpMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.getMenuInflater().inflate(R.menu.pesanan_dua_poupup_menu, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            // Jika terpilih Detail
                            if(menuItem.getItemId() == R.id.detail) {
                                Intent i = new Intent(activity, DetailPemesananActivity.class);
                                i.putExtra("kodeTransaksi", model.getKodeTransaksi());
                                activity.startActivity(i);
                            }

                            // Jika terpilih Bukti Pembayaran
                            if(menuItem.getItemId() == R.id.bukti) {
                                Intent i = new Intent(activity, BuktiPembayaranActivity.class);
                                i.putExtra("kodeTransaksi", model.getKodeTransaksi());
                                i.putExtra("totalBayar", model.getTotalBayar());
                                i.putExtra("dp", model.getDp());
                                activity.startActivity(i);
                            }

                            return true;
                        }
                    });
                    popup.show();
                }
            });
        }



        return view;

    }

    class ViewHolder{
        TextView tvKodeTransaksi;
        TextView tvFullName;
        TextView tvTanggalPemesanan;
        TextView tvApproval;
        ImageView ivPhoto;
        RelativeLayout rlPopUpMenu;
    }

    public String getTanggalIndonesiaFormat(String s) {
        // Split Tanggal, Bulan, Tahun
        String[] jsonDateSplit = s.split("-");
        int jsonDateDay = Integer.valueOf(jsonDateSplit[0]);
        int jsonDateMonth = Integer.valueOf(jsonDateSplit[1]) - 1;
        int jsonDateYear = Integer.valueOf(jsonDateSplit[2]);
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.YEAR, jsonDateYear);
        myCalendar.set(Calendar.MONTH, jsonDateMonth);
        myCalendar.set(Calendar.DAY_OF_MONTH, jsonDateDay);

        String viewFormat = "dd MMMM yyyy";
        Locale id = new Locale("in", "ID");
        SimpleDateFormat sdfView = new SimpleDateFormat(viewFormat, id);

        String dateIndonesiaFormat = sdfView.format(myCalendar.getTime());

        return dateIndonesiaFormat;
    }
}
