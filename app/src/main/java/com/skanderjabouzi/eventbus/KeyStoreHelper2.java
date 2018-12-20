package com.skanderjabouzi.eventbus;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.security.auth.x500.X500Principal;

import static android.content.Context.MODE_PRIVATE;
import static java.security.spec.RSAKeyGenParameterSpec.F4;

public class KeyStoreHelper2 {

    private static final String TAG = "KeyStoreHelper";

    private static final String KEYSTORE_KEY_ALIAS  = "ManulifeMobileKeys";

    private static final Integer KEY_SIZE  = 2048;

    public static Context context;
    /**
     * Creates a public and private key and stores it using the Android Key
     * Store, so that only this application will be able to access the keys.
     */
    private static void createKeys() throws NoSuchProviderException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (!isSigningKey()) {
            createKeys(false);
        }
    }

    private static boolean isSigningKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            keyStore.load(null);
            return keyStore.containsAlias(KEYSTORE_KEY_ALIAS);
        } catch (Exception e) {
            Log.e(TAG + "1 ", e.getMessage(), e);
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void createKeys(boolean requireAuth) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            keyGenerator.init(
                    new KeyGenParameterSpec.Builder(
                            KEYSTORE_KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                            .setKeySize(128)
                            .setUserAuthenticationRequired(requireAuth)
                            .build());
            SecretKey keyPair = keyGenerator.generateKey();
            Log.d(TAG, "Public Key is: " + keyPair.toString());

        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }


    private static KeyStore.SecretKeyEntry getPrivateKeyEntry() {
        try {
            KeyStore ks = KeyStore
                    .getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(KEYSTORE_KEY_ALIAS, null);

            if (entry == null) {
                Log.w(TAG, "No key found under alias: " + KEYSTORE_KEY_ALIAS);
                Log.w(TAG, "Exiting signData()...");
                return null;
            }

            if (!(entry instanceof KeyStore.SecretKeyEntry)) {
                Log.w(TAG, "Not an instance of a PrivateKeyEntry");
                Log.w(TAG, "Exiting signData()...");
                return null;
            }
            return (KeyStore.SecretKeyEntry) entry;
        } catch (Exception e) {
            Log.e(TAG + "2 ", e.getMessage(), e);
            return null;
        }
    }

    private static SecretKey getSecretKey() {
        SecretKey secretKey = getPrivateKeyEntry().getSecretKey();
        return secretKey;
    }

    public static String encrypt(String plaintext) {
        try {
            //make sure keys are created
            createKeys();
            Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            saveIV(Base64.encodeToString(cipher.getIV(), Base64.DEFAULT));
            return Base64.encodeToString(cipher.doFinal(plaintext.getBytes()), Base64.NO_WRAP);
        } catch (Exception e) {
            Log.e(TAG + "3 ",e.getMessage());
            return null;
        }
    }

    public static String decrypt(String ciphertext) {
        try {
            Cipher cipher = getCipher();
            String iv2 = getIV();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.decode(getIV(), Base64.DEFAULT));
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), ivParameterSpec);
            return new String(cipher.doFinal(Base64.decode(ciphertext, Base64.NO_WRAP)));
        } catch (Exception e) {
            if (e.getMessage() != null) Log.e(TAG + "4 ",e.getMessage());
            return null;
        }
    }

    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(
                String.format("%s/%s/%s",
                        SecurityConstants.TYPE,
                        SecurityConstants.BLOCKING_MODE,
                        SecurityConstants.PADDING_TYPE));
    }

    private interface SecurityConstants {
        String KEYSTORE_PROVIDER_ANDROID_KEYSTORE = "AndroidKeyStore";
        String TYPE = "AES";
        String PADDING_TYPE = "PKCS7Padding";
        String BLOCKING_MODE = "CBC";
    }

    private static void saveIV(String iv) {
        SharedPreferences preferences = context.getSharedPreferences("manulifeiv", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("iv", iv);
        editor.commit();
        editor.apply();
        Log.e("IV", iv);
    }

    private static String getIV() {
        SharedPreferences preferences =  context.getSharedPreferences("manulifeiv", MODE_PRIVATE);
        return preferences.getString("iv", "");
    }
}
