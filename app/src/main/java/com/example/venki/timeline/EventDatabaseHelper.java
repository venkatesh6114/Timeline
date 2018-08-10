package com.example.venki.timeline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventDatabaseHelper {
    private static String TAG = EventDatabaseHelper.class.getSimpleName();
    private static String DATABASE_NAME = "timeline.db";
    private static int DATABASE_VERSION = 1;
    private static String TABLE_NAME = "event_table";
    private static String COLUMN_ID = "_id";
    private static String COLUMN_DATE = "date";
    private static String COLUMN_EVENT = "event";

    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase database;

     public EventDatabaseHelper(Context aContext){
         openHelper = new DatabaseOpenHelper(aContext);
         database = openHelper.getWritableDatabase();
     }

     public long insertData(String date, String event){
         ContentValues cv = new ContentValues();
         cv.put(COLUMN_DATE, date);
         cv.put(COLUMN_EVENT, event);
         return database.insert(TABLE_NAME, null, cv);
     }

     public Cursor getData(){
        String query = "SELECT * from "+TABLE_NAME;
        return database.rawQuery(query,null);
     }

    private class DatabaseOpenHelper extends SQLiteOpenHelper{

        public DatabaseOpenHelper(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String query = "CREATE TABLE "+TABLE_NAME+"( "+COLUMN_ID+" INTEGER PRIMARY KEY,"+
                    COLUMN_DATE+" DATE,"+COLUMN_EVENT+" TEXT)";
            Log.e(TAG,"in onCreate "+query);
            sqLiteDatabase.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.e(TAG,"in onUpgrade");
        }
    }
}
