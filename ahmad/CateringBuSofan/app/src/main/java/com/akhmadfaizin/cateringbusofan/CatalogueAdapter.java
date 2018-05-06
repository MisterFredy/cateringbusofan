package com.akhmadfaizin.cateringbusofan;

/**
 * Created by root on 4/25/18.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CatalogueAdapter extends BaseAdapter {
    ArrayList<Object> list;
    private static final int CATALOGUE_ITEM = 0;
    private static final int HEADER = 1;
    private LayoutInflater inflater;

    public CatalogueAdapter(Context context, ArrayList<Object> list) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof Catalogue) {
            return CATALOGUE_ITEM;
        }
        else {
            return HEADER;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            switch (getItemViewType(i)) {
                case CATALOGUE_ITEM:
                    view = inflater.inflate(R.layout.lv_catalogue_item, null);
                    break;
                case HEADER:
                    view = inflater.inflate(R.layout.lv_catalogue_header, null);
            }
        }

        switch(getItemViewType(i)) {
            case CATALOGUE_ITEM:
                ImageView image = (ImageView) view.findViewById(R.id.lv_catalogue_item_img);
                TextView name = (TextView) view.findViewById(R.id.lv_catalogue_item_name);

                image.setImageResource(((Catalogue)list.get(i)).getImage());
                name.setText(((Catalogue)list.get(i)).getName());
                break;
            case HEADER:
                TextView title = view.findViewById(R.id.lv_catalogue_header);
                title.setText(((String)list.get(i)));
                break;
        }

        return view;
    }
}
