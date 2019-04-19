package com.concept.test.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    // Database Information
    private static final String DB_NAME = "POSTLIST.DB";
    // database version
    private static final int DB_VERSION = 1;
    // Table Name
    static final String TABLE_NAME = "postTable";

    // Table columns
    public static final String _ID = "_id";
    public static final String post = "post";
//    public static final String autoId = "autoId";

   // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + post + " TEXT NOT NULL);";


    DatabaseHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( CREATE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
