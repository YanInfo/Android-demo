package com.iauto.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.iauto.dbhelper.model.MyDbHelper;

public class MainActivity extends AppCompatActivity {

    private MyDbHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDbHelper = new MyDbHelper(this, "BookStore.db", null, 1);
        Button createDatabase = (Button) findViewById(R.id.create_database) ;
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDbHelper.getWritableDatabase();
            }
        });

        //  插入数据
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                // 开始组装第一条数据
                values.put("name","The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price",16.96);
                //插入第一条数据
                db.insert("book",null,values);
                values.clear();
                // 开始组装第二条数据
                values.put("name","The Lost Symbol");
                values.put("author","Dan Brown");
                values.put("pages",510);
                values.put("price",19.95);
                //插入第二条数据
                db.insert("book",null,values);
            }
        });

        //  修改数据
        Button updateData = findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 100.1);

                //把Book表中，名字是的Thinking in Java书的价格改成100.1
                db.update("Book", values, "name = ?", new String[] {"Thinking in Java"});
            }
        });

        //  删除数据
        Button deleteData = findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = myDbHelper.getWritableDatabase();

                // 删除页数超过500页的书
                db.delete("book", "pages > ?", new String[]{"500"});
            }
        });

        //  查询数据
        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                // 查询 book 表中所有数据
                Cursor cursor = db.query("book", null,null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do {
                        // 遍历 Cursor 对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        //日志打印
                        Log.d("SQLiteActivity_query", "book name is: " + name);
                        Log.d("SQLiteActivity_query", "book author is: " + author);
                        Log.d("SQLiteActivity_query", "book pages are: " + pages);
                        Log.d("SQLiteActivity_query", "book price is: " + price);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });



    }
}
