package com.example.tanthinh.local4fun.screens.ScreenFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanthinh.local4fun.R;

import com.example.tanthinh.local4fun.models.Booking;
import com.example.tanthinh.local4fun.models.Post;

import com.example.tanthinh.local4fun.adapters.PostAdapter;
import com.example.tanthinh.local4fun.services.FireBaseAPI;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class BookingScreenFragment extends Fragment implements OnMapReadyCallback{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static RecyclerView recyclerView;
    //private RecyclerView.Adapter mAdapter;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static Context context;
    public static ArrayList<Booking> bookings = new ArrayList<Booking>();
    View v;



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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreScreenFragment.
     */
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



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_booking, container, false);
//        SupportMapFragment mapFrag = (SupportMapFragment)
//                getFragmentManager().
//                        findFragmentById(R.id.map);
//        mapFrag.getMapAsync(BookingScreenFragment.this);


        getBookings();
        /*
        recyclerView = (RecyclerView) v.findViewById(R.id.post_block_id_rec_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PostAdapter(context, bookings);

        recyclerView.setAdapter(mAdapter);
*/
        this.context = getActivity();



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

    @Override
    public void onMapReady(GoogleMap googleMap) {


    }
    public static void getBookings(){
        bookings = new ArrayList<>();
        FireBaseAPI.getBookings("user");
    }

    public static void refreshUI(){
        /*
        mAdapter = new PostAdapter(context, bookings);
        recyclerView.setAdapter(mAdapter);
        */
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
