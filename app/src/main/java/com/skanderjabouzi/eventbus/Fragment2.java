package com.skanderjabouzi.eventbus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class Fragment2 extends Fragment {

    View rootView;
    Button button5;
    TextView text5;

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 newInstance() {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        text5 = rootView.findViewById(R.id.text5);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment2, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.e("EVENT2", "FARGMENT2 REGISTRED");
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        Log.e("EVENT2", "FARGMENT2 UNREGISTRED");
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(Event4 event) {
        Log.e("EVENT2 MESSAGE2", event.getMessage());
        if (event.getMessage().equals("fragmentMessage1")) {
            Log.e("EVENT2 TEXT2", event.getText());
            text5.setText(event.getText());
        }
    }
}
