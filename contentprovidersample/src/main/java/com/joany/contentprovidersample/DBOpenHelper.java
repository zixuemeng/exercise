package com.joany.contentprovidersample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joany on 2016/8/17.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "user.db";
    public static final String DB_TABLE = "user";
    public static final int DB_VERSION = 1;

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DB_TABLE
                +"("+User.KEY_ID +" integer primary key autoincrement,"
                + User.KEY_NAME +" text not null,"
                + User.KEY_AGE +" integer,"
                + User.KEY_HEIGHT + " float);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST " + DB_TABLE);
        onCreate(db);
    }
}
