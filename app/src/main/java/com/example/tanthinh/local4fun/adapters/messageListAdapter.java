package com.example.tanthinh.local4fun.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Singleton;
import com.example.tanthinh.local4fun.models.User;
import com.example.tanthinh.local4fun.screens.MessageDetail;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class messageListAdapter extends BaseAdapter {
    private Context mContext;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private List<User> list  = new ArrayList<User>();
    private ArrayList<DataSnapshot> mSnapshotList;
    private Singleton singleton;
    private String currentName;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            User user = new User();
            user = dataSnapshot.getValue(User.class);
            if (user.getEmail()!= null){
                if (user.getEmail().equals(singleton.loginUser.getEmail())){
                    currentName = user.getFullname();
                }else {
                    mSnapshotList.add(dataSnapshot);
                }
            }
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    public messageListAdapter(Context c) {
        mContext = c;
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        mFirebaseDatabase.addChildEventListener(mListener);
        mSnapshotList = new ArrayList<>();
        singleton = Singleton.initInstance();
    }

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataSnapshot snapshot = mSnapshotList.get(position);
        User user = new User();
        user = snapshot.getValue(User.class);

        LinearLayout.LayoutParams lp;

        View v = convertView;
        if(v == null){
            LayoutInflater vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.custom_message_row, null);
        }

        TextView tv = (TextView)v.findViewById(R.id.txtSetting);

        CircleImageView img = (CircleImageView) v.findViewById(R.id.profile_image);
        if(user.getImgUrl() != null && !user.getImgUrl().equals("")){

            Picasso.get().invalidate(user.getImgUrl());
            Picasso.get().load(user.getImgUrl())
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .fit()
                    .centerCrop()
                    .into(img);
        }
//        if (convertView == null) {
//            tv = new TextView(mContext);
//            lp = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    100);
//            tv.setLayoutParams(lp);
//        }
//        else
//        {
//            tv = (TextView) convertView;
//        }
        tv.setText(user.getFullname());
        tv.setTextSize(22);
//        tv.setGravity(Gravity.CENTER | Gravity.LEFT);
//        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_account_circle_black_24dp, 0, 0, 0);
        final String name = user.getFullname();




        //currentName = singleton.loginUser.getFullname();
        final String imgUrl = singleton.initInstance().loginUser.getImgUrl();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageDetail.class);
                intent.putExtra("SenderName", currentName);
                intent.putExtra("ReceiverName", name);
                intent.putExtra("ImgUrl", imgUrl);
                mContext.startActivity(intent);
            }
        });
        return v;
    }
}
