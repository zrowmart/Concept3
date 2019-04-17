package com.concept.test.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "post_data";
    private static final String COL_1 = "post";
    private static final String COL_2 = "autoId";

    public DbHelper(Context context) {
        super( context,TABLE_NAME,null,1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + COL_1+ " TEXT, "+ COL_2 +" TEXT)";
        db.execSQL( createTable );
        addData( "ankit sukla","Nothing much" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS "+ TABLE_NAME );
        onCreate( db );
    }

    public boolean addData(String item1,String item2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(  );
        contentValues.put( COL_1,item1 );
        contentValues.put(COL_2,item2);
        Log.d("YTA","addData: Adding" + item1 + "and" + item2 + TABLE_NAME);
        long result = db.insert( TABLE_NAME,null,contentValues );
        if(result == 1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor data = db.rawQuery( query,null );
        return data;
    }
}
