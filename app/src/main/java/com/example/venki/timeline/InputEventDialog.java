package com.example.venki.timeline;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.UiAutomation;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Calendar;

public class InputEventDialog extends AppCompatDialogFragment {

    private EditText event_textField;
    private EditText date_textField;
    private Calendar calendar;
    private ImageButton datePicker;
    private InputEventDialogInterface inputEventDialogInterface;
    private Handler mHandler;
    private DialogInterface tmpDialogInterface;
    private TextView event_err_msg;

   @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.e("Timeline","onCreateDialog(bundle)");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_create_timeline,null);

        calendar = Calendar.getInstance();
        event_textField = view.findViewById(R.id.event_value);
        date_textField = view.findViewById(R.id.date_editText);
        date_textField.setText(getCurrentDate());
        datePicker = view.findViewById(R.id.imageButton);
        event_err_msg = view.findViewById(R.id.event_err_msg);

        event_textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event_err_msg.setVisibility(View.INVISIBLE);
            }
        });

        date_textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerDialog();
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerDialog();
            }
        });

        mHandler = new Handler(Looper.getMainLooper());
        Log.e("Timeline>> :",getClass().getSuperclass().toString());
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
                        tmpDialogInterface = dialogInterface;
                        if(!event_name.isEmpty()) {
                            if(!date.isEmpty())
                                inputEventDialogInterface.attachTexts(date, event_name);
                            else
                                inputEventDialogInterface.attachTexts(getCurrentDate(),event_name);
                            canCloseDialog(dialogInterface,true);
                        }
                        else {
                            canCloseDialog(dialogInterface, false);
                            event_err_msg.setVisibility(View.VISIBLE);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    event_err_msg.setVisibility(View.INVISIBLE);
                                }
                            },3000);
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                canCloseDialog(tmpDialogInterface,true);
                            }
                        },50);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        canCloseDialog(dialogInterface,true);
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Log.e("Timeline:","in onDismiss");
                    }
                })
                ;

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

    private void canCloseDialog(DialogInterface dialogInterface, boolean canClose){
        try {
            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialogInterface,canClose);
        }catch(NoSuchFieldException e){}
        catch (IllegalAccessException e){}
    }


    private String getCurrentDate() {
        return new StringBuilder().append(doubleDigit(calendar.get(Calendar.DAY_OF_MONTH)))
                            .append("/")
                            .append(doubleDigit(calendar.get(Calendar.MONTH)))
                            .append("/")
                            .append(calendar.get(Calendar.YEAR)).toString();
    }

    private String doubleDigit(int a){
        return (a<10)?("0"+a):(""+a);
    }

    private DatePickerDialog.OnDateSetListener date_range_listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            date_textField.setText(new StringBuilder()
                            .append(doubleDigit(day))
                            .append("/").append(doubleDigit(month+1))
                            .append("/").append(year));
        }
    };

    private void showPickerDialog(){
        String fieldDate = date_textField.getText().toString();
        int day = Integer.parseInt(doubleDigit(Integer.parseInt(fieldDate.split("/")[0])));
        int month = Integer.parseInt(doubleDigit(Integer.parseInt(fieldDate.split("/")[1])-1));
        int year = Integer.parseInt(doubleDigit(Integer.parseInt(fieldDate.split("/")[2])));
        new DatePickerDialog(getContext(),date_range_listener,
                year,
                month,
                day).show();
    }

    protected interface InputEventDialogInterface{
        void attachTexts(String date,String event);
    }

}
