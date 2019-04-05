package com.example.tanthinh.local4fun.models;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class FingerprintHelper {
    public static final int RECOVERABLE_ERROR = 843;
    public static final int NON_RECOVERABLE_ERROR = 566;
    public static final int CANNOT_RECOGNIZE_ERROR = 456;
    private static final String KEY_NAME = UUID.randomUUID().toString();
    private static final String ERROR_FAILED_TO_GENERATE_KEY =
            "Failed to generate secrete key for authentication.";
    private static final String ERROR_FAILED_TO_INIT_CHIPPER =
            "Failed to generate cipher key for authentication.";

    private KeyStore mKeyStore;
    private Cipher mCipher;
    private Context mContext;
    private Callback mCallback;
    private CancellationSignal mCancellationSignal;
    private boolean isScanning;


    private FingerprintHelper(@NonNull Context context, @NonNull Callback callback) {
        mCallback = callback;
        mContext = context;
    }
    public static FingerprintHelper getHelper(@NonNull Context context,
                                              @NonNull Callback callback) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null.");
        } else if (callback == null) {
            throw new IllegalArgumentException("FingerPrintAuthCallback cannot be null.");
        }

        return new FingerprintHelper(context, callback);
    }

    private boolean checkFingerPrintAvailability(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FingerprintManagerCompat fingerprintManager =
                    FingerprintManagerCompat.from(context);
            if (!fingerprintManager.isHardwareDetected()) {
                mCallback.onNoFingerPrintHardwareFound();
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                mCallback.onNoFingerPrintRegistered();
                return false;
            }
            return true;
        } else {
            mCallback.onBelowMarshmallow();
            return false;
        }
    }

    private boolean generateKey() {
        mKeyStore = null;
        KeyGenerator keyGenerator;

        //Get the instance of the key store.
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            return false;
        } catch (KeyStoreException e) {
            return false;
        }

            //generate key.
        try {
            mKeyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();

            return true;
        } catch (NoSuchAlgorithmException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException e) {
            return false;
        }
    }

    private boolean cipherInit() {
        boolean isKeyGenerated = generateKey();

        if (!isKeyGenerated) {
            mCallback.onAuthFailed(NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_GENERATE_KEY);
            return false;
        }

        try {
            mCipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            mCallback.onAuthFailed(NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_GENERATE_KEY);
            return false;
        }

        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            mCipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            mCallback.onAuthFailed(NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER);
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            mCallback.onAuthFailed(NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER);
            return false;
        }
    }
    private FingerprintManager.CryptoObject getCryptoObject() {
        return cipherInit() ? new FingerprintManager.CryptoObject(mCipher) : null;
    }

    public void startAuth() {
        if (isScanning) stopAuth();

        if (!checkFingerPrintAvailability(mContext)) return;

        FingerprintManager fingerprintManager = (FingerprintManager)
                mContext.getSystemService(Context.FINGERPRINT_SERVICE);

        FingerprintManager.CryptoObject cryptoObject = getCryptoObject();
        if (cryptoObject == null) {
            mCallback.onAuthFailed(NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER);
        } else {
            mCancellationSignal = new CancellationSignal();
            //noinspection MissingPermission
            fingerprintManager.authenticate(cryptoObject,
                    mCancellationSignal,
                    0,
                    new FingerprintManager.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errMsgId, CharSequence errString) {
                            mCallback.onAuthFailed(NON_RECOVERABLE_ERROR, errString.toString());
                        }

                        @Override
                        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                            mCallback.onAuthFailed(RECOVERABLE_ERROR, helpString.toString());
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            mCallback.onAuthFailed(CANNOT_RECOGNIZE_ERROR, null);
                        }

                        @Override
                        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult
                                                                      result) {
                            mCallback.onAuthSuccess(result.getCryptoObject());
                        }
                    }, null);
        }
    }

    public void stopAuth() {
        if (mCancellationSignal != null) {
            isScanning = true;
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }

    public boolean isScanning() {
        return isScanning;
    }

    public interface Callback {
        void onNoFingerPrintHardwareFound();
        void onNoFingerPrintRegistered();
        void onBelowMarshmallow();
        void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject);
        void onAuthFailed(int errorCode, String errorMessage);
    }
}
