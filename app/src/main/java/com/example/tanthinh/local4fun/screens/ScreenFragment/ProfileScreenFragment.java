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
import com.example.tanthinh.local4fun.screens.BecomeHostScreen;
import com.example.tanthinh.local4fun.screens.ChangePasswordScreen;
import com.example.tanthinh.local4fun.screens.EditProfileScreen;
import com.example.tanthinh.local4fun.screens.LoginScreen.LoginActivity;

public class ProfileScreenFragment extends Fragment {


    String[] list = {"Edit Profile", "Become a host", "Contact us", "Change Password", "Sign out"};
    int[] list_icon = {R.drawable.host_black_24dp, R.drawable.host_black_24dp,
            R.drawable.contact_mail_black_24dp, R.drawable.change_password_black_24dp,
            R.drawable.sign_out_black_24dp};

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
                    case 0: //Edit profile
                        Intent edit_profile = new Intent(view.getContext(), EditProfileScreen.class);
                        startActivity(edit_profile);
                        break;
                    case 1: //Become a host
                        Intent become_host = new Intent(view.getContext(), BecomeHostScreen.class);
                        startActivity(become_host);
                        break;
                    case 2: //Contact us
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto","email@email.com", null));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Hi");
                        intent.putExtra(Intent.EXTRA_TEXT, "Hello");
                        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                        break;
                    case 3: //Change Password
                        Intent change_pass = new Intent(view.getContext(), ChangePasswordScreen.class);
                        startActivity(change_pass);
                        break;
                    case 4: //Logout
                        Intent logout = new Intent(view.getContext(), LoginActivity.class);
                        startActivity(logout);
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
