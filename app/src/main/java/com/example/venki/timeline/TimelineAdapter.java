package com.example.venki.timeline;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>{
    public ArrayList<DataModel> mDataModel;
    public  static class TimelineViewHolder extends RecyclerView.ViewHolder{
    TextView dataTextView,eventTextView;

    public TimelineViewHolder(View v) {
            super(v);
            dataTextView = v.findViewById(R.id.card_event_date);
            eventTextView = v.findViewById(R.id.card_event_name);
        }
    }

    public TimelineAdapter(ArrayList<DataModel> mDataModel) {
        if(mDataModel!=null)
            this.mDataModel = mDataModel;
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
        String date = mDataModel.get(position).date;
        String event_name = mDataModel.get(position).event_name;
        holder.dataTextView.setText(date);
        holder.eventTextView.setText(event_name);
    }

    @Override
    public int getItemCount() {
        if(mDataModel==null)
            return 0;
        return mDataModel.size();
    }


}