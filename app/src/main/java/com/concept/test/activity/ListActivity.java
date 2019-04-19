package com.concept.test.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

import com.concept.test.R;
import com.concept.test.helper.DBManager;
import com.concept.test.helper.DatabaseHelper;
import com.concept.test.util.ZrowActivity;

public class ListActivity extends ZrowActivity {

    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.post };

    final int[] to = new int[] { R.id.title };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_data );
        thisActivity = ListActivity.this;

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_list_data, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

    }
}
