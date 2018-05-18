package com.akhmadfaizin.cateringbusofan;

import android.app.Activity;
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
                        String message = model.getPaket() + " - " + menuItem.getTitle();
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
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
