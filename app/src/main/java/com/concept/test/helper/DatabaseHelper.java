package com.concept.test.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {


    // Database Information
    private static final String DB_NAME = "POSTLIST.DB";
    // database version
    private static final int DB_VERSION = 1;
    // Table Name
    static final String TABLE_NAME = "postTable";

    // Table columns
    public static final String _ID = "_id";
    public static final String COL_2 = "post";
//    public static final String autoId = "autoId";

   // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT NOT NULL);";


    public DatabaseHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        SQLiteDatabase db   = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("htht","Table Xreated");
        db.execSQL( CREATE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.d("htht","Table Deleted");
        onCreate(db);
    }

    public boolean insertData(String postStory){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(  );
        contentValues.put( COL_2,postStory);
        long result = db.insert( TABLE_NAME,null,contentValues );
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }


    public Cursor viewData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "select * from " + TABLE_NAME, null );
        return cursor;

    }


}
