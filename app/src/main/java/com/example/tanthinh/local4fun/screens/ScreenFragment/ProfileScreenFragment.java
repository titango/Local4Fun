package com.example.tanthinh.local4fun.screens.ScreenFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.adapters.ProfileAdapter;

public class ProfileScreenFragment extends Fragment {


    String[] list = {"Edit Profile", "Become a host", "Contact us", "Turn Off Notification", "Change Password", "Sign out"};
    int[] list_icon = {R.drawable.ic_card_giftcard_white_24dp, R.drawable.ic_card_giftcard_white_24dp,
            R.drawable.ic_card_giftcard_white_24dp, R.drawable.ic_card_giftcard_white_24dp, R.drawable.ic_card_giftcard_white_24dp,
            R.drawable.ic_card_giftcard_white_24dp};

    public ProfileScreenFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileScreenFragment newInstance(String param1, String param2) {
        ProfileScreenFragment fragment = new ProfileScreenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        ListView listView = (ListView)view.findViewById(R.id.profile_setting);
        ProfileAdapter profileAdapter = new ProfileAdapter(view.getContext(),list,list_icon);
        listView.setAdapter(profileAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                    case 3:
                    case 4:
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto","email@email.com", null));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Hi");
                        intent.putExtra(Intent.EXTRA_TEXT, "Hello");
                        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                        break;
                    default:
                        break;

                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
