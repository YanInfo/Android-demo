package com.iauto.testsqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private YanDatabaseHelper yanDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        yanDatabaseHelper = new YanDatabaseHelper(this, "user.db", null, 1);
        Button createDatabase = findViewById(R.id.login);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yanDatabaseHelper.getWritableDatabase();
            }
        });
    }
}
