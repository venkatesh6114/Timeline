package com.example.venki.timeline;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SingleDateAdapter extends RecyclerView.Adapter<SingleDateAdapter.DateViewHolder> {

    private String date;
    private String[] event;
    private String TAG="SingleDateAdapter";

    public SingleDateAdapter(String date,String eventName) {
   //     Log.e(TAG,"inSingleDateAdapter constructor");
        this.date = date;
        event = eventName.split("\n");
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView eName;
        CardView cView;
        public DateViewHolder(View itemView) {
            super(itemView);
            eName = itemView.findViewById(R.id.single_card_event_name);
            cView = itemView.findViewById(R.id.date_card_view);
        }
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.datewise_card_layout,parent,false);
        return new DateViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        holder.eName.setText(event[position]);
        if(position % 2 == 0)
            holder.cView.setCardBackgroundColor(Color.LTGRAY);
    }

    @Override
    public int getItemCount() {
        return event.length;
    }
}
