package com.skanderjabouzi.eventbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {
    public static final String TAG = "NetworkReceiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        Log.e(TAG, "onReceive");

        String status = NetworkUtil.getConnectivityStatusString(context);

        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        final String action = intent.getAction();
//        Log.e(TAG, "onReceive");
//
//        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
//            Log.e(TAG, "android.net.conn.CONNECTIVITY_CHANGE");
//            Toast.makeText(context, "CONNECTIVITY_CHANGE", Toast.LENGTH_LONG).show();
//            if (!isNetworkAvailable(context)) {
//                Intent i=new Intent(context.getApplicationContext(), AlertDialogActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//            }
//        }
//    }
//
//    private boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
}
