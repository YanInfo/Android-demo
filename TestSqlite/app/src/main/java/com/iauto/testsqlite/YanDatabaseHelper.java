package com.iauto.testsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author zhangyan
 */
public class YanDatabaseHelper extends SQLiteOpenHelper {

    //构造方法
    public YanDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table 'user'" +
                "('id' integer(10) PRIMARY KEY AUTOINCREMENT , 'username' varchar(20) not null, 'password' varchar(20) not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
