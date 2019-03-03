package com.example.tanthinh.local4fun.screens.ScreenFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.adapters.PostAdapter;

import com.example.tanthinh.local4fun.models.Post;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class ExploreScreenFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private ArrayList<Post> posts = new ArrayList<Post>();
    View v;

//    private OnFragmentInteractionListener mListener;

    public ExploreScreenFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.explore_fragment, container, false);

        getPosts();

        this.context = getActivity();

        // Inflate the layout for this fragment
        return v;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    public void getPosts(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        Query phoneQuery = myRef.child("posts");
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    Post p = new Post(singleSnapshot.child("userId").getValue().toString(),
                            singleSnapshot.child("title").getValue().toString(),
                            singleSnapshot.child("description").getValue().toString(),
                            Double.parseDouble(singleSnapshot.child("hours").getValue().toString()),
                            Double.parseDouble(singleSnapshot.child("pricePerPerson").getValue().toString()));

                    for(DataSnapshot picSnapshot : singleSnapshot.child("pictures/addresses").getChildren()){
                        p.addPicture(picSnapshot.getValue().toString());
                    }
                    posts.add(p);
                }

                recyclerView = (RecyclerView)v.findViewById(R.id.post_block_id_rec_view);
                recyclerView.setHasFixedSize(true);

                layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);

                mAdapter = new PostAdapter(context, posts);
                recyclerView.setAdapter(mAdapter);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

    }


//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }
}
