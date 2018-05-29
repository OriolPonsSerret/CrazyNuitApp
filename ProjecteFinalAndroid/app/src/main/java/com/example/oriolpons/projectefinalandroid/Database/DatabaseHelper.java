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
                        "email TEXT)";

        db.execSQL(CREATE_TABLE);

        CREATE_TABLE =
                "CREATE TABLE user ( _id INTEGER PRIMARY KEY, " +
                        "username TEXT, " +
                        "description TEXT, " +
                        "email TEXT, " +
                        "phonenumber INTEGER, " +
                        "birthdate DATE)";

        db.execSQL(CREATE_TABLE);

        CREATE_TABLE =
                "CREATE TABLE filterconfig ( _id TEXT PRIMARY KEY, " +
                        "ascdesc TEXT, " +
                        "type TEXT, " +
                        "city TEXT, " +
                        "cityposition INTEGER)";

        db.execSQL(CREATE_TABLE);


        CREATE_TABLE =
                "CREATE TABLE restaurant ( _id INTEGER PRIMARY KEY, " +
                        "name TEXT NOT NULL, " +
                        "description TEXT, " +
                        "assessment REAL," +
                        "address TEXT, " +
                        "opening_hours TEXT, " +
                        "schedule_close TEXT, " +
                        "gastronomy TEXT, " +
                        "category INTEGER)";
        db.execSQL(CREATE_TABLE);

        CREATE_TABLE =
                "CREATE TABLE pub ( _id INTEGER PRIMARY KEY, " +
                        "name TEXT NOT NULL, " +
                        "description TEXT, " +
                        "assessment REAL, " +
                        "address TEXT, " +
                        "opening_hours TEXT, " +
                        "schedule_close TEXT)";
        db.execSQL(CREATE_TABLE);

        CREATE_TABLE =
                "CREATE TABLE disco ( _id INTEGER PRIMARY KEY, " +
                        "name TEXT NOT NULL, " +
                        "description TEXT, " +
                        "assessment REAL, " +
                        "address TEXT, " +
                        "opening_hours TEXT, " +
                        "schedule_close TEXT, " +
                        "entrance_price REAL)";
        db.execSQL(CREATE_TABLE);

        CREATE_TABLE =
                "CREATE TABLE routes ( _id INTEGER PRIMARY KEY, " +
                        "route_lenght INTEGER NOT NULL, " +
                        "name TEXT NOT NULL, " +
                        "description TEXT, " +
                        "assessment REAL, " +
                        "creator INTEGER NOT NULL, " +
                        "city TEXT NOT NULL, " +
                        "rute_locals TEXT, " +
                        "route_date TEXT, " +
                        "favourite TEXT)";
        db.execSQL(CREATE_TABLE);

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