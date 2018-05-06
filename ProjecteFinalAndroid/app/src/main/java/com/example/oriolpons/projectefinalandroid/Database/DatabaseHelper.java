package com.example.oriolpons.projectefinalandroid.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 01/05/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "Database";

    public DatabaseHelper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_TABLE =
                "CREATE TABLE rememberme ( _id TEXT PRIMARY KEY, " +
                        "username TEXT, " +
                        "email TEXT)";

       // db.execSQL(CREATE_TABLE);

        CREATE_TABLE =
                "CREATE TABLE filterconfig ( _id TEXT, " +
                        "ascdesc TEXT, " +
                        "type TEXT, " +
                        "city TEXT, " +
                        "cityposition INTEGER)";

        db.execSQL(CREATE_TABLE);


        CREATE_TABLE =
                "CREATE TABLE bar ( _id INTEGER PRIMARY KEY, " +
                        "name TEXT, NOT NULL" +
                        "description TEXT, " +
                        "assessment REAL," +
                        "address TEXT, " +
                        "opening_hours TEXT, " +
                        "schedule_close TEXT, " +
                        "gastronomy TEXT, " +
                        "category INTEGER)";
        //db.execSQL(CREATE_TABLE);

        CREATE_TABLE =
                "CREATE TABLE pub ( _id INTEGER PRIMARY KEY, " +
                        "name TEXT, NOT NULL" +
                        "description TEXT, " +
                        "assessment REAL," +
                        "address TEXT, " +
                        "opening_hours TEXT, " +
                        "schedule_close TEXT)";
        //db.execSQL(CREATE_TABLE);

        CREATE_TABLE =
                "CREATE TABLE disco ( _id INTEGER PRIMARY KEY, " +
                        "name TEXT, NOT NULL" +
                        "description TEXT, " +
                        "assessment REAL," +
                        "address TEXT, " +
                        "opening_hours TEXT, " +
                        "schedule_close TEXT)" +
                        "entrance_price TEXT)";
        //db.execSQL(CREATE_TABLE);


/*
        CREATE_TABLE =
                "CREATE TABLE local ( _id INTEGER PRIMARY KEY, " +
                        "type TEXT, NOT NULL" +
                        "name TEXT, NOT NULL" +
                        "description TEXT, " +
                        "assessment REAL," +
                        "address TEXT, " +
                        "opening_hours TEXT, " +
                        "schedule_close TEXT, " +
                        "gastronomy TEXT, " +
                        "category INTEGER, " +
                        "entrance_price TEXT)";

        db.execSQL(CREATE_TABLE);
*/
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}