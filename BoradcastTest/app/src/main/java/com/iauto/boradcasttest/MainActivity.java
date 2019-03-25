package com.iauto.boradcasttest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.iauto.boradcasttest.tools.BaseActivity;

import static android.content.ContentValues.TAG;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forceOffline = findViewById(R.id.force);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"################# zhangyan #################");
                //发送强制下线广播

                Intent intent = new Intent("com.yan.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }
}
