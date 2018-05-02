package com.example.oriolpons.projectefinalandroid.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 01/05/2018.
 */

public class Datasourse {
/*
    public static final String table_REMEMBERME = "rememberme";
    public static final String REMEMBERME_ID = "_id";
    public static final String REMEMBERME_USERNAME = "username";
    public static final String REMEMBERME_EMAIL = "email";
*/
    private DatabaseHelper dbHelper;
    private SQLiteDatabase dbW, dbR;

    // CONSTRUCTOR
    public Datasourse(Context ctx) {
        // Ombrim la comunicació amb la base de dades
        dbHelper = new DatabaseHelper(ctx);
        open();
    }
    private void open() {
        dbW = dbHelper.getWritableDatabase();
        dbR = dbHelper.getReadableDatabase();
    }
    // DESTRUCTOR
    protected void finalize () {
        // Cerramos las bases de datos.
        dbW.close();
        dbR.close();
    }



}