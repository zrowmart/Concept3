package com.concept.test.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.concept.test.R;
import com.concept.test.helper.DatabaseHelper;

import java.util.ArrayList;

public class ListPost extends AppCompatActivity {

    ArrayList<String> offlinePost;
    ArrayAdapter<String> adapter;
    DatabaseHelper db;
    ListView draftList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_post );
        db = new DatabaseHelper( this );
        draftList = findViewById( R.id.offlineList );
        offlinePost = new ArrayList<>(  );
        viewData();

        draftList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = draftList.getAdapter().getItem( position ).toString();
                Intent in = new Intent( ListPost.this,InputActivity.class );
                in.putExtra( "post",value );
                startActivity( in );
            }
        } );
    }

    @SuppressLint("ShowToast")
    private void viewData(){
        Cursor cursor = db.viewData();
        if(cursor.getCount() == 0){
            Toast.makeText( this,"Nothing to show",Toast.LENGTH_LONG );
        }else{
            while(cursor.moveToNext()){
                offlinePost.add( cursor.getString( 1 ) );
            }
            adapter = new ArrayAdapter<>( this,android.R.layout.simple_list_item_1,offlinePost);
            draftList.setAdapter( adapter );
        }
    }

}
