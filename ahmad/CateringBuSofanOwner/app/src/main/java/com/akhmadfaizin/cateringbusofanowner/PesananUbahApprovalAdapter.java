package com.akhmadfaizin.cateringbusofanowner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PesananUbahApprovalAdapter extends BaseAdapter {
    Activity activity;
    List<PesananUbahApproval> listChoice;
    LayoutInflater inflater;

    public PesananUbahApprovalAdapter(Activity activity,  List<PesananUbahApproval> listChoice) {
        this.activity   = activity;
        this.listChoice      = listChoice;

        inflater        = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return listChoice.size();
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

            view = inflater.inflate(R.layout.lv_approval_item, viewGroup, false);

            holder = new ViewHolder();

            holder.tvChoiceName = (TextView)view.findViewById(R.id.tv_choice_name);
            holder.ivCheckBox = (ImageView) view.findViewById(R.id.iv_check_box);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        PesananUbahApproval model = listChoice.get(i);

        String description = model.getApproval() + "\n" + model.getAlasan();
        holder.tvChoiceName.setText(description);

        if (model.isSelected())
            holder.ivCheckBox.setBackgroundResource(R.drawable.radiochecked);
        else
            holder.ivCheckBox.setBackgroundResource(R.drawable.radiocheck);

        return view;

    }

    public void updateRecords(List<PesananUbahApproval> listChoice){
        this.listChoice = listChoice;
        notifyDataSetChanged();
    }

    class ViewHolder{

        TextView tvChoiceName;
        ImageView ivCheckBox;
    }
}
