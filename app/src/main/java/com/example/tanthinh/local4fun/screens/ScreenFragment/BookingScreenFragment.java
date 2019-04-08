package com.example.tanthinh.local4fun.screens.ScreenFragment;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tanthinh.local4fun.R;

import com.example.tanthinh.local4fun.adapters.BookingAdapter;
import com.example.tanthinh.local4fun.intefaces.OnDataReceiveCallback;
import com.example.tanthinh.local4fun.models.Booking;
import com.example.tanthinh.local4fun.models.Post;

import com.example.tanthinh.local4fun.adapters.PostAdapter;
import com.example.tanthinh.local4fun.models.Review;
import com.example.tanthinh.local4fun.models.Singleton;
import com.example.tanthinh.local4fun.models.User;
import com.example.tanthinh.local4fun.screens.PostDetailsScreen;
import com.example.tanthinh.local4fun.services.FireBaseAPI;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingScreenFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static RecyclerView recyclerView;
    private static BookingScreenFragment currentBooking;


    private static RecyclerView.Adapter mAdapter;
    private static RecyclerView.LayoutManager layoutManager;
    private static Context context;
    public static ArrayList<Booking> bookings = new ArrayList<Booking>();
    View v;

    public static Map<String,User> usersOnPost= new HashMap<>();

    public static ArrayList<Post> posts = new ArrayList<Post>();

    GoogleMap mMap;
    Marker marker;
    String location;
    LatLng ll;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


//    private OnFragmentInteractionListener mListener;

    public BookingScreenFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BookingScreenFragment newInstance(String param1, String param2) {
        BookingScreenFragment fragment = new BookingScreenFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        getBookings();
        this.context = getActivity();
        currentBooking = this;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_booking, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.post_block_id_rec_view);

        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

    public static void getBookings(){
        bookings = new ArrayList<>();
        FireBaseAPI.getBookings(Singleton.getInstance().loginUser.getId());
    }

    public static void refreshUI(){

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

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

                for (int i = 0; i < posts.size(); i++) {
                    String userId = posts.get(i).getUserId();
                    Log.w("string userId", userId + "");

                    for (int j = 0; j < list.size(); j++) {
                        Log.e("list: ", list.get(j).getId() + "");
                        if (userId.equals(list.get(j).getId())) {
                            usersOnPost.put(posts.get(i).getId(), list.get(j));
                        }
                    }
                }

                Log.w("After booking bookings", bookings.size() + "");
                Log.w("After bookings posts", posts.size()+"");
                Log.w("After bookings users", usersOnPost.size()+"");
                currentBooking.createBookingUI();
            }

        });
    }

    public static void getPosts(){

        posts = new ArrayList<>();
        FireBaseAPI.getPostsBooking();
    }

    private void createBookingUI()
    {
        mAdapter = new BookingAdapter(bookings,posts,usersOnPost);
        recyclerView.setAdapter(mAdapter);

        ((BookingAdapter) mAdapter).setOnItemClickListener(new BookingAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // Log.i(LOG_TAG, " Clicked on Item " + position);
                Toast.makeText(context, "You clicked on post " + position, Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                String postString = gson.toJson(posts.get(position), Post.class);
                String userString = gson.toJson(usersOnPost.get(posts.get(position).getId()), User.class);

                String location = posts.get(position).getLocation();

                if (location == null || location.equals("")) {
                    Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT).show();

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
                    pdsIntent.putExtra("showBookingButton", false);
                    pdsIntent.putExtras(args);
                    startActivity(pdsIntent);
                }
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
