package com.skanderjabouzi.eventbus;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    Button button1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
//        button2 = findViewById(R.id.button2);
        button1.setOnClickListener(buttonClicked);
//        button2.setOnClickListener(buttonClicked);

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

    private View.OnClickListener buttonClicked = new View.OnClickListener() {
        public void onClick(View v) {
            // So we will make
            switch (v.getId() /*to get clicked view id**/) {
                case R.id.button1:
                    startService();
                    break;
//                case R.id.button2:
//                    break;
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

    private void startService() {
        Intent intent = new Intent(this, MyService.class);
        this.startService(intent);
    }


    private void showEditDialog() {
        Toast.makeText(this, "This is a toast text", Toast.LENGTH_LONG).show();
    }
}

