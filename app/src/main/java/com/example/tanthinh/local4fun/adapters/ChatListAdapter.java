package com.example.tanthinh.local4fun.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.InstantMessage;
import com.example.tanthinh.local4fun.models.Singleton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mSenderName ;
    private String mReceiverName ;
    private ArrayList<DataSnapshot> mSnapshotList;
    private Singleton singleton;
    private String imgUrl;


    public ChatListAdapter(Activity activity, DatabaseReference ref, String sender, String receiver, String imgUrl) {

        mActivity = activity;
        mSenderName = sender;
        mReceiverName = receiver;
        imgUrl = imgUrl;
        // common error: typo in the db location. Needs to match what's in MainChatActivity.
        mDatabaseReference = ref.child("messages");
        mDatabaseReference.addChildEventListener(mListener);

        mSnapshotList = new ArrayList<>();
    }
    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            InstantMessage message = dataSnapshot.getValue(InstantMessage.class);
            if(message.getreceiver()!= null && message.getSender()!= null){
                if (message.getreceiver().equals(mReceiverName) && message.getSender().equals(mSenderName) ){
                    mSnapshotList.add(dataSnapshot);
                }
                if (message.getreceiver().equals(mSenderName) && message.getSender().equals(mReceiverName) ){
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
    private static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout group_holder;
        LinearLayout.LayoutParams params;
        private CircleImageView imgPath,imgViewImage;
    }

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public InstantMessage getItem(int position) {

        DataSnapshot snapshot = mSnapshotList.get(position);
        return snapshot.getValue(InstantMessage.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_msg_row, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.group_holder = (LinearLayout) convertView.findViewById(R.id.group_holder);
            holder.authorName = (TextView) convertView.findViewById(R.id.author);
            holder.body = (TextView) convertView.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            holder.imgViewImage = (CircleImageView) convertView.findViewById(R.id.profile_image);


            convertView.setTag(holder);

        }

        final InstantMessage message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        boolean isMe = message.getSender().equals(mSenderName);

        imgUrl = message.getImgPath();

        if(imgUrl != "") {
            Picasso.get().load(imgUrl)
                    .fit()
                    .centerCrop()
                    .into(holder.imgViewImage);
        }

        setChatRowAppearance(isMe, holder);

        String author = message.getSender();
        holder.authorName.setText(author);

        String msg = message.getMessage();
        holder.body.setText(msg);


        return convertView;
    }

    private void setChatRowAppearance(boolean isItMe, ViewHolder holder) {

        if (isItMe) {


            holder.params.gravity = Gravity.END;
            holder.authorName.setTextColor(Color.GREEN);

            // If you want to use colours from colors.xml
            // int colourAsARGB = ContextCompat.getColor(mActivity.getApplicationContext(), R.color.yellow);
            // holder.authorName.setTextColor(colourAsARGB);

            holder.body.setBackgroundResource(R.drawable.bubble2);
            holder.params.gravity = Gravity.END;


        } else {
            holder.params.gravity = Gravity.START;
            holder.authorName.setTextColor(Color.BLUE);
            holder.body.setBackgroundResource(R.drawable.bubble1);
        }

        holder.authorName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);
        holder.group_holder.setLayoutParams(holder.params);
    }


    public void cleanup() {

        mDatabaseReference.removeEventListener(mListener);
    }


}

