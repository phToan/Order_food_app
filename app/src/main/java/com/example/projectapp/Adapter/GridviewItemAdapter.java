package com.example.projectapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectapp.ObjectClass.item_gridview;
import com.example.projectapp.R;

import java.util.List;

public class GridviewItemAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<item_gridview> arraylist;

    public GridviewItemAdapter(Context context, int layout, List<item_gridview> arraylist) {
        this.context = context;
        this.layout = layout;
        this.arraylist = arraylist;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    private class ViewHolder {
        TextView text;
        ImageView img ;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder =new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.tv_nameitem);
            viewHolder.img = (ImageView) view.findViewById(R.id.imageGridview);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.text.setText(arraylist.get(i).getTitle());
        viewHolder.img.setImageResource(arraylist.get(i).getImage());
        return view;
    }
}
