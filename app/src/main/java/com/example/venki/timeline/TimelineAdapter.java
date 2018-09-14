package com.example.venki.timeline;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>{
    public ArrayList<DataModel> mDataModel;
    private Context mContext;

    @Override
    public int getItemViewType(int position) {
        Log.e("venki","getItemViewType:"+position+",count:"+getItemCount());
        return TimelineRecyclerView.getTimeLineViewType(position,getItemCount());
    }


    public  static class TimelineViewHolder extends RecyclerView.ViewHolder{
    TextView dataTextView,eventTextView;
    TimelineRecyclerView timelineRecyclerView;

    public TimelineViewHolder(View itemView,int viewType) {
            super(itemView);
            dataTextView = itemView.findViewById(R.id.card_event_date);
            eventTextView = itemView.findViewById(R.id.card_event_name);
            timelineRecyclerView = itemView.findViewById(R.id.marker);
            Log.e("venki","viewType::"+viewType);
            timelineRecyclerView.initLine(viewType);
        }
    }

    public TimelineAdapter(ArrayList<DataModel> mDataModel) {
        if(mDataModel!=null)
            this.mDataModel = mDataModel;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        Log.e("venki","before inflate the view ");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        Log.e("venki","before creating the view holder...");
        TimelineViewHolder vh = new TimelineViewHolder(v,viewType);
        Log.e("Adapter:","exiting oncreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        Log.e("Adapter:","entering onBindViewHolder");
        String date = mDataModel.get(position).date;
        String event_name = mDataModel.get(position).event_name;
        holder.dataTextView.setText(date);
        holder.eventTextView.setText(event_name);
        holder.timelineRecyclerView.setMarker(ContextCompat.getDrawable(mContext,R.drawable.ic_marker ), ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    @Override
    public int getItemCount() {
        if(mDataModel==null)
            return 0;
        return mDataModel.size();
    }


}