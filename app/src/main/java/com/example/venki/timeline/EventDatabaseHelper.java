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
    public static String COLUMN_DATE = "date";
    public static String COLUMN_EVENT = "event";

    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase database;

     public EventDatabaseHelper(Context aContext){
         openHelper = new DatabaseOpenHelper(aContext);
         database = openHelper.getWritableDatabase();
     }

     public void insertData(String date, String event){
         Cursor cursor = getDateEvent(date);

//         Log.e("timeline","Cursor.getCount():"+cursor.getCount());

         String oldEvent="";
         if (cursor.moveToFirst()){
             do{
                 oldEvent = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT));
             }while(cursor.moveToNext());
         }
         cursor.close();

 //        Log.e("timeline","OLD Event:"+oldEvent);

         ContentValues cv = new ContentValues();
         cv.put(COLUMN_DATE, date);

         if(cursor.getCount()>0) {
             cv.put(COLUMN_EVENT, oldEvent+"\n"+event);
             database.update(TABLE_NAME, cv, COLUMN_DATE + " = ? ", new String[]{date});
         }
         else {
             cv.put(COLUMN_EVENT, event);
             database.insert(TABLE_NAME, null, cv);
         }
     }

     public Cursor getDateEvent(String date){
            String query = "SELECT * from "+TABLE_NAME+" where "+COLUMN_DATE+" = '"+date+"'";
            return database.rawQuery(query,null);
     }

     public Cursor getAllEvent(){
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
            sqLiteDatabase.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        }
    }
}
