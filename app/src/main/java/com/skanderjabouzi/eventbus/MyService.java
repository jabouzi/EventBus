package com.skanderjabouzi.eventbus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        try {
            Thread.sleep(5000);
            EventBus.getDefault().post(new Event("ShowPushPermission"));
        } catch(InterruptedException e) {
            // Process exception
        }
    }

}
