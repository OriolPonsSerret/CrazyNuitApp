package com.example.oriolpons.projectefinalandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created on 01/05/2018.
 */

public class Datasource {

    public static final String table_FILTERCONFIG= "filterconfig";
    public static final String FILTERCONFIG_ID = "_id";
    public static final String FILTERCONFIG_ASCDESC = "ascdesc";
    public static final String FILTERCONFIG_TYPE = "type";
    public static final String FILTERCONFIG_CITY = "city";
    public static final String FILTERCONFIG_CITYPOSITION = "cityposition";

    public static final String table_REMEMBERME= "rememberme";
    public static final String REMEMBERME_ID = "_id";
    public static final String REMEMBERME_EMAIL = "email";

    public static final String table_USER= "user";
    public static final String USER_ID = "_id";
    public static final String USER_USERNAME = "username";
    public static final String USER_DESCRIPTION = "description";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONENUMBER = "phonenumber";
    public static final String USER_BIRTHDATE = "birthdate";

    public static final String table_RESTAURANTS= "restaurant";
    public static final String RESTAURANTS_ID = "_id";
    public static final String RESTAURANTS_NAME = "name";
    public static final String RESTAURANTS_DESCRIPTION = "description";
    public static final String RESTAURANTS_ASSESSMENT = "assessment";
    public static final String RESTAURANTS_ADDRESS = "address";
    public static final String RESTAURANTS_OPENING = "opening_hours";
    public static final String RESTAURANTS_CLOSE = "schedule_close";
    public static final String RESTAURANTS_GASTRONOMY = "gastronomy";
    public static final String RESTAURANTS_CATEGORY = "category";

    public static final String table_PUBS= "pub";
    public static final String PUBS_ID = "_id";
    public static final String PUBS_NAME = "name";
    public static final String PUBS_DESCRIPTION = "description";
    public static final String PUBS_ASSESSMENT = "assessment";
    public static final String PUBS_ADDRESS = "address";
    public static final String PUBS_OPENING = "opening_hours";
    public static final String PUBS_CLOSE = "schedule_close";

    public static final String table_DISCOS= "disco";
    public static final String DISCOS_ID = "_id";
    public static final String DISCOS_NAME = "name";
    public static final String DISCOS_DESCRIPTION = "description";
    public static final String DISCOS_ASSESSMENT = "assessment";
    public static final String DISCOS_ADDRESS = "address";
    public static final String DISCOS_OPENING = "opening_hours";
    public static final String DISCOS_CLOSE = "schedule_close";
    public static final String DISCOS_PRICE = "entrance_price";

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

    // LOGIN SCREEN
    public Cursor remembermeCount() {
        return dbR.rawQuery("SELECT COUNT(*) " +
                " FROM " + table_REMEMBERME, null);
    }

    public Cursor remembermeGetUser() {
        return dbR.rawQuery("SELECT " + REMEMBERME_ID + ", " + REMEMBERME_EMAIL +
                " FROM " + table_REMEMBERME, null);
    }

    public void userRememberAdd(int id, String email) {
        ContentValues values = new ContentValues();
        values.put(REMEMBERME_ID,id);
        values.put(REMEMBERME_EMAIL,email);

        dbW.insert(table_REMEMBERME,null,values);
    }

    public void userRememberUpdate(int id, String email) {
        ContentValues values = new ContentValues();
        values.put(REMEMBERME_ID,id);
        values.put(REMEMBERME_EMAIL,email);

        dbW.update(table_FILTERCONFIG,values, FILTERCONFIG_ID + " = ?", new String[] { String.valueOf(id) });
    }



    // FILTERS
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
        values.put(FILTERCONFIG_ASCDESC,"ASC");
        values.put(FILTERCONFIG_TYPE,"restaurants");
        values.put(FILTERCONFIG_CITY,"Mataró");
        values.put(FILTERCONFIG_CITYPOSITION,0);

        dbW.insert(table_FILTERCONFIG,null,values);

        values.put(FILTERCONFIG_ID,"routes");
        values.put(FILTERCONFIG_ASCDESC,"ASC");
        values.put(FILTERCONFIG_TYPE,"short");
        values.put(FILTERCONFIG_CITY,"Mataró");
        values.put(FILTERCONFIG_CITYPOSITION,0);

        dbW.insert(table_FILTERCONFIG,null,values);
    }

    public void filterConfigUpdate(String id, String ascdesc, String type, String city, int position) {
        ContentValues values = new ContentValues();
        values.put(FILTERCONFIG_ASCDESC,ascdesc);
        values.put(FILTERCONFIG_TYPE,type);
        values.put(FILTERCONFIG_CITY,city);
        values.put(FILTERCONFIG_CITYPOSITION,position);

        dbW.update(table_FILTERCONFIG,values, FILTERCONFIG_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public Cursor filterLocalRestaurants(String city, String order, String localName) {
        //localName = "";
        return dbR.rawQuery("SELECT " + RESTAURANTS_ID + ", " + RESTAURANTS_NAME + ", " + RESTAURANTS_DESCRIPTION + ", " + RESTAURANTS_ASSESSMENT + ", " + RESTAURANTS_ADDRESS + ", " + RESTAURANTS_OPENING + ", " + RESTAURANTS_CLOSE + ", " + RESTAURANTS_GASTRONOMY + ", " + RESTAURANTS_CATEGORY +
                " FROM " + table_RESTAURANTS +
                " WHERE UPPER(" +  RESTAURANTS_ADDRESS + ") LIKE UPPER ('" + city +"') AND UPPER(" +  RESTAURANTS_NAME + ") LIKE UPPER ('%" + localName +"%')" +
                " ORDER BY " + RESTAURANTS_ASSESSMENT + " " + order, null);
    }

    public Cursor filterLocalPub(String city, String order, String localName) {
        //localName = "";
        return dbR.rawQuery("SELECT " + PUBS_ID + ", " + PUBS_NAME + ", " + PUBS_DESCRIPTION + ", " + PUBS_ASSESSMENT + ", " + PUBS_ADDRESS + ", " + PUBS_OPENING + ", " + PUBS_CLOSE+
                " FROM " + table_PUBS +
                " WHERE UPPER(" +  PUBS_ADDRESS + ") LIKE UPPER ('" + city +"') AND UPPER(" +  PUBS_NAME + ") LIKE UPPER ('%" + localName +"%')" +
                " ORDER BY " + PUBS_ASSESSMENT + " " + order, null);
    }

    public Cursor filterLocalDisco(String city, String order, String localName) {
        //localName = "";
        return dbR.rawQuery("SELECT " + DISCOS_ID + ", " + DISCOS_NAME + ", " + DISCOS_DESCRIPTION + ", " + DISCOS_ASSESSMENT + ", " + DISCOS_ADDRESS + ", " + DISCOS_OPENING + ", " + DISCOS_CLOSE + ", " + DISCOS_PRICE +
                " FROM " + table_DISCOS +
                " WHERE UPPER(" +  DISCOS_ADDRESS + ") LIKE UPPER ('" + city +"') AND UPPER(" +  DISCOS_NAME + ") LIKE UPPER ('%" + localName +"%')" +
                " ORDER BY " + DISCOS_ASSESSMENT + " " + order, null);
    }






    public boolean restaurantsAskExist(int id) {
        Cursor c = dbR.rawQuery("SELECT " + RESTAURANTS_ID  +
                " FROM " + table_RESTAURANTS +
                " WHERE " +  RESTAURANTS_ID + " LIKE " + id, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public void restaurantsAdd(int id, String name, String description, Double assessment, String address, String opening, String close, String gastronomy, int category) {
        ContentValues values = new ContentValues();
        values.put(RESTAURANTS_ID, id);
        values.put(RESTAURANTS_NAME, name);
        values.put(RESTAURANTS_DESCRIPTION, description);
        values.put(RESTAURANTS_ASSESSMENT, assessment);
        values.put(RESTAURANTS_ADDRESS, address);
        values.put(RESTAURANTS_OPENING, opening);
        values.put(RESTAURANTS_CLOSE, close);
        values.put(RESTAURANTS_GASTRONOMY, gastronomy);
        values.put(RESTAURANTS_CATEGORY, category);

        dbW.insert(table_RESTAURANTS, null, values);
    }

    public void restaurantsUpdate(int id, String name, String description, Double assessment, String address, String opening, String close, String gastronomy, int category) {
        ContentValues values = new ContentValues();
        values.put(RESTAURANTS_NAME, name);
        values.put(RESTAURANTS_DESCRIPTION, description);
        values.put(RESTAURANTS_ASSESSMENT, assessment);
        values.put(RESTAURANTS_ADDRESS, address);
        values.put(RESTAURANTS_OPENING, opening);
        values.put(RESTAURANTS_CLOSE, close);
        values.put(RESTAURANTS_GASTRONOMY, gastronomy);
        values.put(RESTAURANTS_CATEGORY, category);

        dbW.update(table_RESTAURANTS,values, RESTAURANTS_ID + " = ?", new String[] { String.valueOf(id) });
    }


    public boolean pubsAskExist(int id) {
        Cursor c = dbR.rawQuery("SELECT " + PUBS_ID  +
                " FROM " + table_PUBS +
                " WHERE " +  PUBS_ID + " LIKE " + id, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public void pubsAdd(int id, String name, String description, Double assessment, String address, String opening, String close) {
        ContentValues values = new ContentValues();
        values.put(PUBS_ID, id);
        values.put(PUBS_NAME, name);
        values.put(PUBS_DESCRIPTION, description);
        values.put(PUBS_ASSESSMENT, assessment);
        values.put(PUBS_ADDRESS, address);
        values.put(PUBS_OPENING, opening);
        values.put(PUBS_CLOSE, close);

        dbW.insert(table_PUBS, null, values);
    }

    public void pubsUpdate(int id, String name, String description, Double assessment, String address, String opening, String close) {
        ContentValues values = new ContentValues();
        values.put(PUBS_NAME, name);
        values.put(PUBS_DESCRIPTION, description);
        values.put(PUBS_ASSESSMENT, assessment);
        values.put(PUBS_ADDRESS, address);
        values.put(PUBS_OPENING, opening);
        values.put(PUBS_CLOSE, close);

        dbW.update(table_PUBS,values, PUBS_ID + " = ?", new String[] { String.valueOf(id) });
    }


    public boolean discoAskExist(int id) {
        Cursor c = dbR.rawQuery("SELECT " + DISCOS_ID  +
                " FROM " + table_DISCOS +
                " WHERE " +  DISCOS_ID + " LIKE " + id, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public void discosAdd(int id, String name, String description, Double assessment, String address, String opening, String close, Double price) {
        ContentValues values = new ContentValues();
        values.put(DISCOS_ID, id);
        values.put(DISCOS_NAME, name);
        values.put(DISCOS_DESCRIPTION, description);
        values.put(DISCOS_ASSESSMENT, assessment);
        values.put(DISCOS_ADDRESS, address);
        values.put(DISCOS_OPENING, opening);
        values.put(DISCOS_CLOSE, close);
        values.put(DISCOS_PRICE, price);

        dbW.insert(table_DISCOS, null, values);
    }

    public void discosUpdate(int id, String name, String description, Double assessment, String address, String opening, String close, Double price) {
        ContentValues values = new ContentValues();
        values.put(DISCOS_NAME, name);
        values.put(DISCOS_DESCRIPTION, description);
        values.put(DISCOS_ASSESSMENT, assessment);
        values.put(DISCOS_ADDRESS, address);
        values.put(DISCOS_OPENING, opening);
        values.put(DISCOS_CLOSE, close);
        values.put(DISCOS_PRICE, price);

        dbW.update(table_DISCOS,values, DISCOS_ID + " = ?", new String[] { String.valueOf(id) });
    }
}