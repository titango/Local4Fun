package com.example.tanthinh.local4fun.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
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

import java.util.ArrayList;
import java.util.List;

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
            mSnapshotList.add(dataSnapshot);
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

        TextView tv ;
        LinearLayout.LayoutParams lp;
        if (convertView == null) {
            tv = new TextView(mContext);
            lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    100);
            tv.setLayoutParams(lp);
        }
        else
        {
            tv = (TextView) convertView;
        }
        tv.setText(user.getFullname());
        tv.setTextSize(22);
        tv.setGravity(Gravity.CENTER | Gravity.LEFT);
        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_account_circle_black_24dp, 0, 0, 0);
        final String name = user.getFullname();

        currentName = singleton.loginUser.getFullname();

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageDetail.class);
                intent.putExtra("userName", currentName);
                mContext.startActivity(intent);
            }
        });
        return tv;
    }
}
