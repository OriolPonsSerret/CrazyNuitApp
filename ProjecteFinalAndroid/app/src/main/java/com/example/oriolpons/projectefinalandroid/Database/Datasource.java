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
    public static final String REMEMBERME_FIND = "_id";
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

    public static final String table_ROUTES= "routes";
    public static final String ROUTES_ID = "_id";
    public static final String ROUTES_LENGHT = "route_lenght";
    public static final String ROUTES_NAME = "name";
    public static final String ROUTES_DESCRIPTION = "description";
    public static final String ROUTES_ASSESSMENT = "assessment";
    public static final String ROUTES_CREATOR = "creator";
    public static final String ROUTES_CITY = "city";
    public static final String ROUTES_LOCALS = "rute_locals";
    public static final String ROUTES_DATE = "route_date";
    //public static final String ROUTES_FAVOURITE = "favourite";

    public static final String table_ACHIEVEMENTS= "achievements";
    public static final String ACHIEVEMENTS_ID = "_id";
    public static final String ACHIEVEMENTS_NAME = "name";
    public static final String ACHIEVEMENTS_DESCRIPTION = "description";

    public static final String table_FAVOURITEROUTES= "favouriteroutes";
    public static final String FAVOURITEROUTES_ID = "_id";
    public static final String FAVOURITEROUTES_IDUSER = "id_user";

    public static final String table_FOLLOW= "follow";
    public static final String FOLLOW_ID = "_id";
    public static final String FOLLOW_IDUSER = "id_user";

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

    // REMEMBERME
    public Cursor remembermeCount() {
        return dbR.rawQuery("SELECT COUNT(*)" +
                " FROM " + table_REMEMBERME, null);
    }

    public Cursor remembermeGetUser() {
        return dbR.rawQuery("SELECT " + REMEMBERME_EMAIL +
                " FROM " + table_REMEMBERME, null);
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
    public void DefaultRemembermeAdd() {
        ContentValues values = new ContentValues();
        values.put(REMEMBERME_FIND,"user");
        values.put(REMEMBERME_EMAIL,"");

        dbW.insert(table_REMEMBERME,null,values);
    }

    public void UserRemembermeAdd(String email) {
        String type = "user";
        ContentValues values = new ContentValues();
        values.put(REMEMBERME_FIND,"user");
        values.put(REMEMBERME_EMAIL,email);

        dbW.update(table_REMEMBERME,values, REMEMBERME_FIND + " = ?", new String[] { String.valueOf(type) });
    }

    public void RemembermeRemove() {
        String type = "user";
        ContentValues values = new ContentValues();
        values.put(REMEMBERME_EMAIL,"");

        dbW.update(table_REMEMBERME,values, REMEMBERME_FIND + " = ?", new String[] { String.valueOf(type) });
    }



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
        return dbR.rawQuery("SELECT " + PUBS_ID + ", " + PUBS_NAME + ", " + PUBS_DESCRIPTION + ", " + PUBS_ASSESSMENT + ", " + PUBS_ADDRESS + ", " + PUBS_OPENING + ", " + PUBS_CLOSE+
                " FROM " + table_PUBS +
                " WHERE UPPER(" +  PUBS_ADDRESS + ") LIKE UPPER ('" + city +"') AND UPPER(" +  PUBS_NAME + ") LIKE UPPER ('%" + localName +"%')" +
                " ORDER BY " + PUBS_ASSESSMENT + " " + order, null);
    }

    public Cursor filterLocalDisco(String city, String order, String localName) {
        return dbR.rawQuery("SELECT " + DISCOS_ID + ", " + DISCOS_NAME + ", " + DISCOS_DESCRIPTION + ", " + DISCOS_ASSESSMENT + ", " + DISCOS_ADDRESS + ", " + DISCOS_OPENING + ", " + DISCOS_CLOSE + ", " + DISCOS_PRICE +
                " FROM " + table_DISCOS +
                " WHERE UPPER(" +  DISCOS_ADDRESS + ") LIKE UPPER ('" + city +"') AND UPPER(" +  DISCOS_NAME + ") LIKE UPPER ('%" + localName +"%')" +
                " ORDER BY " + DISCOS_ASSESSMENT + " " + order, null);
    }

    public Cursor filterRoutes(String city, String order, String routeName, int route_lenghtmin, int route_lenght) {
        return dbR.rawQuery("SELECT " + ROUTES_ID + ", " + ROUTES_LENGHT +", " + ROUTES_NAME + ", " + ROUTES_DESCRIPTION + ", " + ROUTES_ASSESSMENT + ", " + ROUTES_CREATOR + ", " + ROUTES_CITY + ", " + ROUTES_LOCALS + ", " + ROUTES_DATE +
                " FROM " + table_ROUTES +
                " WHERE UPPER(" +  ROUTES_CITY + ") LIKE UPPER ('" + city +"') AND UPPER(" +  ROUTES_NAME + ") LIKE UPPER ('%" + routeName +"%') AND " +  ROUTES_LENGHT + " > " + route_lenghtmin + " AND " +  ROUTES_LENGHT + " <= " + route_lenght  +
                " ORDER BY " + ROUTES_ASSESSMENT + " " + order, null);
    }
    public Cursor filterRoutesUser(String city, String order, String routeName, int route_lenghtmin, int route_lenght, int idCreator) {
        return dbR.rawQuery("SELECT " + ROUTES_ID + ", " + ROUTES_LENGHT +", " + ROUTES_NAME + ", " + ROUTES_DESCRIPTION + ", " + ROUTES_ASSESSMENT + ", " + ROUTES_CREATOR + ", " + ROUTES_CITY + ", " + ROUTES_LOCALS + ", " + ROUTES_DATE +
                " FROM " + table_ROUTES +
                " WHERE UPPER(" +  ROUTES_CITY + ") LIKE UPPER ('" + city +"') AND UPPER(" +  ROUTES_NAME + ") LIKE UPPER ('%" + routeName +"%') AND " +  ROUTES_CREATOR + " LIKE " + idCreator +" AND " +  ROUTES_LENGHT + " > " + route_lenghtmin + " AND " +  ROUTES_LENGHT + " <= " + route_lenght  +
                " ORDER BY " + ROUTES_ASSESSMENT + " " + order, null);
    }

    public Cursor showAllRoutesUser(int idCreator) {
        return dbR.rawQuery("SELECT " + ROUTES_ID + ", " + ROUTES_LENGHT +", " + ROUTES_NAME + ", " + ROUTES_DESCRIPTION + ", " + ROUTES_ASSESSMENT + ", " + ROUTES_CREATOR + ", " + ROUTES_CITY + ", " + ROUTES_LOCALS + ", " + ROUTES_DATE +
                " FROM " + table_ROUTES +
                " WHERE UPPER(" +  ROUTES_CREATOR + ") LIKE " + idCreator+
                " ORDER BY " + ROUTES_LENGHT + " ASC", null);
    }
    public Cursor filterFavRoutes(String city, String order, String routeName, int route_lenghtmin, int route_lenght, int routeId) {
        return dbR.rawQuery("SELECT " + ROUTES_ID + ", " + ROUTES_LENGHT +", " + ROUTES_NAME + ", " + ROUTES_DESCRIPTION + ", " + ROUTES_ASSESSMENT + ", " + ROUTES_CREATOR + ", " + ROUTES_CITY + ", " + ROUTES_LOCALS + ", " + ROUTES_DATE +
                " FROM " + table_ROUTES +
                " WHERE UPPER(" +  ROUTES_CITY + ") LIKE UPPER ('" + city +"') AND UPPER(" +  ROUTES_NAME + ") LIKE UPPER ('%" + routeName +"%') AND " +  ROUTES_LENGHT + " > " + route_lenghtmin + " AND " +  ROUTES_LENGHT + " <= " + route_lenght + " AND " +  ROUTES_ID + " LIKE " + routeId +
                " ORDER BY " + ROUTES_ASSESSMENT + " " + order, null);
    }


    public Cursor searchFavRoutes(int userId) {
        return dbR.rawQuery("SELECT " + FAVOURITEROUTES_ID +
                " FROM " + table_FAVOURITEROUTES +
                " WHERE " +  FAVOURITEROUTES_IDUSER + " LIKE " + userId, null);
    }

    public Cursor searchFollowing(int userId) {
        return dbR.rawQuery("SELECT " + FOLLOW_ID +
                " FROM " + table_FOLLOW +
                " WHERE " +  FOLLOW_IDUSER + " LIKE " + userId, null);
    }
    public Cursor searchFollowed(int userId) {
        return dbR.rawQuery("SELECT " + FOLLOW_IDUSER +
                " FROM " + table_FOLLOW +
                " WHERE " +  FOLLOW_ID + " LIKE " + userId, null);
    }

    public Cursor filterUserById(int userId) {
        return dbR.rawQuery("SELECT " + USER_ID + ", " + USER_USERNAME + ", " + USER_DESCRIPTION +
                " FROM " + table_USER +
                " WHERE " +  USER_ID + " LIKE " + userId, null);
    }


    public Cursor getRouteInformation(int id) {
        return dbR.rawQuery("SELECT " + ROUTES_LENGHT +", " + ROUTES_NAME + ", " + ROUTES_DESCRIPTION + ", " + ROUTES_ASSESSMENT + ", " + ROUTES_CREATOR + ", " + ROUTES_CITY + ", " + ROUTES_LOCALS + ", " + ROUTES_DATE +
                " FROM " + table_ROUTES +
                " WHERE " +  ROUTES_ID + " LIKE " + id , null);
    }


    public Cursor getRestaurantsInformationByName(String localName) {
        return dbR.rawQuery("SELECT " + RESTAURANTS_ID +", " + RESTAURANTS_NAME + ", " + RESTAURANTS_DESCRIPTION + ", " + RESTAURANTS_ASSESSMENT + ", " + RESTAURANTS_ADDRESS + ", " + RESTAURANTS_OPENING + ", " + RESTAURANTS_CLOSE + ", " + RESTAURANTS_GASTRONOMY + ", " + RESTAURANTS_CATEGORY +
                " FROM " + table_RESTAURANTS +
                " WHERE UPPER(" +  RESTAURANTS_NAME + ") LIKE UPPER ('" + localName +"')" , null);
    }
    public Cursor getPubsInformationByName(String localName) {
        return dbR.rawQuery("SELECT " + PUBS_ID +", " + PUBS_NAME + ", " + PUBS_DESCRIPTION + ", " + PUBS_ASSESSMENT + ", " + PUBS_ADDRESS + ", " + PUBS_OPENING + ", " + PUBS_CLOSE +
                " FROM " + table_PUBS +
                " WHERE UPPER(" +  PUBS_NAME + ") LIKE UPPER ('" + localName +"')" , null);
    }
    public Cursor getDiscosInformationByName(String localName) {
        return dbR.rawQuery("SELECT " + DISCOS_ID +", " + DISCOS_NAME + ", " + DISCOS_DESCRIPTION + ", " + DISCOS_ASSESSMENT + ", " + DISCOS_ADDRESS + ", " + DISCOS_OPENING + ", " + DISCOS_CLOSE + ", " + DISCOS_PRICE +
                " FROM " + table_DISCOS +
                " WHERE UPPER(" +  DISCOS_NAME + ") LIKE UPPER ('" + localName +"')" , null);
    }

    public Cursor routeLastId(){
        return dbR.rawQuery("SELECT " + ROUTES_ID +
                " FROM " + table_ROUTES +
                " ORDER BY " + ROUTES_ID + " DESC", null);
    }


    public Cursor getAchievementById(int id) {
        return dbR.rawQuery("SELECT " + ACHIEVEMENTS_ID + ", " + ACHIEVEMENTS_NAME + ", " + ACHIEVEMENTS_DESCRIPTION +
                " FROM " + table_ACHIEVEMENTS +
                " WHERE " +  ACHIEVEMENTS_ID + " LIKE " + id , null);
    }


    public Cursor getUserInformationById(int id) {
        return dbR.rawQuery("SELECT " + USER_ID + ", " + USER_USERNAME + ", " + USER_DESCRIPTION + ", " + USER_EMAIL + ", " + USER_PHONENUMBER + ", " + USER_BIRTHDATE +
                " FROM " + table_USER +
                " WHERE " +  USER_ID + " LIKE " + id , null);
    }
    public Cursor getUserInformationByEmail(String email) {
        return dbR.rawQuery("SELECT " + USER_ID + ", " + USER_USERNAME + ", " + USER_DESCRIPTION + ", " + USER_EMAIL + ", " + USER_PHONENUMBER + ", " + USER_BIRTHDATE +
                " FROM " + table_USER +
                " WHERE UPPER(" +  USER_EMAIL + ") LIKE UPPER ('%" + email +"%')", null);
    }

    public Cursor getUserInformationByName(String name) {
        return dbR.rawQuery("SELECT " + USER_ID + ", " + USER_USERNAME + ", " + USER_DESCRIPTION + ", " + USER_EMAIL + ", " + USER_PHONENUMBER + ", " + USER_BIRTHDATE +
                " FROM " + table_USER +
                " WHERE UPPER(" +  USER_USERNAME + ") LIKE UPPER ('%" + name +"%')", null);
    }

    public void userAddedByJson(int id, String username, String description, String email, String phonenumber, String birthdate) {
        ContentValues values = new ContentValues();
        values.put(USER_ID, id);
        values.put(USER_USERNAME, username);
        values.put(USER_DESCRIPTION, description);
        values.put(USER_EMAIL, email);
        values.put(USER_PHONENUMBER, phonenumber);
        values.put(USER_BIRTHDATE, birthdate);

        dbW.insert(table_USER, null, values);
    }
    public void userUpdateByJson(int id, String username, String description, String email, String phonenumber, String birthdate) {
        ContentValues values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_DESCRIPTION, description);
        values.put(USER_EMAIL, email);
        values.put(USER_PHONENUMBER, phonenumber);
        values.put(USER_BIRTHDATE, birthdate);

        dbW.update(table_USER,values, USER_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void userAdd(String username, String email) {
        ContentValues values = new ContentValues();
        values.put(USER_ID, 0);
        values.put(USER_USERNAME, username);
        values.put(USER_DESCRIPTION, "");
        values.put(USER_EMAIL, email);
        values.put(USER_PHONENUMBER, "");
        values.put(USER_BIRTHDATE, "");

        dbW.insert(table_USER, null, values);
    }


    public void userUpdateName(String email, String username) {
        ContentValues values = new ContentValues();
        values.put(USER_USERNAME, username);

        dbW.update(table_USER,values, USER_EMAIL + " = ?", new String[] { String.valueOf(email) });
    }
    public void userUpdateDescription(String email, String description) {
        ContentValues values = new ContentValues();
        values.put(USER_DESCRIPTION, description);

        dbW.update(table_USER,values, USER_EMAIL + " = ?", new String[] { String.valueOf(email) });
    }
    public void userUpdatePhonenumber(String email, String phonenumber) {
        ContentValues values = new ContentValues();
        values.put(USER_PHONENUMBER, phonenumber);

        dbW.update(table_USER,values, USER_EMAIL + " = ?", new String[] { String.valueOf(email) });
    }
    public void userUpdateBirthdate(String email, String birthdate) {
        ContentValues values = new ContentValues();
        values.put(USER_BIRTHDATE, birthdate);

        dbW.update(table_USER,values, USER_EMAIL + " = ?", new String[] { String.valueOf(email) });
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




    public void discosAdd(int id, String name, String description, Double assessment, String address, String opening, String close, String price) {
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

    public void discosUpdate(int id, String name, String description, Double assessment, String address, String opening, String close, String price) {
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



    public void routesAdd(int id, int route_lenght, String name, String description, Double assessment, int idCreator, String city, String locals, String date) {
        ContentValues values = new ContentValues();
        values.put(ROUTES_ID, id);
        values.put(ROUTES_LENGHT, route_lenght);
        values.put(ROUTES_NAME, name);
        values.put(ROUTES_DESCRIPTION, description);
        values.put(ROUTES_ASSESSMENT, assessment);
        values.put(ROUTES_CREATOR, idCreator);
        values.put(ROUTES_CITY, city);
        values.put(ROUTES_LOCALS, locals);
        values.put(ROUTES_DATE, date);

        dbW.insert(table_ROUTES, null, values);
    }

    public void routesUpdate(int id, int route_lenght, String name, String description, Double assessment, int idCreator, String city, String locals, String date) {
        ContentValues values = new ContentValues();
        values.put(ROUTES_LENGHT, route_lenght);
        values.put(ROUTES_NAME, name);
        values.put(ROUTES_DESCRIPTION, description);
        values.put(ROUTES_ASSESSMENT, assessment);
        values.put(ROUTES_CREATOR, idCreator);
        values.put(ROUTES_CITY, city);
        values.put(ROUTES_LOCALS, locals);
        values.put(ROUTES_DATE, date);
        //values.put(ROUTES_FAVOURITE, date);

        dbW.update(table_ROUTES,values, ROUTES_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void routesUpdatedByUser(int id, String name, String description) {
        ContentValues values = new ContentValues();
        values.put(ROUTES_NAME, name);
        values.put(ROUTES_DESCRIPTION, description);

        dbW.update(table_ROUTES,values, ROUTES_ID + " = ?", new String[] { String.valueOf(id) });
    }



    public void achievementAddedByJson(int id, String name, String description) {
        ContentValues values = new ContentValues();
        values.put(ACHIEVEMENTS_ID, id);
        values.put(ACHIEVEMENTS_NAME, name);
        values.put(ACHIEVEMENTS_DESCRIPTION, description);

        dbW.insert(table_ACHIEVEMENTS, null, values);
    }
    public void achievementUpdateByJson(int id, String username, String description) {
        ContentValues values = new ContentValues();
        values.put(ACHIEVEMENTS_NAME, username);
        values.put(ACHIEVEMENTS_DESCRIPTION, description);

        dbW.update(table_ACHIEVEMENTS,values, ACHIEVEMENTS_ID + " = ?", new String[] { String.valueOf(id) });
    }


    public void addFavourite(int idRoute, int idUser) {
        ContentValues values = new ContentValues();
        values.put(FAVOURITEROUTES_ID, idRoute);
        values.put(FAVOURITEROUTES_IDUSER, idUser);

        dbW.insert(table_FAVOURITEROUTES, null, values);
    }

    public void follow(int idUserFollowed, int idUser) {
        ContentValues values = new ContentValues();
        values.put(FOLLOW_ID, idUserFollowed); //He is Followed by another User
        values.put(FOLLOW_IDUSER, idUser);

        dbW.insert(table_FOLLOW, null, values);
    }


//---------------------------------------------------------------------------------------------------------------
    //ASK EXIST
    public boolean userAskExist(String email) {
        Cursor c = dbR.rawQuery("SELECT " + USER_EMAIL  +
                " FROM " + table_USER +
                " WHERE UPPER(" +  USER_EMAIL + ") LIKE UPPER ('" + email +"')", null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean usersAskExist(int id) {
        Cursor c = dbR.rawQuery("SELECT " + USER_ID  +
                " FROM " + table_USER +
                " WHERE " +  USER_ID + " LIKE " + id, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean usernameAskExist(String name, int id) {
        Cursor c = dbR.rawQuery("SELECT " + USER_USERNAME  +
                " FROM " + table_USER +
                " WHERE " +  USER_ID + " NOT LIKE " + id + " AND UPPER(" +  USER_USERNAME + ") LIKE UPPER ('%" + name +"%')", null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean restaurantsAskExist(int id) {
        Cursor c = dbR.rawQuery("SELECT " + RESTAURANTS_ID  +
                " FROM " + table_RESTAURANTS +
                " WHERE " +  RESTAURANTS_ID + " LIKE " + id, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean restaurantsAskExistByName(String localName) {
        Cursor c = dbR.rawQuery("SELECT " + RESTAURANTS_NAME  +
                " FROM " + table_RESTAURANTS +
                " WHERE UPPER(" +  RESTAURANTS_NAME + ") LIKE UPPER ('%" + localName +"%')", null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean pubsAskExist(int id) {
        Cursor c = dbR.rawQuery("SELECT " + PUBS_ID  +
                " FROM " + table_PUBS +
                " WHERE " +  PUBS_ID + " LIKE " + id, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean pubsAskExistByName(String localName) {
        Cursor c = dbR.rawQuery("SELECT " + PUBS_NAME  +
                " FROM " + table_PUBS +
                " WHERE UPPER(" +  PUBS_NAME + ") LIKE UPPER ('%" + localName +"%')", null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean discoAskExist(int id) {
        Cursor c = dbR.rawQuery("SELECT " + DISCOS_ID  +
                " FROM " + table_DISCOS +
                " WHERE " +  DISCOS_ID + " LIKE " + id, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }
    public boolean discoAskExistByName(String localName) {
        Cursor c = dbR.rawQuery("SELECT " + DISCOS_NAME  +
                " FROM " + table_DISCOS +
                " WHERE UPPER(" +  DISCOS_NAME + ") LIKE UPPER ('%" + localName +"%')", null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean routesAskExist(int id) {
        Cursor c = dbR.rawQuery("SELECT " + ROUTES_ID  +
                " FROM " + table_ROUTES +
                " WHERE " +  ROUTES_ID + " LIKE " + id, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean routesFavAskExist(int routeId, int userId) {
        Cursor c = dbR.rawQuery("SELECT " + FAVOURITEROUTES_ID  + ", " + FAVOURITEROUTES_IDUSER +
                " FROM " + table_FAVOURITEROUTES +
                " WHERE " +  FAVOURITEROUTES_ID + " LIKE " + routeId + " AND " +  FAVOURITEROUTES_IDUSER + " LIKE " + userId, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean followAskExist(int userIdFollowed, int userId) {
        Cursor c = dbR.rawQuery("SELECT " + FOLLOW_ID  + ", " + FOLLOW_IDUSER +
                " FROM " + table_FOLLOW +
                " WHERE " +  FOLLOW_ID + " LIKE " + userIdFollowed + " AND " +  FOLLOW_IDUSER + " LIKE " + userId, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean routesAskExistName(String name) {
        Cursor c = dbR.rawQuery("SELECT " + ROUTES_NAME  +
                " FROM " + table_ROUTES +
                " WHERE UPPER(" +  ROUTES_NAME + ") LIKE UPPER ('" + name + "')", null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean achievementAskExistById(int id) {
        Cursor c = dbR.rawQuery("SELECT " + ACHIEVEMENTS_ID  +
                " FROM " + table_ACHIEVEMENTS +
                " WHERE " +  ACHIEVEMENTS_ID + " LIKE " + id, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

//---------------------------------------------------------------------------------------------------------------
    //DELETE
    public void userDelete(int id) {
        dbW.delete(table_USER,USER_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void routesDelete(int id) {
        dbW.delete(table_ROUTES,ROUTES_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void removeFavourite(int idRoute, int idUser) {
        dbW.delete(table_FAVOURITEROUTES,FAVOURITEROUTES_ID + " = ? AND " + FAVOURITEROUTES_IDUSER + " = ?", new String[] { String.valueOf(idRoute), String.valueOf(idUser) });
    }

    public void unfollow(int idUserFollowed, int idUser) {
        dbW.delete(table_FOLLOW,FOLLOW_ID + " = ? AND " + FOLLOW_IDUSER + " = ?", new String[] { String.valueOf(idUserFollowed), String.valueOf(idUser) });
    }
}