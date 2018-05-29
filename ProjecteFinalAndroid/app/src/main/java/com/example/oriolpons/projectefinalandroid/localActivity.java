package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oriolpons.projectefinalandroid.Database.Datasource;
import com.example.oriolpons.projectefinalandroid.Models.local;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterLocal;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class localActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btnHome, btnLocal, btnRoutes, btnUserProfile, btnBack;
    private Button btnMenu, btnFilterLocal;
    private ArrayList<local> listLocals;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterLocal adapterLocal;
    private RecyclerView recyclerLocals;
    private LinearLayout linearLayoutMenu;
    private  Spinner spCity;
    private int duration = 4;
    //private JSONObject data = null;
    private StringBuffer json;
    private AlertDialog dialog;
    private Datasource bd;
    private String localName, localDescription, localAssessment, NameFilter = "", typeOfLocalFilter = "restaurants", assessmentFilter = "ASC", cityOfLocalFilter= "Mataró", url =  "http://10.0.2.2/ApiCrazyNuit/public/api/";
    private int cityOfLocalFilterPosition = 0;

    private String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        bd = new Datasource(this);

        listLocals = new ArrayList<>();
        recyclerLocals = (RecyclerView) findViewById(R.id.RecyclerLocal);
        recyclerLocals.setLayoutManager(new LinearLayoutManager(this));

        btnFilterLocal = (Button) findViewById(R.id.btnFilterLocal);
        btnFilterLocal.setOnClickListener(this);

        linearLayoutMenu = (LinearLayout) findViewById(R.id.linearLayoutMenu);
        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
        btnLocal = (ImageButton) findViewById(R.id.btnLocal);
        btnLocal.setOnClickListener(this);
        btnRoutes = (ImageButton) findViewById(R.id.btnRoutes);
        btnRoutes.setOnClickListener(this);
        btnUserProfile = (ImageButton) findViewById(R.id.btnUserProfile);
        btnUserProfile.setOnClickListener(this);


        String[] arraySpinnerCity = new String[] {
                "Mataró", "Barcelona", "Girona"
        };
        spCity = (Spinner) findViewById(R.id.spCity);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arraySpinnerCity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity.setAdapter(adapter);

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                cityOfLocalFilterPosition = position;
                cityOfLocalFilter = spCity.getSelectedItem().toString();
                bd.filterConfigUpdate("local", assessmentFilter, typeOfLocalFilter, cityOfLocalFilter, cityOfLocalFilterPosition);

                //filterConfig();
                clearData();
                //getJsonData();
                getJsonData getJson = new getJsonData();
                getJson.execute();
                databaseToLocalList();
                //addLocalsToDatabase(); //
                //databaseToLocalList(); //
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        adapterLocal = new adapterLocal(listLocals);
        adapterLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localName = listLocals.get(recyclerLocals.getChildAdapterPosition(view)).getName();
                localDescription = listLocals.get(recyclerLocals.getChildAdapterPosition(view)).getDescription();
                localAssessment = listLocals.get(recyclerLocals.getChildAdapterPosition(view)).getAssessment() + "/5";
                intentLocalContent();
            }
        });
        recyclerLocals.setAdapter(adapterLocal);

        userEmail = this.getIntent().getExtras().getString("user_email");

        filterConfig();
        clearData();
        //getJsonData();
        getJsonData getJson = new getJsonData();
        getJson.execute();
        databaseToLocalList();
        //addLocalsToDatabase(); //
        //databaseToLocalList(); //


        spCity.setSelection(cityOfLocalFilterPosition);
        btnLocal.setEnabled(false);
        getSupportActionBar().setTitle("Locales en tu zona");
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
    }
*/
    private void filterConfig() {
        Cursor cursor = bd.filterConfigLocal();
        while(cursor.moveToNext()){
            assessmentFilter = cursor.getString(0);
            typeOfLocalFilter = cursor.getString(1);
            cityOfLocalFilter = cursor.getString(2);
            cityOfLocalFilterPosition = cursor.getInt(3);

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnHome: intentMain(); break;
            case R.id.btnLocal: intentLocal(); break;
            case R.id.btnRoutes: intentRoutes(); break;
            case R.id.btnUserProfile: intentUserProfile(); break;
            case R.id.btnMenu: actionShowHideMenu(); break;
            case R.id.btnBack: actionBack(); break;
            case R.id.btnFilterLocal: actionFilterLocal(); break;
        }
    }

    private void actionShowHideMenu() {

        if (linearLayoutMenu.getVisibility() == View.VISIBLE) {
            linearLayoutMenu.setVisibility(View.GONE);
        } else {
            linearLayoutMenu.setVisibility(View.VISIBLE);
        }
    }
    private void actionBack() {

        if (linearLayoutMenu.getVisibility() == View.VISIBLE) {
            linearLayoutMenu.setVisibility(View.GONE);
        } else {
            finish();
        }

    }


    private void intentMain() {
        Bundle bundle = new Bundle();
        bundle.putString("user_email", userEmail);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }
    private void intentLocal() {
        Bundle bundle = new Bundle();
        bundle.putString("user_email", userEmail);
        Intent i = new Intent(this, localActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }
    private void intentRoutes() {
        Bundle bundle = new Bundle();
        bundle.putString("user_email", userEmail);
        Intent i = new Intent(this, routesActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }
    private void intentUserProfile() {
        String userName = "";

        Cursor cursor = bd.getUserInformationByEmail(userEmail);
        while(cursor.moveToNext()){
            userName = cursor.getString(1);
        }
        Bundle bundle = new Bundle();
        bundle.putString("type","me");
        bundle.putString("userName",userName);
        bundle.putString("user_email", userEmail);
        Intent i = new Intent(this, profileActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    private void actionFilterLocal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_filter_local,null);

        final EditText edtFilterName = (EditText) view.findViewById(R.id.edtFilterLocalName);
        final RadioButton rbtnAsc = (RadioButton) view.findViewById(R.id.rbtnAsc);
        final RadioButton rbtnDes = (RadioButton) view.findViewById(R.id.rbtnDes);
        final RadioButton ckbxRestaurant = (RadioButton) view.findViewById(R.id.ckbxRestaurant);
        final RadioButton ckbxPub = (RadioButton) view.findViewById(R.id.ckbxPub);
        final RadioButton ckbxDisco = (RadioButton) view.findViewById(R.id.ckbxDisco);
        Button btnFilterLocal = (Button) view.findViewById(R.id.btnFilterLocal);

        filterConfig();
        if (assessmentFilter.equals("ASC")){
            rbtnAsc.setChecked(true);
        }
        else{
            if (assessmentFilter.equals("DESC")){
                rbtnDes.setChecked(true);
            }
        }
        if (typeOfLocalFilter.equals("restaurants")) {
            ckbxRestaurant.setChecked(true);
        }
        else{
            if (typeOfLocalFilter.equals("pubs")) {
                ckbxPub.setChecked(true);
            }
            else{
                if (typeOfLocalFilter.equals("discoteques")) {
                    ckbxDisco.setChecked(true);
                }
            }
        }

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        btnFilterLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration;
                Toast mensaje;

                if (rbtnAsc.isChecked()){
                    assessmentFilter = "ASC";
                }
                else{
                    assessmentFilter = "DESC";
                }

                if (ckbxRestaurant.isChecked()){
                    typeOfLocalFilter = "restaurants";
                }
                else{
                    if (ckbxPub.isChecked()){
                        typeOfLocalFilter = "pubs";
                    }else{

                    }
                    if (ckbxDisco.isChecked()){
                        typeOfLocalFilter = "discoteques";
                    }
                }

                NameFilter = edtFilterName.getText().toString();

                bd.filterConfigUpdate("local", assessmentFilter, typeOfLocalFilter, cityOfLocalFilter, cityOfLocalFilterPosition);
                clearData();
                //getJsonData();
                getJsonData getJson = new getJsonData();
                getJson.execute();
                databaseToLocalList();
                //addLocalsToDatabase(); //
                //databaseToLocalList(); //

                text = "Se han aplicado los filtros.";
                duration = 3;

                mensaje = Toast.makeText(context, text, duration);
                mensaje.show();
                dialog.cancel();
            }
        });
    }

    public void clearData() {
        listLocals.clear(); //clear list
        adapterLocal.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }

    private void intentLocalContent() {
        Bundle bundle = new Bundle();
        bundle.putString("name", localName);
        bundle.putString("description", localDescription);
        bundle.putString("assessment", localAssessment);
        Intent intent = new Intent(this, localContentActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void databaseToLocalList() {
        Cursor cursor;
        Long id;
        String type, name, description, address, opening_hours, schedule_close, gastronomy;
        Double assessment, entrance_price;
        int category;

        if (typeOfLocalFilter.equals("restaurants")) {
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

                listLocals.add(new local(id, type, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category, entrance_price, 0));
            }
        }
        else{
            if (typeOfLocalFilter.equals("pubs")) {
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

                    listLocals.add(new local(id, type, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category, entrance_price, 0));
                }
            }
            else{
                if (typeOfLocalFilter.equals("discoteques")) {
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

                        listLocals.add(new local(id, type, name, description, assessment, address, opening_hours, schedule_close, gastronomy, category, entrance_price, 0));
                    }
                }
            }
        }
    }

    private class getJsonData extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... argumentos) {

            StringBuffer bufferCadena = new StringBuffer("");

            try {
                HttpClient cliente = new DefaultHttpClient();
                HttpGet peticion = new HttpGet(url + typeOfLocalFilter);
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
/*
    private void getJsonData() {

        String URL = url + typeOfLocalFilter;
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest or = new JsonObjectRequest(Request.Method.GET
                , URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject data) {
                try {
                    Log.i("Data: ", data.toString());
                    readDataFromJson(data);

                    //gestionarJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i("Error Response: ", error.toString());
                    }
                }

        );
        rq.add(or);
    }*/
/*
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL = "http://10.0.2.2/CrazyNuitApi/public/api/";
                    URL = URL + typeOfLocalFilter;
                    URL url = new URL(URL);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    json = new StringBuffer(1024);
                    String tmp = "";

                    while ((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONArray(json.toString());


                    if (data.getInt("cod") != 200) {
                        System.out.println("Cancelled");
                        return null;
                    }


                } catch (Exception e) {
                    System.out.println("Data: " + data);
                    System.out.println("Exception " + e.getMessage());
                    return null;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                if (data != null) {
                    Log.d("my weather received", data.toString());

                    try {
                        readDataFromJson();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.execute();
    }
*/
    private void readDataFromJson(String data) throws JSONException {
        int id;
        String name = "", description= "", address= "Mataró", opening_hours= "", schedule_close= "", gastronomy= "";
        Double assessment = 1.0, entrance_price = 10.0;
        int category = 4;

        // Long id;
        // String type, name, description, address, opening_hours, schedule_close, gastronomy, entrance_price;
        // Double assessment;
        // int category;

        JSONArray jArray = new JSONArray(data);
        JSONObject jObject = jArray.getJSONObject(0);

        if (typeOfLocalFilter.equals("restaurants")){

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
        }

        if (typeOfLocalFilter.equals("pubs")){
            for (int i = 0; i < jArray.length(); i++) {
                jObject = jArray.getJSONObject(i);
                    id = (int) jObject.get("idPub");
                    name = (String) jObject.get("Nom");
                    description = (String) jObject.get("Descripcio");
                    if (jObject.get("Valoracio") == null){
                        assessment = (Double) jObject.get("Valoracio");
                    }else{assessment = 0.0;}


                 /*
                    if (jObject.get("Direccio") == null){
                        address = (String) jObject.get("Direccio");
                    }
                    else{ address = "Mataró";}*/
                   // opening_hours = (String) jObject.get("HorariObertura");
                   // schedule_close = (String) jObject.get("HorariTancament");

                   // gastronomy = (String) data.get("TipusGastronomic");
                   // category = (int) data.get("Categoria");

                if (bd.pubsAskExist(id)){
                    bd.pubsUpdate(id, name, description, assessment, address, opening_hours, schedule_close);
                }
                else{
                    bd.pubsAdd(id, name, description, assessment, address, opening_hours, schedule_close);
                }
            }
        }

        if (typeOfLocalFilter.equals("discoteques")){
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
        // JSONObject jsonObjectMain = (JSONObject) data.get("main");
    }
}
