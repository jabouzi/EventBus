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


public class Fragment1 extends Fragment {

    View rootView;
    Button button4;
    TextView text4;

    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance() {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button4 = rootView.findViewById(R.id.button4);
        button4.setOnClickListener(buttonClicked);
        text4 = rootView.findViewById(R.id.text4);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment1, container, false);
        return rootView;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }

//    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
//    public void onEvent(Event event) {
//        Log.e("EVENT MESSAGE1", event.getMessage());
//        if (event.getMessage().equals("fragmentMessage2")) {
//            Log.e("EVENT TEXT1", event.getText());
//            text4.setText(event.getText());
//        }
//    }

    private View.OnClickListener buttonClicked = new View.OnClickListener() {
        public void onClick(View v) {
            // So we will make
            switch (v.getId() /*to get clicked view id**/) {
                case R.id.button4:
                    showFragment2();
                    EventBus.getDefault().postSticky(new Event4("fragmentMessage1", "This a message from fragment 1"));
                    break;
                default:
                    break;
            }
        }
    };

    private void showFragment2() {
        Fragment drawerHome = new Fragment2();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, drawerHome, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
