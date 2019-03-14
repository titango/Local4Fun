package com.example.tanthinh.local4fun.screens;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.screens.ScreenFragment.BookingScreenFragment;
import com.example.tanthinh.local4fun.screens.ScreenFragment.ExploreScreenFragment;
import com.example.tanthinh.local4fun.screens.ScreenFragment.MessageScreenFragment;
import com.example.tanthinh.local4fun.screens.ScreenFragment.ProfileScreenFragment;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

//    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        toolbar.setTitle("ExploreScreen");
        loadFragment(new ExploreScreenFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.explore_screen_fragment:
//                    toolbar.setTitle("ExploreScreen");
                    fragment = new ExploreScreenFragment();
                    loadFragment(fragment);
                return true;
                case R.id.booking_screen_fragment:
//                    toolbar.setTitle("BookingScreen");
                    fragment = new BookingScreenFragment();
                    loadFragment(fragment);
                return true;
                case R.id.message_screen_fragment:
//                    toolbar.setTitle("MessageScreen");
                    fragment = new MessageScreenFragment();
                    loadFragment(fragment);
                return true;
                case R.id.profile_screen_fragment:
                    loadProfileScreenFragment();
                return true;
            }
            return false;
        }

    };


    private void loadProfileScreenFragment(){
        Fragment fragment = new ProfileScreenFragment();
        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

}
