package com.akhmadfaizin.cateringbusofanowner;

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

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BuktiPembayaranAdapter extends BaseAdapter {
    private String TAG = BuktiPembayaranAdapter.class.getSimpleName();
    Activity activity;
    String kodeTransaksi;
    String token;
    int dp;
    List<BuktiPembayaran> users;
    LayoutInflater inflater;

    public BuktiPembayaranAdapter(Activity activity, List<BuktiPembayaran> users, String kodeTransaksi, String token, int dp) {
        this.activity   = activity;
        this.users      = users;
        this.kodeTransaksi = kodeTransaksi;
        this.token      = token;
        this.dp = dp;

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

            view = inflater.inflate(R.layout.lv_bukti_pembayaran_item, viewGroup, false);

            holder = new ViewHolder();

            holder.tvNominal = view.findViewById(R.id.tv_nominal);
            holder.tvTanggal = view.findViewById(R.id.tv_tanggal);
            holder.tvKonfirmasi = view.findViewById(R.id.tv_konfirmasi);
            holder.ivPhoto = view.findViewById(R.id.iv_photo);
            holder.rlPopUpMenu = view.findViewById(R.id.rl_popUpMenu);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        final BuktiPembayaran model = users.get(i);

        holder.tvNominal.setText("Rp " + String.format("%,d", model.getNominal()));
        holder.tvTanggal.setText(getTanggalIndonesiaFormat(model.getTanggal()));
        holder.tvKonfirmasi.setText(model.getKonfirmasi());
        holder.tvKonfirmasi.setTextColor(Color.parseColor("#D0CACA"));
        Picasso.with(activity)
                .load(model.getUrl_img())
                .resize(60, 60)
                .into(holder.ivPhoto);

        // Handling Image Get Clicked
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, FullBuktiImageActivity.class);
                i.putExtra("url_img", model.getUrl_img());
                i.putExtra("nominal", model.getNominal());
                activity.startActivity(i);
            }
        });

        // Penyesuaian PopUp Menu
        if(model.getKonfirmasi().equals("Belum Dicek")) {
            // Menu Popup
            holder.rlPopUpMenu.setVisibility(View.VISIBLE);
            holder.rlPopUpMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.getMenuInflater().inflate(R.menu.bukti_pembayaran_item_poupup_menu, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            // Jika terpilih Gambar Cocok
                            if(menuItem.getItemId() == R.id.action_gambar_cocok) {
                                // Build an AlertDialog
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                                // Set a title for Alert Dialog
                                builder.setTitle("Konfirmasi");

                                // Ask the final question
                                builder.setMessage("Anda Yakin Bukti Pembayaran COCOK Dengan Nominal ?");

                                // Set the alert dialog yes button click listener
                                builder.setPositiveButton("Saya Yakin", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String konfirmasi = "Gambar Cocok Dengan Nominal";
                                        Intent i = new Intent(activity, BuktiPembayaranUpdateKonfirmasiActivity.class);
                                        i.putExtra("kodeTransaksi", kodeTransaksi);
                                        i.putExtra("dp", dp);
                                        i.putExtra("nominal", model.getNominal());
                                        i.putExtra("id", model.getId());
                                        i.putExtra("token", token);
                                        i.putExtra("konfirmasi", konfirmasi);
                                        activity.startActivity(i);
                                        activity.finish();
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

                            // Jika terpilih Tidak Cocok
                            if(menuItem.getItemId() == R.id.action_gambar_tidak_cocok) {
                                // Build an AlertDialog
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                                // Set a title for Alert Dialog
                                builder.setTitle("Konfirmasi");

                                // Ask the final question
                                builder.setMessage("Anda Yakin Bukti Pembayaran TIDAK COCOK Dengan Nominal ?");

                                // Set the alert dialog yes button click listener
                                builder.setPositiveButton("Saya Yakin", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String konfirmasi = "Gambar Tidak Cocok Dengan Nominal";
                                        Intent i = new Intent(activity, BuktiPembayaranUpdateKonfirmasiActivity.class);
                                        i.putExtra("kodeTransaksi", kodeTransaksi);
                                        i.putExtra("dp", dp);
                                        i.putExtra("nominal", model.getNominal());
                                        i.putExtra("id", model.getId());
                                        i.putExtra("token", token);
                                        i.putExtra("konfirmasi", konfirmasi);
                                        activity.startActivity(i);
                                        activity.finish();
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


        } else if(model.getKonfirmasi().equals("Gambar Cocok Dengan Nominal")) {
            holder.tvKonfirmasi.setText("Gambar Cocok \nDengan Nominal");
            holder.tvKonfirmasi.setTextColor(Color.parseColor("#32FF84"));
            holder.rlPopUpMenu.setVisibility(View.GONE);

        } else if(model.getKonfirmasi().equals("Gambar Tidak Cocok Dengan Nominal")) {
            holder.tvKonfirmasi.setText("Gambar Tidak Cocok \nDengan Nominal");
            holder.tvKonfirmasi.setTextColor(Color.parseColor("#1F9999"));
            holder.rlPopUpMenu.setVisibility(View.GONE);
        }

        return view;
    }

    class ViewHolder{
        TextView tvNominal;
        TextView tvTanggal;
        TextView tvKonfirmasi;
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
