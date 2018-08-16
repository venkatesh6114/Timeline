package com.example.venki.timeline;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.UiAutomation;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class InputEventDialog extends AppCompatDialogFragment {

    private EditText event_textField;
    private EditText date_textField;
    private Calendar calendar;
    private ImageButton datePicker;
    private InputEventDialogInterface inputEventDialogInterface;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.e("Timeline","onCreateDialog(bundle)");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_create_timeline,null);

        event_textField = view.findViewById(R.id.event_value);
        date_textField = view.findViewById(R.id.date_editText);
        datePicker = view.findViewById(R.id.imageButton);

        calendar = Calendar.getInstance();

        date_textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });

        TextView title = new TextView(getContext());
        title.setGravity(Gravity.CENTER);
        title.setText("Create Event");
        title.setPadding(10,30,10,10);
        title.setTextSize(23);
        title.setTextColor(Color.DKGRAY);

        builder.setView(view)
                .setCustomTitle(title)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String event_name = event_textField.getText().toString();
                        String date = date_textField.getText().toString();
                        inputEventDialogInterface.attachTexts(date,event_name);
                        //Toast.makeText(getContext(),"Positive button",Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(),"Negative button",Toast.LENGTH_LONG).show();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            inputEventDialogInterface = (InputEventDialogInterface) context;
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setDate(){
        new DatePickerDialog(getContext(),date_range_listener,
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
            date_textField.setText(new StringBuilder()
                            .append(i)
                            .append("/").append(doubleDigit(i1+1))
                            .append("/").append(doubleDigit(i2)));
        }
    };

    protected interface InputEventDialogInterface{
        void attachTexts(String date,String event);
    }

}
