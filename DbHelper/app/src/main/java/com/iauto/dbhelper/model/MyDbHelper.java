package com.iauto.dbhelper.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDbHelper extends SQLiteOpenHelper {

    //需要执行的sql
    public static final String CREATE_BOOK = "create table Book (\n" +
            "    id integer primary key autoincrement,\n" +
            "    author text,\n" +
            "    price real,\n" +
            "    pages integer,\n" +
            "    name text)";

    public static final String CREATE_FOOD = "create table food ("+
            " id integer primary key autoincrement, "+
            "name String, "+
            "phone varchar )";

    private Context mContext;

    //构造方法
    public MyDbHelper(Context context , String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }


        @Override
        public void onCreate(SQLiteDatabase db) {
            //执行sql语句
            db.execSQL(CREATE_BOOK);
            db.execSQL(CREATE_FOOD);
            Toast.makeText(mContext, "Create successed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                 switch (oldVersion) {
                     case 1:
                     db.execSQL(CREATE_FOOD);
                 case 2:
                     db.execSQL("alter table food add column food_id integer");
                 default:
             }
    }
}
