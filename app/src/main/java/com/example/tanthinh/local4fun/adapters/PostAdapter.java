package com.example.tanthinh.local4fun.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.tanthinh.local4fun.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private List postList; // Temporary List
    private Context context;
    private ViewPagerAdapter viewPagerAdapter;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ViewPager viewPager; // for slider
        public TextView textView;

        public MyViewHolder(View v) {
            super(v);
            viewPager = v.findViewById(R.id.viewPager);
            textView = v.findViewById(R.id.fakeText);
        }
    }

    public PostAdapter(Context context, List postList)
    {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_block, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String[] imgurl = new String[]{
                "https://cdn.pixabay.com/photo/2014/09/21/17/56/wanderer-455338_1280.jpg",
                "https://cdn.pixabay.com/photo/2014/10/22/18/04/freerider-498473_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/01/06/23/25/snow-3066167_1280.jpg"
        };
        viewPagerAdapter = new ViewPagerAdapter(context, imgurl);
        holder.viewPager.setAdapter(viewPagerAdapter);

        holder.textView.setText((String)postList.get(position) + "");
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
