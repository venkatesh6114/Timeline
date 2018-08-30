package com.example.venki.timeline;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
//import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements  InputEventDialog.InputEventDialogInterface {

    static final int CREATE_EVENT=100;
    private String TAG="Timeline";
    private EventDatabaseHelper databaseHelper;
    private TodoCursorAdapter cursorAdapter;
    private Toolbar toolbar;
    private ListView listView;
    private RecyclerView recyclerView;
    private TimelineAdapter tlAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataModel> dataModelArrayList;

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
                /*
                Intent i = new Intent(MainActivity.this,CreateTimelineActivity.class);
                startActivityForResult(i,CREATE_EVENT);
                */

                InputEventDialog inputEventDialog = new InputEventDialog();
                inputEventDialog.show(getSupportFragmentManager(),"input event dialog");
            }
        });
/*
        listView = findViewById(R.id.event_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e(TAG,"Clicked on item:"+position);
            }
        });
*/

        databaseHelper = new EventDatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"RecyclerView on Clicked");
            }
        });
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        dataModelArrayList = new ArrayList<DataModel>();

        tlAdapter = new TimelineAdapter(getStringArray(databaseHelper.getAllEvent()));
        recyclerView.setAdapter(tlAdapter);

/*        new Handler().post(new Runnable() {
            @Override
            public void run() {
     //           cursorAdapter = new TodoCursorAdapter(MainActivity.this,databaseHelper.getAllEvent());
  //              listView.setAdapter(cursorAdapter);

            }
        });
*/
    }

    public ArrayList<DataModel> getStringArray(Cursor cursor){
        if(cursor!=null) {
            cursor.moveToFirst();
            do {
                String date = cursor.getString(cursor.getColumnIndex(EventDatabaseHelper.COLUMN_DATE));
                String event = cursor.getString(cursor.getColumnIndex(EventDatabaseHelper.COLUMN_EVENT));
                if(!date.isEmpty() && !event.isEmpty())
                    dataModelArrayList.add(new DataModel(date,event));
            }while (cursor.moveToNext());
            return dataModelArrayList;
        }
        return null;
    }

    @Override
    public void attachTexts(String date, String event) {
        Log.e(TAG,"date:"+date);
        Log.e(TAG,"event:"+event);
        databaseHelper.insertData(date,event);
        dataModelArrayList.add(new DataModel(date,event));
        tlAdapter.notifyDataSetChanged();
//        cursorAdapter.swapCursor(databaseHelper.getAllEvent());
 //        cursorAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        Log.e(TAG,"onResume");
/*        new Handler().post(new Runnable() {
            @Override
            public void run() {
                cursorAdapter = new TodoCursorAdapter(MainActivity.this,databaseHelper.getData());
                listView.setAdapter(cursorAdapter);
            }
        });*/
        super.onResume();
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG,"onActivityForResult");
        if(requestCode == CREATE_EVENT && resultCode == RESULT_OK) {
            databaseHelper.insertData(data.getExtras().getString("event_date"),data.getExtras().getString("event_name"));

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
*/
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
