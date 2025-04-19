package com.example.planner_practice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    /**
     * Додає нове завдання до бази даних.
     *
     * @param name назва завдання
     * @param difficulty рівень складності
     * @param time орієнтовна тривалість виконання (в хвилинах)
     * @param date запланова дата виконання
     * @param priority пріоритет завдання
     * @param type тип завдання
     */
    public void insertToDb(String name, int difficulty, int time, String date, int priority, String type) {
        ContentValues cv = new ContentValues();
        cv.put(Tasks.NAME, name);
        cv.put(Tasks.DIFFICULTY, difficulty);
        cv.put(Tasks.TIME, time);
        cv.put(Tasks.DATE, date);
        cv.put(Tasks.PRIORITY, priority);
        cv.put(Tasks.TYPE, type);
        cv.put(Tasks.STATUS, 0);
        db.insert(Tasks.TABLE_NAME, null, cv);
    }

    public List<Task> getFromDb(String s) {
        List<Task> list = new ArrayList<>();
        Cursor cursor = db.query(Tasks.TABLE_NAME, null, s, null,
                null, null, Tasks.DATE + " ASC, " + Tasks.PRIORITY + " ASC");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Tasks._ID));
            String name = cursor.getString(cursor.getColumnIndex(Tasks.NAME));
            int difficulty = cursor.getInt(cursor.getColumnIndex(Tasks.DIFFICULTY));
            int time = cursor.getInt(cursor.getColumnIndex(Tasks.TIME));
            String date = cursor.getString(cursor.getColumnIndex(Tasks.DATE));
            int priority = cursor.getInt(cursor.getColumnIndex(Tasks.PRIORITY));
            String type = cursor.getString(cursor.getColumnIndex(Tasks.TYPE));
            int status = cursor.getInt(cursor.getColumnIndex(Tasks.STATUS));
            list.add(new Task(id, name, difficulty, time, date, priority, type, status));
        }
        cursor.close();
        return list;
    }

    public void changeStatus(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(Tasks.STATUS, status);
        db.update(Tasks.TABLE_NAME, cv, "_ID = " + id, null);
    }

    public void deleteTask(int id) {
        db.delete(Tasks.TABLE_NAME, "_ID = " + id, null);
    }

    public void closeDb(){
        dbHelper.close();
    }
}
