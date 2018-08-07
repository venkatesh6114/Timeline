package com.example.venki.timeline;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

public class CreateTimelineActivity extends AppCompatActivity {

    private EditText date;
    private EditText event;
    private Calendar calendar;
    private String TAG="Timeline";
    private ImageButton  sucessButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        calendar = Calendar.getInstance();

        event = findViewById(R.id.event_value);
        date = findViewById(R.id.date_editText);
        date.setInputType(InputType.TYPE_NULL);
//        date.setText(new StringBuilder().append(calendar.get(calendar.YEAR)).append("/").append(doubleDigit(calendar.get(calendar.MONTH)+1)).append("/").append(doubleDigit(calendar.get(calendar.DAY_OF_MONTH))));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(view);
            }
        });
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    setDate(view);
            }
        });

        sucessButton = (ImageButton)findViewById(R.id.sucess_button);
        sucessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = event.getText().toString();
                String d = date.getText().toString();

                SQLiteDatabase db = openOrCreateDatabase("timeline",MODE_PRIVATE,null);
                db.execSQL("CREATE TABLE IF NOT EXISTS TimelineList(event VARCHAR,date DATE)");
                ContentValues cv = new ContentValues();
                cv.put("event",e);
                cv.put("date",d);
                db.insert("TimelineList",null,cv);
            }
        });
    }

    public void setDate(View view){
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
            date.setText(new StringBuilder().append(i).append("/").append(doubleDigit(i1+1)).append("/").append(doubleDigit(i2)));
        }
    };

}


