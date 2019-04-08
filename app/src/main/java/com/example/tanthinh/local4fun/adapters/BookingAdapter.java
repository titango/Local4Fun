package com.example.tanthinh.local4fun.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Booking;
import com.example.tanthinh.local4fun.models.Post;
import com.example.tanthinh.local4fun.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {

    public List<Booking> bookings = new ArrayList<Booking>();
    private ArrayList<Post> bookedPost;
    private Map<String,User> bookedUser;

    private static BookingAdapter.MyClickListener myClickListener;

    public BookingAdapter(List<Booking> booking, ArrayList<Post> bookedPost, Map bookedUser)
    {
        this.bookings = booking;
        this.bookedPost = bookedPost;
        this.bookedUser = bookedUser;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView postTitle;
        public TextView postDate;
        public TextView postTime;
        public TextView numPeople;
        public TextView postPrice;
        public TextView postBy;

        public MyViewHolder(View view) {
            super(view);

            postTitle = view.findViewById(R.id.post_title);
            postDate = view.findViewById(R.id.post_date);
            numPeople = view.findViewById(R.id.num_people);
            postPrice = view.findViewById(R.id.price);
            postBy = view.findViewById(R.id.post_by);
            postTime = view.findViewById(R.id.post_time);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myClickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(BookingAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
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
        Post bp = null;
        User bu = null;

        boolean foundPost = false;

        if(!foundPost)
        {
            for(int j = 0; j < bookedPost.size(); j++)
            {
                if(bookedPost.get(j).getId().equals(bo.getPostId()))
                {
                    bp = bookedPost.get(j);
                    foundPost = true;
                    break;
                }
            }
        }

        if(foundPost)
        {
            bu = bookedUser.get(bp.getId());
        }


        holder.postTitle.setText(bo.getPostId().toString());
        holder.postDate.setText(bo.getDate());
        holder.numPeople.setText(bo.getNumberOfPeople() + "");
        holder.postPrice.setText(" "+bo.getTotalPrice()+"");
        holder.postTime.setText(" "+bo.getTime()+"");

        if(this.bookedPost != null)
        {
            holder.postTitle.setText(bp.getTitle().toString());
            holder.postBy.setText(bu.getFullname());
        }
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
