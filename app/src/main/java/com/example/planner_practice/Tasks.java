package com.example.planner_practice;

public class Tasks {
    public static final String TABLE_NAME = "tasks";
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String DIFFICULTY = "difficulty";
    public static final String TIME = "time";
    public static final String DATE = "date";
    public static final String PRIORITY = "priority";
    public static final String TYPE = "type";
    public static final String STATUS = "status";
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "task.db";
    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + NAME + " TEXT, "
            + DIFFICULTY + " INTEGER, " + TIME + " INTEGER, " + DATE + " TEXT,"
            + PRIORITY + " INTEGER,"  + TYPE + " TEXT, " + STATUS + " INTEGER)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
