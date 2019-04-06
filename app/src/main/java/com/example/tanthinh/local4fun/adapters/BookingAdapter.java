package com.example.tanthinh.local4fun.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {

    public List<Booking> bookings = new ArrayList<Booking>();

    public BookingAdapter(List<Booking> booking)
    {
        this.bookings = booking;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView postTitle;
        public TextView postDate;
        public TextView numPeople;
        public TextView postPrice;

        public MyViewHolder(View view) {
            super(view);

            postTitle = view.findViewById(R.id.post_title);
            postDate = view.findViewById(R.id.post_date);
            numPeople = view.findViewById(R.id.num_people);
            postPrice = view.findViewById(R.id.price);
        }

        @Override
        public void onClick(View view) {

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_block, parent, false);

        BookingAdapter.MyViewHolder vh = new BookingAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Booking bo = bookings.get(position);
        holder.postTitle.setText(bo.getPostId().toString());
        holder.postDate.setText(bo.getDate());
        holder.numPeople.setText(bo.getNumberOfPeople() + "");
        holder.postPrice.setText(bo.getTotalPrice()+"");
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
