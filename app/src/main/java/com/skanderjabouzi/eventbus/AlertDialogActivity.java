package com.skanderjabouzi.eventbus;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Window;

public class AlertDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide activity title
//        setContentView(R.layout.activity_my_alert_dialog);

        AlertDialog.Builder Builder=new AlertDialog.Builder(this)
                .setMessage("Do You Want continue ?")
                .setTitle("exit")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton("Quit App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialogActivity.this.finish();
                    }
                })
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialogActivity.this.finish();
                    }
                });
        AlertDialog alertDialog=Builder.create();
        alertDialog.show();

    }
}

