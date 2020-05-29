package com.bryanpoh.c347_p06_ps_taskmananger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "taskManager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT) ";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");

        /*//Dummy records, to be inserted when the database is created
        for (int i = 0; i< 4; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOTE_CONTENT, "Data number " + i);
            db.insert(TABLE_NOTE, null, values);
        }
        Log.i("info", "dummy records inserted");*/
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_TASK + " ADD COLUMN module_name TEXT ");
        // onCreate(db); // Delete as table already created
    }



    public long insertTask(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);

        // Returns a number which is recordID on the table for record inserted
        // If insert fail, id will be -1
        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> songs = new ArrayList<Task>();

        String selectQuery = "SELECT "
                + COLUMN_NAME  + "," + COLUMN_DESCRIPTION + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);

                Task song = new Task(name, description);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Task> getAllTasks(String keyword) {
        ArrayList<Task> tasks = new ArrayList<Task>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION};
        String condition = COLUMN_DESCRIPTION + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(TABLE_TASK, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);

                Task task = new Task(name, description);
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }
}
