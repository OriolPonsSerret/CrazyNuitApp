package com.example.oriolpons.projectefinalandroid;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.oriolpons.projectefinalandroid.Database.Datasource;
import com.example.oriolpons.projectefinalandroid.Models.local;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class splashScreen extends Activity {
    private ProgressBar progressBar;
    int progressStatus = 0, quantity = 0;
    private Datasource bd;
    private String url = "http://10.0.2.2/ApiCrazyNuit/public/api/", type = "restaurants";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        bd = new Datasource(this);

        progressBar=(ProgressBar)findViewById(R.id.progressBar1);

        if (bd.filterConfigVerification()) {
        }
        else{
            bd.DefaultFilterConfigAdd();
        }

        Cursor cursor  = bd.remembermeCount();
        while(cursor.moveToNext()){
            quantity = cursor.getInt(0);
        }
        if (quantity == 0){
            bd.DefaultRemembermeAdd();
        }
        loading();
    }

    private void loading() {

        Thread logoTimer = new Thread(){
            public void run(){
                try{
                    int logoTimer = 0;

                    downloadDataFromApi();

                    while (logoTimer<10000){
                        sleep(100);
                        if (progressStatus < 100) {
                            progressStatus += 1;
                            progressBar.setProgress(progressStatus);
                        }
                        logoTimer=logoTimer+100;
                    }
                    //finish();
                    loginIntent();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    finish();
                }
            }
        };
        logoTimer.start();
    }
    private void loginIntent() {
        Intent i = new Intent(this, loginActivity.class );
        startActivity(i);
    }

    private void downloadDataFromApi() {
        getJsonData getJson = new getJsonData();
        getJson.execute();
    }

    private class getJsonData extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... argumentos) {

            StringBuffer bufferCadena = new StringBuffer("");

            try {
                HttpClient cliente = new DefaultHttpClient();
                HttpGet peticion = new HttpGet(url + type);
                // ejecuta una petición get
                HttpResponse respuesta = cliente.execute(peticion);

                //lee el resultado
                BufferedReader entrada = new BufferedReader(new InputStreamReader(
                        respuesta.getEntity().getContent()));
                // Log.i("ResponseObject: ", respuesta.toString());

                String separador = "";
                String NL = System.getProperty("line.separator");
                //almacena el resultado en bufferCadena

                while ((separador = entrada.readLine()) != null) {
                    bufferCadena.append(separador + NL);
                }
                entrada.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                Log.i("ResponseObject: ", e.toString());
            }

            return bufferCadena.toString();

        }

        protected void onPostExecute(String data) {

            try {
                readDataFromJson(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(data);
            // Toast.makeText(localActivity.this, data, Toast.LENGTH_SHORT).show();

        }
    }

    private void readDataFromJson(String data) throws JSONException {
        int id;
        String entrance_price = "", city = "Mataró", username = "", email = "", phonenumber = "", birthdate = "", name = "", description= "", address= "Mataró", opening_hours= "", schedule_close= "", gastronomy= "", locals = "", date = "";
        Double assessment = 1.0;
        int category = 4, route_lenght = 2, idCreator = 0;

        // Long id;
        // String type, name, description, address, opening_hours, schedule_close, gastronomy, entrance_price;
        // Double assessment;
        // int category;

        JSONArray jArray = new JSONArray(data);
        JSONObject jObject = jArray.getJSONObject(0);

        if (type.equals("restaurants")){

            for (int i = 0; i < jArray.length(); i++) {
                jObject = jArray.getJSONObject(i);

                id = (int) jObject.get("idBarRestaurant");
                name = (String) jObject.get("Nom");
                description = (String) jObject.get("Descripcio");

                if (!jObject.get("Valoracio").equals(null)){
                    int value;
                    value = (int) jObject.get("Valoracio");
                    assessment = (double) value;
                }
                else{assessment = 0.0;}
                /*
                if (jObject.get("Direccio") == null){
                    address = (String) jObject.get("Direccio");
                }
                else{ address = "Mataró";}
                if (jObject.get("Horari-Obertura") == null){
                    opening_hours = (String) jObject.get("Horari-Obertura");
                }
                else{ opening_hours = "";}
                if (jObject.get("Horari-Tancament") == null){
                    schedule_close = (String) jObject.get("Horari-Tancament");
                }
                else{ schedule_close = "";}


                gastronomy = (String) jObject.get("TipusGastronomic");
                category = (int) jObject.get("Categoria");
                */

                if (bd.restaurantsAskExist(id)){
                    bd.restaurantsUpdate(id, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category);
                }
                else{
                    bd.restaurantsAdd(id, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category);
                }
            }
            type = "pubs";
            downloadDataFromApi();
        }

        if (type.equals("pubs")){
            for (int i = 0; i < jArray.length(); i++) {
                jObject = jArray.getJSONObject(i);
                id = (int) jObject.get("idPub");
                name = (String) jObject.get("Nom");
                description = (String) jObject.get("Descripcio");
                if (!jObject.get("Valoracio").equals(null)){
                    int value;
                    value = (int) jObject.get("Valoracio");
                    assessment = (double) value;
                }
                else{assessment = 0.0;}

                if (bd.pubsAskExist(id)){
                    bd.pubsUpdate(id, name, description, assessment, address, opening_hours, schedule_close);
                }
                else{
                    bd.pubsAdd(id, name, description, assessment, address, opening_hours, schedule_close);
                }
            }
            type = "discoteques";
            downloadDataFromApi();
        }

        if (type.equals("discoteques")){
            for (int i = 0; i < jArray.length(); i++) {
                jObject = jArray.getJSONObject(i);
                id = (int) jObject.get("idDiscoteca");
                name = (String) jObject.get("Nom");
                description = (String) jObject.get("Descripcio");
                if (!jObject.get("Valoracio").equals(null)){
                    int value;
                    value = (int) jObject.get("Valoracio");
                    assessment = (double) value;
                }
                else{assessment = 0.0;}
                // address = (String) jObject.get("Direccio"); //NO me llega
                // opening_hours = (String) jObject.get("HorariObertura");
                // schedule_close = (String) jObject.get("HorariTancament");
                if (!jObject.get("PreuEntrada").equals(null)){
                    int value;
                    entrance_price = (String) jObject.get("PreuEntrada");
                }
                else{entrance_price = "";}


                // category = (int) data.get("Categoria");


                if (bd.discoAskExist(id)){
                    bd.discosUpdate(id, name, description, assessment, address, opening_hours, schedule_close, entrance_price);
                }
                else{
                    bd.discosAdd(id, name, description, assessment, address, opening_hours, schedule_close, entrance_price);
                }
            }
            type = "rutes";
            downloadDataFromApi();
        }
        if (type.equals("rutes")){
            for (int i = 0; i < jArray.length(); i++) {
                jObject = jArray.getJSONObject(i);
                id = (int) jObject.get("idrutes");
                route_lenght = (int) jObject.get("rutmida");
                name = (String) jObject.get("rutnom");
                if (!jObject.get("rutdescripcio").equals(null)){
                    description = (String) jObject.get("rutdescripcio");
                }
                else{description = "";}
                assessment = 0.0;
                /*if (!jObject.get("Valoracio").equals(null)){
                    int value;
                    value = (int) jObject.get("Valoracio");
                    assessment = (double) value;
                }
                else{assessment = 0.0;}*/
                idCreator = (int) jObject.get("rutcreador");
                if (!jObject.get("rutlocals").equals(null)){
                    locals = (String) jObject.get("rutlocals");
                }
                else{locals = "";}

                if (bd.routesAskExist(id)){
                    bd.routesUpdate(id, route_lenght, name, description, assessment, idCreator, city, locals, date);
                }
                else{
                    bd.routesAdd(id, route_lenght, name, description, assessment, idCreator, city, locals, date);
                }
            }
            type = "usuaris";
            downloadDataFromApi();
        }
        if (type.equals("usuaris")){
            for (int i = 0; i < jArray.length(); i++) {
                jObject = jArray.getJSONObject(i);
                id = (int) jObject.get("idusuaris");
                username = (String) jObject.get("name");
                if (!jObject.get("descripcio").equals(null)){
                    description = (String) jObject.get("descripcio");
                }
                else{description = "";}
                email = (String) jObject.get("email");
                if (!jObject.get("telefon").equals(null)){
                    phonenumber = (String) jObject.get("telefon");
                }
                else{phonenumber = "";}
                if (!jObject.get("DataNaixement").equals(null)){
                    birthdate = (String) jObject.get("DataNaixement");
                }
                else{birthdate = "";}


                if (bd.usersAskExist(id)){
                    bd.userUpdateByJson(id, username, description, email, phonenumber, birthdate);
                }
                else{
                    bd.userAddedByJson(id, username, description, email, phonenumber, birthdate);
                }
            }
            type = "";
            //type = "assoliments";
            //downloadDataFromApi();
        }/*
        if (type.equals("assoliments")){
            for (int i = 0; i < jArray.length(); i++) {
                jObject = jArray.getJSONObject(i);
                id = (int) jObject.get("idassoliments");
                name = (String) jObject.get("assonom");
                if (!jObject.get("assodescripcio").equals(null)){
                    description = (String) jObject.get("assodescripcio");
                }
                else{description = "";}


                if (bd.achievementAskExistById(id)){
                    bd.achievementUpdateByJson(id, name, description);
                }
                else{
                    bd.achievementAddedByJson(id, name, description);
                }
            }
            type = "";
        }*/
    }

}
