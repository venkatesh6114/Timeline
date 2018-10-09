package com.example.venki.timeline;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  InputEventDialog.InputEventDialogInterface {

    private String TAG="Timeline";
    private EventDatabaseHelper databaseHelper;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TimelineAdapter tlAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataModel> dataModelArrayList;
    public static final String SINGLE_EVENT_DATE = "single_event_date";
    public static final String SINGLE_EVENT_NAME = "single_event_name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputEventDialog inputEventDialog = new InputEventDialog();
                inputEventDialog.show(getSupportFragmentManager(),"input event dialog");
            }
        });

        databaseHelper = new EventDatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        dataModelArrayList = new ArrayList<>();

        tlAdapter = new TimelineAdapter(this,getStringArray(databaseHelper.getAllEvent()));
        recyclerView.setAdapter(tlAdapter);
    }

    public ArrayList<DataModel> getStringArray(Cursor cursor){
        if(cursor!=null) {
            cursor.moveToFirst();
            if(cursor.getCount() == 0)
                return null;
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
        databaseHelper.insertData(date,event);
        dataModelArrayList.add(new DataModel(date,event));
        tlAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        Log.e(TAG,"onResume");
        super.onResume();
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
