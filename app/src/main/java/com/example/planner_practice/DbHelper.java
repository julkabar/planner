package com.example.planner_practice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(@Nullable Context context) {
        super(context, Tasks.DB_NAME, null, Tasks.DB_VERSION);
    }
   @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Tasks.TABLE_STRUCTURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Tasks.DROP_TABLE);
        onCreate(db);
    }
}
