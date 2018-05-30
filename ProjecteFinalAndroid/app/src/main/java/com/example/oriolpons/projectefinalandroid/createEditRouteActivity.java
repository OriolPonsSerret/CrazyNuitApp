package com.example.oriolpons.projectefinalandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oriolpons.projectefinalandroid.Database.Datasource;
import com.example.oriolpons.projectefinalandroid.Models.local;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalAdd;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class createEditRouteActivity extends Activity implements View.OnClickListener{

    private LinearLayout layoutCity, layoutRoute;
    private TextView txtCity, txtLocalsRoute, edtRouteName, edtRouteDescription;
    private ImageButton btnBack, btnDelete;
    private Button btnAccept;
    private ArrayList<local> listLocalGlobal, listLocalRoute;
    private RecyclerView LocalsGlobal, LocalsInMyRoute;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalList adapterLocalList;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalAdd adapterLocalAdd;
    private String typeF = "restaurants", url = "http://10.0.2.2/ApiCrazyNuit/public/api/", localName, cityOfLocalFilter = "Mataró", assessmentFilter = "DESC", NameFilter = "", typeOfLocalFilter;
    private Datasource bd;

    //Locals
    private Long id;
    private String type, name, description, address, opening_hours, schedule_close, gastronomy;
    private Double assessment, entrance_price;
    private int category, userId;

    //Routes
    private int routeId;
    private String routeName, routeDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_route);
        bd = new Datasource(this);

        listLocalGlobal = new ArrayList<>();
        listLocalRoute = new ArrayList<>();
        txtLocalsRoute = (TextView) findViewById(R.id.txtLocalsRoute);
        txtCity = (TextView) findViewById(R.id.txtCity);
        edtRouteName = (TextView) findViewById(R.id.edtRouteName);
        edtRouteDescription = (TextView) findViewById(R.id.edtRouteDescription);

        layoutCity = (LinearLayout) findViewById(R.id.layoutCity);
        layoutRoute = (LinearLayout) findViewById(R.id.layoutRoute);


        LocalsGlobal = (RecyclerView) findViewById(R.id.LocalsGlobal);
        LocalsGlobal.setLayoutManager(new LinearLayoutManager(this));
        LocalsInMyRoute = (RecyclerView) findViewById(R.id.LocalsInMyRoute);
        LocalsInMyRoute.setLayoutManager(new LinearLayoutManager(this));

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(this);

        btnDelete = (ImageButton) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        adapterLocalAdd = new adapterLocalAdd(listLocalGlobal);
        adapterLocalAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = LocalsGlobal.getChildAdapterPosition(view);

                NameFilter = listLocalGlobal.get(position).getName();
                id  = listLocalGlobal.get(position).getId();
                type = listLocalGlobal.get(position).getType();
                name = listLocalGlobal.get(position).getName();
                description = listLocalGlobal.get(position).getDescription();
                assessment = listLocalGlobal.get(position).getAssessment();
                address = listLocalGlobal.get(position).getAddress();
                opening_hours = listLocalGlobal.get(position).getOpening_hours();
                schedule_close = listLocalGlobal.get(position).getSchedule_close();
                gastronomy = listLocalGlobal.get(position).getGastronomy();
                category = listLocalGlobal.get(position).getCategory();
                entrance_price = listLocalGlobal.get(position).getEntrance_price();

                listLocalGlobal.remove(position);
                addLocal();
                adapterLocalAdd.notifyDataSetChanged();
                adapterLocalList.notifyDataSetChanged();
            }
        });
        LocalsGlobal.setAdapter(adapterLocalAdd);

        adapterLocalList = new adapterLocalList(listLocalRoute);
        adapterLocalList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = LocalsInMyRoute.getChildAdapterPosition(view);

                NameFilter = listLocalRoute.get(position).getName();
                id  = listLocalRoute.get(position).getId();
                type = listLocalRoute.get(position).getType();
                name = listLocalRoute.get(position).getName();
                description = listLocalRoute.get(position).getDescription();
                assessment = listLocalRoute.get(position).getAssessment();
                address = listLocalRoute.get(position).getAddress();
                opening_hours = listLocalRoute.get(position).getOpening_hours();
                schedule_close = listLocalRoute.get(position).getSchedule_close();
                gastronomy = listLocalRoute.get(position).getGastronomy();
                category = listLocalRoute.get(position).getCategory();
                entrance_price = listLocalRoute.get(position).getEntrance_price();

                listLocalRoute.remove(position);
                removeLocal();
                adapterLocalAdd.notifyDataSetChanged();
                adapterLocalList.notifyDataSetChanged();
            }
        });
        LocalsInMyRoute.setAdapter(adapterLocalList);

        routeId = this.getIntent().getExtras().getInt("id");
        cityOfLocalFilter = this.getIntent().getExtras().getString("city");

        if (routeId != -1){
            routeName = this.getIntent().getExtras().getString("name");
            routeDescription = this.getIntent().getExtras().getString("description");
            edtRouteName.setText(routeName);
            edtRouteDescription.setText(routeDescription);
            txtCity.setVisibility(View.GONE);
            txtLocalsRoute.setVisibility(View.GONE);
            layoutRoute.setVisibility(View.GONE);
            layoutCity.setVisibility(View.GONE);
            LocalsGlobal.setVisibility(View.GONE);
            LocalsInMyRoute.setVisibility(View.GONE);
        }
        else{
            userId = this.getIntent().getExtras().getInt("userId");
        }
        txtCity.setText(txtCity.getText().toString() + cityOfLocalFilter);

        downloadDataFromApi();
        clearData();
        databaseToLocalList();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnAccept: actionAcceptChanges(); break;
            case R.id.btnBack: actionBack(); break;
            case R.id.btnDelete: actionDelete(); break;
        }
    }
    private void actionAcceptChanges() {
        Cursor cursor;
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration, index = 0;
        Toast mensaje;

        if (routeId == -1){
            assessment = 0.0;
        }

        routeName = edtRouteName.getText().toString();
        if (routeName.trim().equals("")) {
            edtRouteName.setError("Debes ponerle un nombre a la ruta.");
            return;
        }
        else{
            edtRouteDescription = (TextView) findViewById(R.id.edtRouteDescription);
            routeDescription = edtRouteDescription.getText().toString();
            if (routeDescription.trim().equals("")) {
                edtRouteDescription.setError("Debes ponerle una pequeña descripción.");
                return;
            }
            else{
                if (routeDescription.trim().length() > 50) {
                    edtRouteDescription.setError("La descripción es demasiado extensa.");
                    return;
                }else{
                    int route_lenght = listLocalRoute.size(), ruta = 0;
                    String creator = "Senpai", locals = "", date = "";
                    date = Calendar.getInstance().getTime().toString();


                    if (routeId == -1){
                        if (bd.routesAskExistName(routeName)){
                            edtRouteName.setError("Ya existe una ruta con este nombre.");
                            return;


                        }else{
                            //cursor = bd.routeLastId();

                            bd.routesAdd(0, route_lenght, routeName, routeDescription, 0.0, userId, cityOfLocalFilter, locals, "");

                            //Post
                            //  route_lenght, routeName, routeDescription, assessment, creator, cityOfLocalFilter, locals, date,

                            text = "¡Se ha creado la ruta!";

                            mensaje = Toast.makeText(context, text, text.length());
                            mensaje.show();
                        }
                    }
                    else{
                        bd.routesUpdatedByUser(routeId, routeName, routeDescription);
                        text = "¡Se ha modificado la ruta!";

                        mensaje = Toast.makeText(context, text, text.length());
                        mensaje.show();

                       //Put
                        //  route_lenght, name, description, assessment, creator, cityOfLocalFilter, locals, date,
                    }
                    finish();
                }
            }
        }
    }
    private void actionBack() {
        finish();
    }

    private void actionDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Estás seguro de que quieres eliminar la ruta? Esta acción será irreversible.");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bd.routesDelete(routeId);
                finish();
            }
        });

        builder.setNegativeButton("No", null);
        builder.show();



    }

    private void databaseToLocalList() {
        Cursor cursor;

        typeOfLocalFilter = "discoteques";
        cursor = bd.filterLocalRestaurants(cityOfLocalFilter, assessmentFilter, NameFilter);
        while(cursor.moveToNext()){
            id = cursor.getLong(0);
            type = "restaurants";
            name = cursor.getString(1);
            description = cursor.getString(2);
            assessment = cursor.getDouble(3);
            address = cursor.getString(4);
            opening_hours = cursor.getString(5);
            schedule_close = cursor.getString(6);
            gastronomy = cursor.getString(7);
            category = cursor.getInt(8);
            entrance_price = 0.0;

            listLocalGlobal.add(new local(id, type, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category, entrance_price, 0));
        }

        typeOfLocalFilter = "pubs";
        cursor = bd.filterLocalPub(cityOfLocalFilter, assessmentFilter, NameFilter);
        while(cursor.moveToNext()){
            id = cursor.getLong(0);
            type = "pubs";
            name = cursor.getString(1);
            description = cursor.getString(2);
            assessment = cursor.getDouble(3);
            address = cursor.getString(4);
            opening_hours = cursor.getString(5);
            schedule_close = cursor.getString(6);
            gastronomy = "";
            category = 0;
            entrance_price = 0.0;

            listLocalGlobal.add(new local(id, type, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category, entrance_price, 0));
        }


        typeOfLocalFilter = "discoteques";
        cursor = bd.filterLocalDisco(cityOfLocalFilter, assessmentFilter, NameFilter);
        while(cursor.moveToNext()){
            id = cursor.getLong(0);
            type = "discoteques";
            name = cursor.getString(1);
            description = cursor.getString(2);
            assessment = cursor.getDouble(3);
            address = cursor.getString(4);
            opening_hours = cursor.getString(5);
            schedule_close = cursor.getString(6);
            gastronomy = "";
            category = 0;
            entrance_price = cursor.getDouble(7);

            listLocalGlobal.add(new local(id, type, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category, entrance_price, 0));
        }

    }
    public void clearData() {
        listLocalRoute.clear(); //clear list
        adapterLocalList.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }

    private void addLocal() {
        listLocalRoute.add(new local(id, type, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category, entrance_price,0));
    }
    private void removeLocal() {
        listLocalGlobal.add(new local(id, type, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category, entrance_price,0));
    }

    private class getJsonData extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... argumentos) {

            StringBuffer bufferCadena = new StringBuffer("");

            try {
                HttpClient cliente = new DefaultHttpClient();
                HttpGet peticion = new HttpGet(url + typeF);
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
        String city = "Mataró", username = "", email = "", phonenumber = "", birthdate = "", name = "", description= "", address= "Mataró", opening_hours= "", schedule_close= "", gastronomy= "", locals = "", date = "";
        Double assessment = 1.0, entrance_price = 10.0;
        int category = 4, route_lenght = 2, idCreator = 0;

        // Long id;
        // String type, name, description, address, opening_hours, schedule_close, gastronomy, entrance_price;
        // Double assessment;
        // int category;

        JSONArray jArray = new JSONArray(data);
        JSONObject jObject = jArray.getJSONObject(0);

        if (typeF.equals("restaurants")){

            for (int i = 0; i < jArray.length(); i++) {
                jObject = jArray.getJSONObject(i);

                id = (int) jObject.get("idBarRestaurant");
                name = (String) jObject.get("Nom");
                description = (String) jObject.get("Descripcio");

                if (jObject.get("Valoracio") == null){
                    assessment = (Double) jObject.get("Valoracio");
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
            typeF = "pubs";
            downloadDataFromApi();
        }

        if (typeF.equals("pubs")){
            for (int i = 0; i < jArray.length(); i++) {
                jObject = jArray.getJSONObject(i);
                id = (int) jObject.get("idPub");
                name = (String) jObject.get("Nom");
                description = (String) jObject.get("Descripcio");
                if (jObject.get("Valoracio") == null){
                    assessment = (Double) jObject.get("Valoracio");
                }else{assessment = 0.0;}

                if (bd.pubsAskExist(id)){
                    bd.pubsUpdate(id, name, description, assessment, address, opening_hours, schedule_close);
                }
                else{
                    bd.pubsAdd(id, name, description, assessment, address, opening_hours, schedule_close);
                }
            }
            typeF = "discoteques";
            downloadDataFromApi();
        }

        if (typeF.equals("discoteques")){
            for (int i = 0; i < jArray.length(); i++) {
                jObject = jArray.getJSONObject(i);
                id = (int) jObject.get("idDiscoteca");
                name = (String) jObject.get("Nom");
                description = (String) jObject.get("Descripcio");
                if (jObject.get("Valoracio") == null){
                    assessment = (Double) jObject.get("Valoracio");
                }else{assessment = 0.0;}
                // address = (String) jObject.get("Direccio"); //NO me llega
                // opening_hours = (String) jObject.get("HorariObertura");
                // schedule_close = (String) jObject.get("HorariTancament");
                if (jObject.get("Valoracio") == null){
                    entrance_price = (Double) jObject.get("PreuEntrada");
                }else{entrance_price = 0.0;}

                // category = (int) data.get("Categoria");


                if (bd.discoAskExist(id)){
                    bd.discosUpdate(id, name, description, assessment, address, opening_hours, schedule_close, entrance_price);
                }
                else{
                    bd.discosAdd(id, name, description, assessment, address, opening_hours, schedule_close, entrance_price);
                }
            }
        }
    }

    private void downloadDataFromApi() {
        getJsonData getJson = new getJsonData();
        getJson.execute();
    }

/*
    private void addLocalsToDatabase() {
        String name = "", description= "", address= "", opening_hours= "", schedule_close= "", gastronomy= "";
        Double assessment = 1.0, entrance_price = 10.0;
        int category = 4;


        for(int id = 0; id <= 30; id++){

            assessment = assessment + 0.2;
            if (id >= 0 && id <= 10){
                name = "Restaurante: " + id;
                description= "Un restaurante con los mejores chefs.";
                if (id <= 3){
                    address= "Mataró";
                }
                if (id >= 4 && id <= 7){
                    address= "Barcelona";
                }
                if (id >= 8 && id <= 10){
                    address= "Girona";
                }
                if (bd.restaurantsAskExist(id)){
                    bd.restaurantsUpdate(id, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category);
                }
                else{
                    bd.restaurantsAdd(id, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category);
                }
            }

            if (id >= 11 && id <= 20){
                name = "Pub: " + id;
                description= "Un pub interesante.";
                if (id <= 13){
                    address= "Mataró";
                }
                if (id >= 14 && id <= 17){
                    address= "Barcelona";
                }
                if (id >= 18 && id <= 20){
                    address= "Girona";
                }
                if (bd.pubsAskExist(id)){
                    bd.pubsUpdate(id, name, description, assessment, address, opening_hours, schedule_close);
                }
                else{
                    bd.pubsAdd(id, name, description, assessment, address, opening_hours, schedule_close);
                }
            }
            if (id >= 21 && id <= 30){
                name = "Disco: " + id;
                description= "Una disco con música de ánime.";
                if (id <= 23){
                    address= "Mataró";
                }
                if (id >= 24 && id <= 27){
                    address= "Barcelona";
                }
                if (id >= 28 && id <= 30){
                    address= "Girona";
                }
                if (bd.discoAskExist(id)){
                    bd.discosUpdate(id, name, description, assessment, address, opening_hours, schedule_close, entrance_price);
                }
                else{
                    bd.discosAdd(id, name, description, assessment, address, opening_hours, schedule_close, entrance_price);
                }
            }
        }
    }*/
}
