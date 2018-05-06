package com.example.oriolpons.projectefinalandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created on 01/05/2018.
 */

public class Datasource {
/*
    public static final String table_REMEMBERME = "rememberme";
    public static final String REMEMBERME_ID = "_id";
    public static final String REMEMBERME_USERNAME = "username";
    public static final String REMEMBERME_EMAIL = "email";
*/
    public static final String table_FILTERCONFIG= "filterconfig";
    public static final String FILTERCONFIG_ID = "_id";
    public static final String FILTERCONFIG_ASCDESC = "ascdesc";
    public static final String FILTERCONFIG_TYPE = "type";
    public static final String FILTERCONFIG_CITY = "city";
    public static final String FILTERCONFIG_CITYPOSITION = "cityposition";


    private DatabaseHelper dbHelper;
    private SQLiteDatabase dbW, dbR;

    // CONSTRUCTOR
    public Datasource(Context ctx) {
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
/*
    public Cursor filterConfigCount() {
        return dbR.rawQuery("SELECT COUNT(*) " +
                " FROM " + table_FILTERCONFIG, null);
    }
*/
    public boolean filterConfigVerification() {
        Cursor c = dbR.rawQuery("SELECT " + FILTERCONFIG_ID  +
                " FROM " + table_FILTERCONFIG +
                " WHERE UPPER(" +  FILTERCONFIG_ID + ") LIKE UPPER ('local')", null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public Cursor filterConfigLocal() {
        return dbR.rawQuery("SELECT " + FILTERCONFIG_ASCDESC + ", " + FILTERCONFIG_TYPE + ", " + FILTERCONFIG_CITY + ", " + FILTERCONFIG_CITYPOSITION +
                " FROM " + table_FILTERCONFIG +
                " WHERE UPPER(" +  FILTERCONFIG_ID + ") LIKE UPPER ('local')", null);
    }

    public Cursor filterConfigRoute() {
        return dbR.rawQuery("SELECT " + FILTERCONFIG_ASCDESC + ", " + FILTERCONFIG_TYPE + ", " + FILTERCONFIG_CITY + ", " + FILTERCONFIG_CITYPOSITION +
                " FROM " + table_FILTERCONFIG +
                " WHERE UPPER(" +  FILTERCONFIG_ID + ") LIKE UPPER ('routes')", null);
    }
/*
    public Cursor filterConfigLocalType() {
        return dbR.rawQuery("SELECT " + FILTERCONFIG_TYPE  +
                " FROM " + table_FILTERCONFIG +
                " WHERE UPPER(" +  FILTERCONFIG_ID + ") LIKE UPPER ('local')", null);
    }
*/


    public void DefaultFilterConfigAdd() {
        ContentValues values = new ContentValues();
        values.put(FILTERCONFIG_ID,"local");
        values.put(FILTERCONFIG_ASCDESC,"asc");
        values.put(FILTERCONFIG_TYPE,"restaurants");
        values.put(FILTERCONFIG_CITY,"Mataró");
        values.put(FILTERCONFIG_CITYPOSITION,0);

        dbW.insert(table_FILTERCONFIG,null,values);

        values.put(FILTERCONFIG_ID,"routes");
        values.put(FILTERCONFIG_ASCDESC,"asc");
        values.put(FILTERCONFIG_TYPE,"cortas");
        values.put(FILTERCONFIG_CITY,"Mataró");
        values.put(FILTERCONFIG_CITYPOSITION,0);

        dbW.insert(table_FILTERCONFIG,null,values);
    }

    public void FilterConfigUpdate(String id, String ascdesc, String type,  String city,  int position) {
        ContentValues values = new ContentValues();
        values.put(FILTERCONFIG_ASCDESC,ascdesc);
        values.put(FILTERCONFIG_TYPE,type);
        values.put(FILTERCONFIG_CITY,city);
        values.put(FILTERCONFIG_CITYPOSITION,position);

        dbW.update(table_FILTERCONFIG,values, FILTERCONFIG_ID + " = ?", new String[] { String.valueOf(id) });
    }

}