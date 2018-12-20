package com.skanderjabouzi.eventbus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;

import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static android.content.Context.MODE_PRIVATE;

public class KeyStoreHelper {

    public static Context context;


    @SuppressLint("NewApi")
    public static void testAES() throws Exception {

        final String suchAlphabet = "abcdefghijklmnopqrstuvwxyz";

        createKeys();
        String e = encrypt(suchAlphabet);
        decrypt(e);
    }

    private static SecretKey getSecretKey() {
        try {
            SecretKey secretKey;
            KeyStore keyStore2 = KeyStore.getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            keyStore2.load(null);
            // Load the secret key and encrypt
            secretKey = ((KeyStore.SecretKeyEntry) keyStore2.getEntry(SecurityConstants.KEYSTORE_KEY_ALIAS, null)).getSecretKey();
            return secretKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void decrypt(String encrypted) {

        try {
            String iv2 = getIV();
            Log.e("IV2", iv2);
            Cipher cipher1 = getCipher();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.decode(iv2, Base64.DEFAULT));
            cipher1.init(Cipher.DECRYPT_MODE, getSecretKey(), ivParameterSpec);
            String decrypted = new String(cipher1.doFinal(Base64.decode(encrypted, Base64.NO_WRAP)));
            Log.e("TEXT2", decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String string) {

        try {
            Cipher cipher;
            cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            String iv = Base64.encodeToString(cipher.getIV(), Base64.DEFAULT);
            Log.e("IV", iv);
            saveIV(iv);
            String encrypted = Base64.encodeToString(cipher.doFinal(string.getBytes()), Base64.NO_WRAP);
            Log.e("TEXT1", encrypted);
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void createKeys() {
        try {
            KeyStore keyStore = KeyStore.getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            keyStore.load(null);
            KeyGenParameterSpec aesSpec = new KeyGenParameterSpec.Builder(SecurityConstants.KEYSTORE_KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .setKeySize(256)
                    .build();

            // Create the secret key in the key store
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            keyGenerator.init(aesSpec);
            keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(
                String.format("%s/%s/%s",
                        SecurityConstants.TYPE,
                        SecurityConstants.BLOCKING_MODE,
                        SecurityConstants.PADDING_TYPE));
    }

    public interface SecurityConstants {
        String KEYSTORE_PROVIDER_ANDROID_KEYSTORE = "AndroidKeyStore";
        String KEYSTORE_KEY_ALIAS = "ManulifeMobileKeys";
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
    }

    private static String getIV() {
        SharedPreferences preferences = context.getSharedPreferences("manulifeiv", MODE_PRIVATE);
        return preferences.getString("iv", "");
    }

}
