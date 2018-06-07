package com.example.oriolpons.projectefinalandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterLocal;
import com.example.oriolpons.projectefinalandroid.Database.Datasource;
import com.example.oriolpons.projectefinalandroid.Models.local;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalAdd;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;

public class createEditRouteActivity extends Activity implements View.OnClickListener{

    private LinearLayout layoutCity, layoutRoute;
    private TextView txtCity, txtLocalsRoute, edtRouteName, edtRouteDescription;
    private ImageButton btnBack, btnDelete;
    private Button btnAccept;
    private ArrayList<local> listLocalGlobal, listLocalRoute, listLocals;
    private RecyclerView LocalsGlobal, LocalsInMyRoute, recyclerLocals;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalList adapterLocalList;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalAdd adapterLocalAdd;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterLocal adapterLocal;
    private String localDescription, localAssessment, typeF = "restaurants", url = "http://10.0.2.2/ApiCrazyNuit/public/api/", localName, cityOfLocalFilter = "Mataró", assessmentFilter = "DESC", NameFilter = "", typeOfLocalFilter;
    private Datasource bd;
    private String[] locals;

    //Locals
    private Long id;
    private String type, name, description, address, opening_hours, schedule_close, gastronomy, entrance_price;
    private Double assessment;
    private int category;

    //Routes
    private int routeId, routeAssessment;
    private String routeName = "", routeDescription = ""; //listLocalNameInRoute = "";
    private int route_lenght = 0, userId = 0;
    private String listLocalNameInRoute = "", routeDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_route);
        bd = new Datasource(this);

        listLocals = new ArrayList<>();
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
        recyclerLocals = (RecyclerView) findViewById(R.id.recyclerLocals);
        recyclerLocals.setLayoutManager(new LinearLayoutManager(this));

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



        adapterLocal = new adapterLocal(listLocals);
        adapterLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*localName = listLocals.get(recyclerLocals.getChildAdapterPosition(view)).getName();
                localDescription = listLocals.get(recyclerLocals.getChildAdapterPosition(view)).getDescription();
                localAssessment = listLocals.get(recyclerLocals.getChildAdapterPosition(view)).getAssessment() + "/5";*/
            }
        });
        recyclerLocals.setAdapter(adapterLocal);

        routeId = this.getIntent().getExtras().getInt("id");
        cityOfLocalFilter = this.getIntent().getExtras().getString("city");



        if(isConnected()){
        }

        if (routeId != -1){
            routeName = this.getIntent().getExtras().getString("name");
            routeDescription = this.getIntent().getExtras().getString("description");
            edtRouteName.setText(routeName);
            edtRouteDescription.setText(routeDescription);


            Cursor cursor = bd.getRouteInformation(routeId);
            while(cursor.moveToNext()) {
                route_lenght = cursor.getInt(0);
                locals = cursor.getString(6).split(",");
            }
            cursor = bd.getRouteInformation(routeId);
            while(cursor.moveToNext()){
                route_lenght = cursor.getInt(0);
                routeName = cursor.getString(1);
                routeDescription = cursor.getString(2);
                routeAssessment = cursor.getInt(3);
                userId = cursor.getInt(4);
                listLocalNameInRoute = cursor.getString(5);
                routeDate = cursor.getString(6);
            }
            setLocalsInRoute();

            txtCity.setVisibility(View.GONE);
            txtLocalsRoute.setVisibility(View.GONE);
            layoutRoute.setVisibility(View.GONE);
            layoutCity.setVisibility(View.GONE);
            LocalsGlobal.setVisibility(View.GONE);
            LocalsInMyRoute.setVisibility(View.GONE);
            recyclerLocals.setVisibility(View.VISIBLE);
        }
        else{
            btnDelete.setVisibility(View.GONE);

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
                    int ruta = 0;
                    String creator = "Senpai", locals = "", date = "";
                    date = Calendar.getInstance().getTime().toString();
                    route_lenght = listLocalRoute.size();

                    if (routeId == -1){
                        if (bd.routesAskExistName(routeName)){
                            edtRouteName.setError("Ya existe una ruta con este nombre.");
                            return;


                        }else{

                            setLocalsFromList();
                            new HttpAsyncTask().execute("http://10.0.2.2/ApiCrazyNuit/public/api/rutes");




                           // bd.routesAdd(0, route_lenght, routeName, routeDescription, 0.0, userId, cityOfLocalFilter, locals, "");


                            //Post
                            //  route_lenght, routeName, routeDescription, assessment, creator, cityOfLocalFilter, locals, date,
/*
                            text = "¡Se ha creado la ruta!";

                            mensaje = Toast.makeText(context, text, text.length());
                            mensaje.show();*/
                        }
                    }
                    else{
                        bd.routesUpdatedByUser(routeId, routeName, routeDescription);
                        cursor = bd.getRouteInformation(routeId);
                        while(cursor.moveToNext()){
                            route_lenght = cursor.getInt(0);
                            routeName = cursor.getString(1);
                            routeDescription = cursor.getString(2);
                            routeAssessment = cursor.getInt(3);
                            userId = cursor.getInt(4);
                            listLocalNameInRoute = cursor.getString(5);
                            routeDate = cursor.getString(6);
                        }
                        new HttpAsyncTaskUpdate().execute("http://10.0.2.2/ApiCrazyNuit/public/api/rutes/" + routeId);
/*
                        RequestQueue queue = Volley.newRequestQueue(this);
                        StringRequest putRequest = new StringRequest(Request.Method.PUT, "http://10.0.2.2/ApiCrazyNuit/public/api/rutes/" + routeId,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                        Log.d("Response", response);
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError response) {
                                        // error
                                        Log.d("Error.Response", String.valueOf(response));
                                    }
                                }
                        ) {

                            @Override
                            protected Map<String, String> getParams()
                            {
                                Map<String, String>  params = new HashMap<String, String>();
                                params.put("idrutes", String.valueOf(routeId));
                                params.put("rutmida", String.valueOf(route_lenght));
                                params.put("rutnom", routeName);
                                params.put("rutdescripcio", routeDescription);
                                params.put("rutvaloracio",  String.valueOf(routeAssessment));
                                params.put("rutcreador", String.valueOf(userId));
                                params.put("rutlocals", listLocalNameInRoute);
                                params.put("rutdata", null);

                                return params;
                            }

                        };

                        queue.add(putRequest);
                        finish();*/
                    }
                    //finish();
                }
            }
        }
    }

    private void setLocalsFromList() {
        listLocalNameInRoute = "";
        for (int index = 0; index < listLocalRoute.size(); index++){
            listLocalNameInRoute = listLocalNameInRoute +  listLocalRoute.get(index).getName() + ",";
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
                new HttpAsyncTaskDelete().execute("http://10.0.2.2/ApiCrazyNuit/public/api/rutes/" + routeId);

//                finish();
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
            entrance_price = "";

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
            entrance_price = "";

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
            entrance_price = cursor.getString(7);

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
        String entrance_price = "", city = "Mataró", username = "", email = "", phonenumber = "", birthdate = "", name = "", description= "", address= "Mataró", opening_hours= "", schedule_close= "", gastronomy= "", locals = "", date = "";
        Double assessment = 1.0;
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

                if (!jObject.get("Valoracio").equals(null)){
                    int value;
                    value = (int) jObject.get("Valoracio");
                    assessment = (double) value;
                }
                else{assessment = 0.0;}

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
            typeF = "discoteques";
            downloadDataFromApi();
        }

        if (typeF.equals("discoteques")){
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
        }
        if (typeF.equals("rutes")){
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
            finish();
        }


    }

    private void downloadDataFromApi() {
        getJsonData getJson = new getJsonData();
        getJson.execute();
    }




    private void setLocalsInRoute() {
        long localId = 0;
        int localcategory = 0;
        String localtype = "", localname = "", localdescription = "", localaddress = "", localopening_hours = "", localschedule_close = "", localgastronomy = "", localentrance_price = "";
        Double localassessment = 0.0;
        for (int i = 0; i < locals.length;i++){
            if (bd.restaurantsAskExistByName(locals[i])){
                Cursor cursor3 = bd.getRestaurantsInformationByName(locals[i]);
                while(cursor3.moveToNext()) {
                    localId = cursor3.getLong(0);
                    localtype = "restaurants";
                    localname = cursor3.getString(1);
                    localdescription = cursor3.getString(2);
                    localassessment = cursor3.getDouble(3);
                    localaddress = cursor3.getString(4);
                    localopening_hours = cursor3.getString(5);
                    localschedule_close = cursor3.getString(6);
                    localgastronomy = cursor3.getString(7);
                    localcategory = cursor3.getInt(8);
                    localentrance_price = "";
                    listLocals.add(new local(localId, localtype, localname, localdescription, localassessment, localaddress, localopening_hours, localschedule_close, localgastronomy, localcategory, localentrance_price, 0));
                }
            }
            else{
                if (bd.pubsAskExistByName(locals[i])){
                    Cursor cursor3 = bd.getPubsInformationByName(locals[i]);
                    while(cursor3.moveToNext()) {
                        localId = cursor3.getLong(0);
                        localtype = "pubs";
                        localname = cursor3.getString(1);
                        localdescription = cursor3.getString(2);
                        localassessment = cursor3.getDouble(3);
                        localaddress = cursor3.getString(4);
                        localopening_hours = cursor3.getString(5);
                        localschedule_close = cursor3.getString(6);
                        localgastronomy = "";
                        localcategory = 0;
                        localentrance_price = "";
                        listLocals.add(new local(localId, localtype, localname, localdescription, localassessment, localaddress, localopening_hours, localschedule_close, localgastronomy, localcategory, localentrance_price, 0));
                    }
                }else{
                    if (bd.discoAskExistByName(locals[i])){
                        Cursor cursor3 = bd.getDiscosInformationByName(locals[i]);
                        while(cursor3.moveToNext()) {
                            localId = cursor3.getLong(0);
                            localtype = "discoteques";
                            localname = cursor3.getString(1);
                            localdescription = cursor3.getString(2);
                            localassessment = cursor3.getDouble(3);
                            localaddress = cursor3.getString(4);
                            localopening_hours = cursor3.getString(5);
                            localschedule_close = cursor3.getString(6);
                            localgastronomy = "";
                            localcategory = 0;
                            localentrance_price = cursor3.getString(7);
                            listLocals.add(new local(localId, localtype, localname, localdescription, localassessment, localaddress, localopening_hours, localschedule_close, localgastronomy, localcategory, localentrance_price, 0));
                        }
                    }
                }
            }
        }


    }











    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }



    //POST
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "¡Se ha creado la ruta correctamente!", Toast.LENGTH_LONG).show();
            typeF = "rutes";
            downloadDataFromApi();
        }
    }

    //PUT
    private class HttpAsyncTaskUpdate extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return PUT(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "¡Se han aplicado los cambios a la ruta!", Toast.LENGTH_LONG).show();
            typeF = "rutes";
            downloadDataFromApi();
        }
    }
    //DELETE
    private class HttpAsyncTaskDelete extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return DELETE(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "¡Se ha eliminado la ruta!", Toast.LENGTH_LONG).show();
            finish();
        }
    }












    public String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("rutmida", route_lenght);
            jsonObject.accumulate("rutnom", routeName);
            jsonObject.accumulate("rutdescripcio", routeDescription);
            jsonObject.accumulate("rutvaloracio", 0);
            jsonObject.accumulate("rutcreador", userId);
            jsonObject.accumulate("rutlocals", listLocalNameInRoute);


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    public String PUT (String url){
        InputStream inputStream = null;
        String result = "";
/*
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPut httpPUT = new
                    HttpPut(url);
            String json = "";
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idrutes", routeId);
            jsonObject.put("rutmida", route_lenght);
            jsonObject.put("rutnom", routeName);
            jsonObject.put("rutdescripcio", routeDescription);
            jsonObject.put("rutvaloracio", routeAssessment);
            jsonObject.put("rutcreador", userId);
            jsonObject.put("rutlocals", listLocalNameInRoute);
            jsonObject.put("rutdata", null);//routeDate

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPUT.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPUT.setHeader("Accept", "application/json");
            httpPUT.setHeader("Content-type", "application/json");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPUT);
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
*/


        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make PUT request to the given URL
            HttpPut httpPut = new HttpPut(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            //jsonObject.accumulate("idrutes", routeId);
            //jsonObject.put("rutmida", route_lenght);
            jsonObject.put("rutnom", routeName);
            jsonObject.put("rutdescripcio", routeDescription);
           // jsonObject.put("rutvaloracio", routeAssessment);
            //jsonObject.put("rutcreador", userId);
            //jsonObject.put("rutlocals", listLocalNameInRoute);
            //jsonObject.put("rutdata", null);//routeDate


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPut Entity
            httpPut.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");

            // 8. Execute PUT request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPut);
            //ResponseObject:: org.apache.http.conn.HttpHostConnectException: Connection to http://localhost refused

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        Log.d("InputStream", result.toString());
        // 11. return result
        return result;
    }

    public static String DELETE (String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make DELETE request to the given URL
            HttpDelete httpDelete = new HttpDelete(url);

            // 3. Set some headers to inform server about the type of the content
            httpDelete.setHeader("Accept", "application/json");
            httpDelete.setHeader("Content-type", "application/json");

            // 4. Execute DELETE request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpDelete);

            // 5. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 6. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

















    private class getJsonDataRoutes extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... argumentos) {

            StringBuffer bufferCadena = new StringBuffer("");

            try {
                HttpClient cliente = new DefaultHttpClient();
                HttpGet peticion = new HttpGet("http://10.0.2.2/ApiCrazyNuit/public/api/rutes");
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
}
