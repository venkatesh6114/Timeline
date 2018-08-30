package com.example.venki.timeline;

public class DataModel {
    String date,event_name;

    public DataModel(String date,String event_name){
        this.date = date;
        this.event_name = event_name;
    }

    private String getDate(){
        return date;
    }
    private String getEventName(){
        return event_name;
    }
}
