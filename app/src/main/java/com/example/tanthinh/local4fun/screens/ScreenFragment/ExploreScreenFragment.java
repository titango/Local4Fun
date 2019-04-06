package com.example.tanthinh.local4fun.screens.ScreenFragment;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.adapters.PostAdapter;
import com.example.tanthinh.local4fun.intefaces.OnDataReceiveCallback;
import com.example.tanthinh.local4fun.models.Post;
import com.example.tanthinh.local4fun.models.Review;
import com.example.tanthinh.local4fun.models.User;
import com.example.tanthinh.local4fun.screens.BookingDetailsScreen;
import com.example.tanthinh.local4fun.screens.CreateNewPostScreen;

import com.example.tanthinh.local4fun.screens.PostDetailsScreen;

import com.example.tanthinh.local4fun.services.FireBaseAPI;
import com.google.android.gms.maps.model.LatLng;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static Context context;
    private static ExploreScreenFragment currentExplore;
    public static ArrayList<Post> posts = new ArrayList<Post>();
    View v;
    Post p;

    private static String LOG_TAG = "Explore Screen";

    public static Map<String,User> usersOnPost= new HashMap<>();

    public ExploreScreenFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        currentExplore = this;
        getPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_explore, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.post_block_id_rec_view);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

        posts = new ArrayList<>();
        FireBaseAPI.getPosts();

    }
    public static void refreshUI(){
        recyclerView.setHasFixedSize(true);
//        mAdapter = new PostAdapter(context, posts);
//        recyclerView.setAdapter(mAdapter);

        FireBaseAPI.getAllUsers(new OnDataReceiveCallback() {
            @Override
            public void onReviewReceived(ArrayList<Review> list) {

            }

            @Override
            public void onPostReceived(ArrayList<Post> list) {

            }

            @Override
            public void onUserReceived(ArrayList<User> list) {
                Log.w("onUserRecieved", list.size() + "");

                for(int i = 0; i < posts.size(); i++)
                {
                    String userId = posts.get(i).getUserId();
                    Log.w("string userId", userId + "");

                    for(int j = 0; j < list.size(); j++)
                    {
                        Log.e("list: ", list.get(j).getId() + "");
                        if(userId.equals(list.get(j).getId()))
                        {
                            usersOnPost.put(posts.get(i).getId(), list.get(j));
                        }
                    }
                }

                currentExplore.createPostUI();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void createPostUI()
    {
//        recyclerView = (RecyclerView)v.findViewById(R.id.post_block_id_rec_view);
//        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PostAdapter(context, posts, usersOnPost);

        recyclerView.setAdapter(mAdapter);

        ((PostAdapter) mAdapter).setOnItemClickListener(new PostAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // Log.i(LOG_TAG, " Clicked on Item " + position);
                Toast.makeText(context, "You clicked on post " + position, Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                String postString = gson.toJson(posts.get(position), Post.class);
                String userString = gson.toJson(usersOnPost.get(posts.get(position).getId()), User.class);

                String location = posts.get(position).getLocation();

                if (location == null || location.equals("")) {
                    //Toast.makeText(this, "Please enter a meeting point", Toast.LENGTH_SHORT).show();

                } else {

                    Geocoder geocoder = new Geocoder(getContext());
                    List<Address> list = null;
                    try {
                        list = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //List<Address> list = geocoder.getFromLocationName(loc, 1);
                    Address add = list.get(0);
                    //String locality = add.getLocality();
                    LatLng ll = new LatLng(add.getLatitude(), add.getLongitude());

                    Bundle args = new Bundle();
                    args.putParcelable("latLon", ll);
                    args.putSerializable("desc", location);

                    Intent pdsIntent = new Intent(getActivity(), PostDetailsScreen.class);
                    pdsIntent.putExtra("postObject", postString);
                    pdsIntent.putExtra("userObject", userString);
                    pdsIntent.putExtras(args);
                    startActivity(pdsIntent);
                }


                //NOT SHOWING BOOKING SCREEN FRAGMENT
//                Fragment fragment = new BookingScreenFragment();
//                loadFragment(fragment);
            }

        });

        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getActivity(), CreateNewPostScreen.class);
                startActivity(intent1);

            }
        });
    }

}
