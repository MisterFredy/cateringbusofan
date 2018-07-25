package com.akhmadfaizin.cateringbusofanowner;

/**
 * Created by root on 5/2/18.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PackageCheckboxAdapter extends BaseAdapter {
    Activity activity;
    List<PackageChoice> users;
    LayoutInflater inflater;

    public PackageCheckboxAdapter(Activity activity) {
        this.activity = activity;
    }

    public PackageCheckboxAdapter(Activity activity, List<PackageChoice> users) {
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
            holder.ivCheckBox = (ImageView) view.findViewById(R.id.iv_check_box);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        PackageChoice model = users.get(i);

        holder.tvChoiceName.setText(model.getNama());

        if (model.isSelected())
            holder.ivCheckBox.setBackgroundResource(R.drawable.boxchecked);

        else
            holder.ivCheckBox.setBackgroundResource(R.drawable.boxcheck);

        return view;

    }

    public void updateRecords(List<PackageChoice>  users){
        this.users = users;
        notifyDataSetChanged();
    }

    class ViewHolder{

        TextView tvChoiceName;
        ImageView ivCheckBox;
    }
}
