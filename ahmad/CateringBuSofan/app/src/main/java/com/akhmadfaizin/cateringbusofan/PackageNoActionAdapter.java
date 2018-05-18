package com.akhmadfaizin.cateringbusofan;

/**
 * Created by root on 5/2/18.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PackageNoActionAdapter extends BaseAdapter {
    Activity activity;
    List<PackageChoice> users;
    LayoutInflater inflater;

    public PackageNoActionAdapter(Activity activity) {
        this.activity = activity;
    }

    public PackageNoActionAdapter(Activity activity, List<PackageChoice> users) {
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

            view = inflater.inflate(R.layout.lv_package_item, viewGroup, false);

            holder = new ViewHolder();

            holder.tvChoiceName = (TextView)view.findViewById(R.id.tv_choice_name);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        PackageChoice model = users.get(i);

        holder.tvChoiceName.setText(model.getNama());

        return view;

    }

    public void updateRecords(List<PackageChoice>  users){
        this.users = users;
        notifyDataSetChanged();
    }

    class ViewHolder{

        TextView tvChoiceName;
    }
}
