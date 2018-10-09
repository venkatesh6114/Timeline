package com.example.venki.timeline;

import android.content.Context;
import android.content.Intent;
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


    public class TimelineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView dataTextView,eventTextView;
    TimelineRecyclerView timelineRecyclerView;

        public TimelineViewHolder(View itemView,int viewType) {
            super(itemView);
            dataTextView = itemView.findViewById(R.id.card_event_date);
            eventTextView = itemView.findViewById(R.id.card_event_name);
            timelineRecyclerView = itemView.findViewById(R.id.marker);
     //       Log.e("venki","viewType::"+viewType);
            timelineRecyclerView.initLine(viewType);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            DataModel currentModel = TimelineAdapter.this.mDataModel.get(getLayoutPosition());
            Intent i = new Intent(view.getContext(),SingleDateActivity.class);
            i.putExtra(MainActivity.SINGLE_EVENT_DATE,currentModel.date);
            i.putExtra(MainActivity.SINGLE_EVENT_NAME,currentModel.event_name);
            mContext.startActivity(i);
        }
    }

    public TimelineAdapter(Context context, ArrayList<DataModel> mDataModel) {
        mContext = context;
        if(mDataModel!=null)
            this.mDataModel = mDataModel;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        mContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        TimelineViewHolder vh = new TimelineViewHolder(v,viewType);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, final int position) {
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

    @Override
    public int getItemViewType(int position) {
        return TimelineRecyclerView.getTimeLineViewType(position,getItemCount());
    }

}