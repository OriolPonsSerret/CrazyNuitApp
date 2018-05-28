package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
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
import com.example.oriolpons.projectefinalandroid.Models.routes;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterRoutes;
/*
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class routesActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton fltBtnFavourite;
    private ImageButton btnHome, btnRoutes, btnLocal, btnUserProfile, btnBack;
    private Button btnMenu, btnMyRoutes, btnFilter;
    private ArrayList<routes> listRoutes;
    private RecyclerView recyclerRoutes;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterRoutes adapterRoutes;
    private LinearLayout linearLayoutMenu;
    private Spinner spCity;
    private StringBuffer json;
    private String routeName, routeDescription, routeCreator, routeAssessment, NameFilter = "";
    private double assessment;
    private AlertDialog dialog;
    private JSONObject data = null;

    private Datasource bd;
    private String assessmentFilter = "ASC", typeOfRouteFilter = "short", cityOfRouteFilter= "Mataró", URL =  "http://localhost/CrazyNuitApi/public/api/rutes/";
    private int cityOfRouteFilterPosition = 0, RouteLenghtMin = 0, RouteLenght = 0, routeId;
    private String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        bd = new Datasource(this);
        listRoutes = new ArrayList<>();

        recyclerRoutes = (RecyclerView) findViewById(R.id.RecyclerRoutes);
        recyclerRoutes.setLayoutManager(new LinearLayoutManager(this));

        linearLayoutMenu = (LinearLayout) findViewById(R.id.linearLayoutMenu);

        btnFilter = (Button) findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(this);

        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnMyRoutes = (Button) findViewById(R.id.btnMyRoutes);
        btnMyRoutes.setOnClickListener(this);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
        btnLocal = (ImageButton) findViewById(R.id.btnLocal);
        btnLocal.setOnClickListener(this);
        btnRoutes = (ImageButton) findViewById(R.id.btnRoutes);
        btnRoutes.setOnClickListener(this);
        btnUserProfile = (ImageButton) findViewById(R.id.btnUserProfile);
        btnUserProfile.setOnClickListener(this);

        adapterRoutes = new adapterRoutes(listRoutes);
        adapterRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                routeId = listRoutes.get(recyclerRoutes.getChildAdapterPosition(view)).getId();
                routeName = listRoutes.get(recyclerRoutes.getChildAdapterPosition(view)).getName();
                routeDescription = listRoutes.get(recyclerRoutes.getChildAdapterPosition(view)).getDescription();
                routeAssessment = listRoutes.get(recyclerRoutes.getChildAdapterPosition(view)).getAssessment() + "/5";
                routeCreator = listRoutes.get(recyclerRoutes.getChildAdapterPosition(view)).getCreator();
                intentRouteContent();
            }
        });
        recyclerRoutes.setAdapter(adapterRoutes);

        fltBtnFavourite = (FloatingActionButton) findViewById(R.id.fltBtnFavourite);
        fltBtnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFavouriteRoutes();
            }
        });


        String[] arraySpinnerCity = new String[] {
                "Mataró", "Barcelona", "Girona"
        };
        spCity = (Spinner) findViewById(R.id.spCity);

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                cityOfRouteFilterPosition = position;
                cityOfRouteFilter = spCity.getSelectedItem().toString();
                bd.filterConfigUpdate("routes", assessmentFilter, typeOfRouteFilter, cityOfRouteFilter, cityOfRouteFilterPosition);

                clearData();
                //getJsonData();
                addDBRoutes();
                databaseToRouteList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arraySpinnerCity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity.setAdapter(adapter);

        userEmail = this.getIntent().getExtras().getString("user_email");

        filterConfig();
        clearData();
        //getJsonData();
        addDBRoutes();
        databaseToRouteList();

        spCity.setSelection(cityOfRouteFilterPosition);
        btnRoutes.setEnabled(false);

        getSupportActionBar().setTitle("Rutas globales");
    }


    private void addDBRoutes() {
        String name = "", description= "", creator= "Senpai", city= "", locals = "", date = "", favourite = "FALSE";
        Double assessment = 1.0;
        int route_lenght = 2;

        for(int id = 0; id <= 15; id++){

            assessment = assessment + 0.2;
            if (id >= 0 && id <= 15){
                name = "Ruta: " + id;
                description= "Una ruta entretenida.";
                if (id <= 3){
                    city= "Mataró";
                    assessment = 5.0;
                    route_lenght = id;
                }
                if (id >= 4 && id <= 7){
                    city= "Mataró";
                    assessment = 3.0;
                    route_lenght = id;
                }
                if (id >= 8 && id <= 10){
                    city= "Mataró";
                    assessment = 2.0;
                    route_lenght = id;
                }
                if (id >= 11 && id <= 15){
                    city= "Mataró";
                    assessment = 4.0;
                    route_lenght = 3;
                    creator= "Onii-chan";
                }
                if (bd.routesAskExist(id)){
                    bd.routesUpdate(id, route_lenght, name, description, assessment, creator, city, locals, date, "FALSE");
                }
                else{
                    bd.routesAdd(id, route_lenght, name, description, assessment, creator, city, locals, date, favourite);
                }
            }
        }
    }


    private void intentRouteContent() {
        Bundle bundle = new Bundle();
        bundle.putInt("id",routeId);
        bundle.putString("name",routeName);
        bundle.putString("description",routeDescription);
        bundle.putString("assessment",routeAssessment);
        bundle.putString("creator",routeCreator);
        Intent intent = new Intent(this, routesContentActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void loadFavouriteRoutes() {
        Bundle bundle = new Bundle();
        bundle.putString("user_email", userEmail);
        Intent i = new Intent(this, favRoutesActivity.class);
        i.putExtras(bundle);
        startActivity(i);
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
            case R.id.btnMyRoutes: actionMyRoutes(); break;
            case R.id.btnFilter: actionFilterRoute(); break;
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

    private void actionFilterRoute() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_filter_route,null);

        final EditText edtFilterName = (EditText) view.findViewById(R.id.edtFilterRouteName);
        final RadioButton rbtnAsc = (RadioButton) view.findViewById(R.id.rbtnAsc);
        final RadioButton rbtnDes = (RadioButton) view.findViewById(R.id.rbtnDes);
        final RadioButton ckbxShort = (RadioButton) view.findViewById(R.id.ckbxShort);
        final RadioButton ckbxHalfways = (RadioButton) view.findViewById(R.id.ckbxHalfways);
        final RadioButton ckbxLong = (RadioButton) view.findViewById(R.id.ckbxLong);
        Button btnFilterRoute = (Button) view.findViewById(R.id.btnFilterRoute);

        filterConfig();
        if (assessmentFilter.equals("ASC")){
            rbtnAsc.setChecked(true);
        }
        else{
            if (assessmentFilter.equals("DESC")){
                rbtnDes.setChecked(true);
            }
        }
        if (typeOfRouteFilter.equals("short")) {
            ckbxShort.setChecked(true);
            RouteLenghtMin = 0;
            RouteLenght = 3;
        }
        else{
            if (typeOfRouteFilter.equals("halfways")) {
                ckbxHalfways.setChecked(true);
                RouteLenghtMin = 3;
                RouteLenght = 6;
            }
            else{
                if (typeOfRouteFilter.equals("long")) {
                    ckbxLong.setChecked(true);
                    RouteLenghtMin = 6;
                    RouteLenght = 10;
                }
            }
        }

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        btnFilterRoute.setOnClickListener(new View.OnClickListener() {
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

                if (ckbxShort.isChecked()){
                    typeOfRouteFilter = "short";
                    RouteLenghtMin = 0;
                    RouteLenght = 3;
                }
                else{
                    if (ckbxHalfways.isChecked()){
                        typeOfRouteFilter = "halfways";
                        RouteLenghtMin = 3;
                        RouteLenght = 6;
                    }else{

                    }
                    if (ckbxLong.isChecked()){
                        typeOfRouteFilter = "long";
                        RouteLenghtMin = 6;
                        RouteLenght = 10;
                    }
                }

                NameFilter = edtFilterName.getText().toString();

                bd.filterConfigUpdate("routes", assessmentFilter, typeOfRouteFilter, cityOfRouteFilter, cityOfRouteFilterPosition);
                clearData();
                //getJsonData();
                addDBRoutes();
                databaseToRouteList();

                text = "Se han aplicado los filtros.";
                duration = 3;

                mensaje = Toast.makeText(context, text, duration);
                mensaje.show();

                dialog.cancel();
            }
        });
    }

    private void actionMyRoutes() {
        Bundle bundle = new Bundle();
        bundle.putString("user_email", userEmail);
        Intent i = new Intent(this, myRoutesActivity.class);
        i.putExtras(bundle);
        startActivity(i);
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


    private void filterConfig() {
        Cursor cursor = bd.filterConfigRoute();
        while(cursor.moveToNext()){
            assessmentFilter = cursor.getString(0);
            typeOfRouteFilter = cursor.getString(1);
            cityOfRouteFilter = cursor.getString(2);
            cityOfRouteFilterPosition = cursor.getInt(3);
        }
    }
    public void clearData() {
        listRoutes.clear(); //clear list
        adapterRoutes.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }
    private void databaseToRouteList() {

        Cursor cursor;
        int id;
        String measure = "", name, description, creator, city, rute_locals, route_date;
        Double assessment, entrance_price;
        int route_lenght;

        if (typeOfRouteFilter.equals("short")) {
            RouteLenghtMin = 0;
            RouteLenght = 3;
        }
        else{
            if (typeOfRouteFilter.equals("halfways")) {
                RouteLenghtMin = 3;
                RouteLenght = 6;
            }
            else{
                if (typeOfRouteFilter.equals("long")) {
                    RouteLenghtMin = 6;
                    RouteLenght = 10;
                }
            }
        }

        cursor = bd.filterRoutes(cityOfRouteFilter, assessmentFilter, NameFilter, RouteLenghtMin, RouteLenght);
        while(cursor.moveToNext()){
            id = cursor.getInt(0);
            route_lenght = cursor.getInt(1);
            name = cursor.getString(2);
            description = cursor.getString(3);
            assessment = cursor.getDouble(4);
            creator = cursor.getString(5);
            city = cursor.getString(6);
            rute_locals = cursor.getString(7);
            route_date = cursor.getString(8);

            if (route_lenght <= 3){
                measure = "short";
            }else{
                if (route_lenght <= 6){
                    measure = "halfways";
                }else{
                    if (route_lenght <= 10){
                        measure = "long";
                    }
                }
            }

            listRoutes.add(new routes(id, measure, name, description, creator, assessment, city, 0));
        }
    }


    private void getJsonData() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    java.net.URL url = new URL(URL);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    json = new StringBuffer(1024);
                    String tmp = "";

                    while ((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());

                    if (data.getInt("cod") != 200) {
                        System.out.println("Cancelled");
                        return null;
                    }


                } catch (Exception e) {

                    System.out.println("Exception " + e.getMessage());
                    // mostrarOpcion();
                    return null;
                }

                return null;
            }
            /*
                        private void mostrarOpcion() {
                            Context context = getApplicationContext();
                            Toast mensaje;
                            duration = 4;

                            String text = "No existeix una ciutat amb aquest nom.";
                            mensaje = Toast.makeText(context, text, duration);
                            mensaje.show();
                        }
            */
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

    private void readDataFromJson() throws JSONException {
        int id, idLocal;
        String name = "", description= "", creator= "Senpai", city= "", locals = "", date = "", favourite = "FALSE";
        Double assessment = 1.0;
        int route_lenght = 2;

        JSONArray jArray = new JSONArray(data);


        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObject = jArray.getJSONObject(i);
            id = (int) jObject.get("idrutes");
            route_lenght = (int) jObject.get("rutmida");
            name = (String) jObject.get("nombre");
            description = (String) jObject.get("descripcion");
            assessment = (Double) jObject.get("rutvaloracio");
            creator = (String) jObject.get("creador");
            city = (String) jObject.get("ciudad");
            locals = (String) jObject.get("rutlocals");// Desconozco si vendrá con []
            date = (String) jObject.get("rutdata");

            //En el caso de rutlocals[]
            JSONArray jArrayLocals = new JSONArray("rutlocals");
            for (int i2 = 0; i2 < jArrayLocals.length(); i2++) {
                JSONObject jObjectLocals = jArrayLocals.getJSONObject(i);

            }

            if (bd.routesAskExist(id)){
                bd.routesUpdate(id, route_lenght, name, description, assessment, creator, city, locals, date, "FALSE");
            }
            else{
                bd.routesAdd(id, route_lenght, name, description, assessment, creator, city, locals, date, favourite);
            }
        }

        databaseToRouteList();
    }

/*
    private class AccesData extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... argumentos) {

            StringBuffer bufferCadena = new StringBuffer("");

            try {
                HttpClient cliente = new DefaultHttpClient();
                HttpGet peticion = new HttpGet(url);
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
                JSONObject obj = new JSONObject(data);
                JSONArray routesArray = obj.getJSONArray("routes");

                for (int i = 0; i < routesArray.length(); i++) {
                    JSONObject insideArray = routesArray.getJSONObject(i);
                    id= insideArray.getLong("id");
                    routeName= insideArray.getString("name");
                    routeDescription= insideArray.getString("description");
                    routeCreator= insideArray.getString("creator");
                    assessment= insideArray.getDouble("assessment");
                    listRoutes.add(new routes(id, routeName, routeDescription, routeCreator, assessment));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }*/


    public void onRestart()
    {
        super.onRestart();
        linearLayoutMenu.setVisibility(View.GONE);

        filterConfig();
        clearData();
        databaseToRouteList();

        spCity.setSelection(cityOfRouteFilterPosition);
        adapterRoutes.notifyDataSetChanged();
    }
}
