package com.skanderjabouzi.eventbus;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button button1;
    Button button2;
    Button button3;
    ByteArrayOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button1.setOnClickListener(buttonClicked);
        button2.setOnClickListener(buttonClicked);
        button3.setOnClickListener(buttonClicked);
        loadFragment();

        KeyStoreHelper.context = this;
        try {
            KeyStoreHelper.testAES();
        } catch (Exception e) {
            e.printStackTrace();
        }

        KeyStoreHelper2.context = this;
        String s1 = KeyStoreHelper2.encrypt("skander jabouzi");
        Log.e("Text1", s1);
        String s2 = KeyStoreHelper2.decrypt(s1);
        if (s2 != null) Log.e("Text2", s2);
        else Log.e("Text2", "NULL");

        //        String text = "This is a test string";
//        try {
//            outputStream = KeyStoreHelper.encrypt(text);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            String text2 = KeyStoreHelper.decrypt(outputStream, text);
//            Log.e("Text", text2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void loadFragment() {
        Fragment drawerHome = new Fragment1();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, drawerHome, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private View.OnClickListener buttonClicked = new View.OnClickListener() {
        public void onClick(View v) {
            // So we will make
            switch (v.getId() /*to get clicked view id**/) {
                case R.id.button1:
                    startService();
                    break;
                case R.id.button2:
                    startService2();
                    break;
                case R.id.button3:
                    startService3();
                    break;
                default:
                    break;
            }
        }
    };

    @Subscribe
    public void onEvent(Event event) {
        if (event.getMessage().equals("ShowPushPermission")) {
            showEditDialog();
        }
    }

    @Subscribe
    public void onEvent2(String event) {
        if (event.equals("TestEvent")) {
            showEditDialog2();
        }
    }

    @Subscribe
    public void onEvent3(Event3 event) {
        if (event.getMessage().equals("EventBus3")) {
            showEditDialog3(event.getPerson());
        }
    }

    private void startService() {
        Intent intent = new Intent(this, MyService.class);
        this.startService(intent);
    }

    private void startService2() {
        Intent intent = new Intent(this, MyService2.class);
        this.startService(intent);
    }

    private void startService3() {
        Intent intent = new Intent(this, MyService3.class);
        this.startService(intent);
    }


    private void showEditDialog() {
        Toast.makeText(this, "This is a toast text", Toast.LENGTH_LONG).show();
    }

    private void showEditDialog2() {
        Toast.makeText(this, "This is a toast text2", Toast.LENGTH_LONG).show();
    }

    private void showEditDialog3(Person person) {
        Toast.makeText(this, "This is a toast text3 with person name = " + person.getName() + " and age = " + person.getAge(), Toast.LENGTH_LONG).show();
    }
}

