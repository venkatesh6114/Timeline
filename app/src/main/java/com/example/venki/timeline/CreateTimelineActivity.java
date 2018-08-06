package com.example.venki.timeline;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class CreateTimelineActivity extends AppCompatActivity {

    private EditText date;
    private Calendar calendar;
    private String TAG="Timeline";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        date = findViewById(R.id.date_editText);
        date.setKeyListener(null);

        calendar=Calendar.getInstance();
    }

    public void setDate(View view){
        Log.e(TAG,"in setDate");

        new DatePickerDialog(this,date_range_listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private String doubleDigit(int a){
        return (a<10)?("0"+a):(""+a);
    }

    private DatePickerDialog.OnDateSetListener date_range_listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            Log.e(TAG,"in ondateSet");
            date.setText(new StringBuilder().append(i).append("/").append(doubleDigit(i1+1)).append("/").append(doubleDigit(i2)));
        }
    };

}


