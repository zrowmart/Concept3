package com.concept.test.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.concept.test.R;
import com.concept.test.helper.DbHelper;
import com.concept.test.util.ZrowActivity;

import java.util.ArrayList;

public class ListDataActivity extends ZrowActivity {


    DbHelper dbHelper;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_data );

        listView = findViewById( R.id.listView );
        dbHelper = new DbHelper( this );
        populateListView();
    }

    private void populateListView(){
        Cursor data = dbHelper.getData();
        ArrayList<String> listData = new ArrayList<>(  );

        while(data.moveToNext()){
            listData.add( data.getString( 2 ) );
        }
        ListAdapter adapter = new ArrayAdapter<>( this,android.R.layout.simple_list_item_1,listData );
        listView.setAdapter( adapter );

    }
}
