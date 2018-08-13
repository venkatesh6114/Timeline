package com.example.venki.timeline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;
//import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    static final int CREATE_EVENT=100;
    private String TAG="Timeline";
    private EventDatabaseHelper databaseHelper;
    private TodoCursorAdapter cursorAdapter;
    private Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG,"onCreate()");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CreateTimelineActivity.class);
                startActivityForResult(i,CREATE_EVENT);
            }
        });

        listView = findViewById(R.id.event_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e(TAG,"Clicked on item:"+position);
            }
        });

        databaseHelper = new EventDatabaseHelper(this);

/*        new Handler().post(new Runnable() {
            @Override
            public void run() {
                cursorAdapter = new TodoCursorAdapter(MainActivity.this,databaseHelper.getData());
                listView.setAdapter(cursorAdapter);
            }
        });
        */
    }



    @Override
    protected void onResume() {
        Log.e(TAG,"onResume");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                cursorAdapter = new TodoCursorAdapter(MainActivity.this,databaseHelper.getData());
                listView.setAdapter(cursorAdapter);
            }
        });
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG,"onActivityForResult");
        if(requestCode == CREATE_EVENT && resultCode == RESULT_OK) {
            databaseHelper.insertData(data.getExtras().getString("event_date"),data.getExtras().getString("event_name"));

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,"Settings clicked",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
