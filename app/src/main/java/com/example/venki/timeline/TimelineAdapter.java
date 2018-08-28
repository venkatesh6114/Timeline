package com.example.venki.timeline;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Time;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>{
//    public String[] mDataSet;
    public  static class TimelineViewHolder extends RecyclerView.ViewHolder{
    TextView mTextView;

    public TimelineViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.card_textView);
            Log.e("Adapter:","ViewHolder");
        }
    }

    public TimelineAdapter() {
//        mDataSet = mDataSet;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        TimelineViewHolder vh = new TimelineViewHolder(v);
        Log.e("Adapter:","oncreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        Log.e("Adapter:","onBindViewHolder");
        holder.mTextView.setText("Venki");
    }

    @Override
    public int getItemCount() {
        Log.e("Adapter:","getItemCount");
        return 1;
    }


}