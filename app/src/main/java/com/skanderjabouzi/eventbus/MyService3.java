package com.skanderjabouzi.eventbus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;

public class MyService3 extends Service {
    public MyService3() {
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
            EventBus.getDefault().post(new Event3("EventBus3", createObject()));
        } catch(InterruptedException e) {
            // Process exception
        }
    }

    private Person createObject() {
        Person person = new Person();
        person.setAge(20);
        person.setName("George");

        return person;
    }

}
