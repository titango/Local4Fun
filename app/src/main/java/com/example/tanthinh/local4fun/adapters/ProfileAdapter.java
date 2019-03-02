package com.example.tanthinh.local4fun.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;

public class ProfileAdapter extends BaseAdapter {

    private String[] list;
    private int[] list_icon;
    private Context mContext;

    ProfileAdapter(){

    }

    public ProfileAdapter(Context mContext, String[] list, int[] list_icon){
        this.mContext = mContext;
        this.list = list;
        this.list_icon = list_icon;
    }

    @Override
    public int getCount() {
        return list_icon.length;
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
       View v = view;
       if(v == null){
           LayoutInflater vi = LayoutInflater.from(mContext);
           v = vi.inflate(R.layout.custom_profile_row, null);
       }

       TextView textView = (TextView)v.findViewById(R.id.txtSetting);
       textView.setText(list[i]);

       ImageView img = (ImageView)v.findViewById(R.id.img_icon);
       img.setImageResource(list_icon[i]);

       return v;
    }
}
