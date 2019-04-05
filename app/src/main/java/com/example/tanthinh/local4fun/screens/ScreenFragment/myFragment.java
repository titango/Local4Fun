package com.example.tanthinh.local4fun.screens.ScreenFragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.FingerprintHelper;
import com.example.tanthinh.local4fun.screens.MainActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

public class myFragment extends DialogFragment implements FingerprintHelper.Callback {

    public static final int RECOVERABLE_ERROR = 843;
    public static final int NON_RECOVERABLE_ERROR = 566;
    public static final int CANNOT_RECOGNIZE_ERROR = 456;
    private TextView mAuthMsgTv;
    private ViewSwitcher mSwitcher;
    private FingerprintHelper mFingerPrintAuthHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.purchase_title));
        View v = inflater.inflate(R.layout.dialogcontainter, container, false);

        mFingerPrintAuthHelper = mFingerPrintAuthHelper.getHelper(v.getContext(), this);
        mSwitcher = (ViewSwitcher) v.findViewById(R.id.main_switcher);
        mAuthMsgTv = (TextView) v.findViewById(R.id.auth_message_tv);
        EditText pinEt = (EditText) v.findViewById(R.id.pin_et);
        pinEt.addTextChangedListener(new textChangeListener());

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        mAuthMsgTv.setText("Scan your finger");
        mFingerPrintAuthHelper.startAuth();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFingerPrintAuthHelper.stopAuth();
    }

    private class textChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().equals("1234")){
                Toast.makeText(getContext(), "Authentication succeeded.",
                        Toast.LENGTH_SHORT).show();
                Intent payIntent = new Intent(getApplicationContext(), MainActivity.class);
                payIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(payIntent);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        mAuthMsgTv.setText("Your device does not have finger print scanner." +
                " Please type 1234 to authenticate.");
        mSwitcher.showNext();
    }

    @Override
    public void onNoFingerPrintRegistered() {
        mAuthMsgTv.setText("There are no finger prints registered on this device. " +
                "Please register your finger from settings.");
    }

    @Override
    public void onBelowMarshmallow() {
        mAuthMsgTv.setText("You are running older version of android that " +
                "does not support finger print authentication." +
                " Please type 1234 to authenticate.");
        mSwitcher.showNext();
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Toast.makeText(getContext(), "Authentication succeeded.", Toast.LENGTH_SHORT).show();
        Intent payIntent = new Intent(getApplicationContext(), MainActivity.class);
        payIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(payIntent);
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {
            case CANNOT_RECOGNIZE_ERROR:
                mAuthMsgTv.setText("Cannot recognize your finger print. Please try again.");
                break;
            case NON_RECOVERABLE_ERROR:
                mAuthMsgTv.setText("Cannot initialize finger print authentication." +
                        " Please type 1234 to authenticate.");
                mSwitcher.showNext();
                break;
            case RECOVERABLE_ERROR:
                mAuthMsgTv.setText(errorMessage);
                break;
        }
    }
}
