package com.example.venki.timeline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class SingleDateActivity extends AppCompatActivity {
    String date,eventName;
    RecyclerView recyclerView;
    LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        date = getIntent().getStringExtra(MainActivity.SINGLE_EVENT_DATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(date);

        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventName = getIntent().getStringExtra(MainActivity.SINGLE_EVENT_NAME);

        recyclerView = findViewById(R.id.single_date_recyclerview);
        recyclerView.setHasFixedSize(true);
        llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new SingleDateAdapter(date,eventName));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
