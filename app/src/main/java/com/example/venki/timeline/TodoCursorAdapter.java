package com.example.venki.timeline;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TodoCursorAdapter extends CursorAdapter {

    public TodoCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView event_date = view.findViewById(R.id.list_event_date);
        TextView events = view.findViewById(R.id.list_events);
        event_date.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        events.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
    }
}
