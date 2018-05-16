package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.oriolpons.projectefinalandroid.Models.routes;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterRoutes;

import java.util.ArrayList;

public class favRoutesActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton fltBtnGlobal;
    private ImageButton btnHome, btnLocal, btnRoutes, btnUserProfile, btnBack;
    private Button btnMenu, btnMyRoutes, btnFilter;
    private ArrayList<routes> listRoutes;
    private RecyclerView recyclerRoutes;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterRoutes adapterRoutes;
    private String routeName, routeDescription, routeCreator, routeAssessment, NameFilter = "";
    private LinearLayout linearLayoutMenu;
    private Spinner spCity;
    private long id;
    private double assessment;
    private AlertDialog dialog;

    private Datasource bd;
    private String assessmentFilter = "ASC", typeOfRouteFilter = "short", cityOfRouteFilter= "Mataró", URL =  "http://localhost/ApiCrazyNuit/public/api/";
    private int cityOfRouteFilterPosition = 0, RouteLenghtMin = 0, RouteLenght = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_routes);

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
                routeName = listRoutes.get(recyclerRoutes.getChildAdapterPosition(view)).getName();
                routeDescription = listRoutes.get(recyclerRoutes.getChildAdapterPosition(view)).getDescription();
                routeAssessment = listRoutes.get(recyclerRoutes.getChildAdapterPosition(view)).getAssessment() + "/5 - 1 votos";
                routeCreator = listRoutes.get(recyclerRoutes.getChildAdapterPosition(view)).getCreator();
                intentRouteContent();
            }
        });
        recyclerRoutes.setAdapter(adapterRoutes);

        fltBtnGlobal = (FloatingActionButton) findViewById(R.id.fltBtnGlobal);
        fltBtnGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
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
                addDBRoutes();
                exampleRoutes();
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

        filterConfig();
        clearData();
        addDBRoutes();
        exampleRoutes();

        spCity.setSelection(cityOfRouteFilterPosition);
        btnRoutes.setEnabled(false);
    }


    private void addDBRoutes() {
        String name = "", description= "", creator= "Senpai", city= "", locals = "", date = "";
        Double assessment = 1.0;
        int route_lenght = 2;


        for(int id = 0; id <= 10; id++){

            assessment = assessment + 0.2;
            if (id >= 0 && id <= 10){
                name = "Ruta: " + id;
                description= "Una ruta entretenida.";
                if (id <= 2){
                    city= "Mataró";
                    assessment = 5.0;
                    route_lenght = 2;
                }
                else{
                    if (id == 3){
                        city= "Mataró";
                        assessment = 3.0;
                        route_lenght = 10;
                    }
                }
                if (id >= 4 && id <= 7){
                    city= "Barcelona";
                    assessment = 3.0;
                    route_lenght = 2;
                }
                if (id >= 8 && id <= 10){
                    city= "Girona";
                    assessment = 2.0;
                    route_lenght = 4;
                }
                if (bd.routesAskExist(id)){
                    bd.routesUpdate(id, route_lenght, name, description, assessment, creator, city, locals, date);
                }
                else{
                    bd.routesAdd(id, route_lenght, name, description, assessment, creator, city, locals, date);
                }
            }
        }
    }


    private void intentRouteContent() {
        Bundle bundle = new Bundle();
        bundle.putString("name",routeName);
        bundle.putString("description",routeDescription);
        bundle.putString("assessment",routeAssessment);
        bundle.putString("creator",routeCreator);
        Intent intent = new Intent(this, routesContentActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
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
            RouteLenght = 4;
        }
        else{
            if (typeOfRouteFilter.equals("halfways")) {
                ckbxHalfways.setChecked(true);
                RouteLenghtMin = 4;
                RouteLenght = 8;
            }
            else{
                if (typeOfRouteFilter.equals("long")) {
                    ckbxLong.setChecked(true);
                    RouteLenghtMin = 8;
                    RouteLenght = 13;
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
                    RouteLenght = 4;
                }
                else{
                    if (ckbxHalfways.isChecked()){
                        typeOfRouteFilter = "halfways";
                        RouteLenghtMin = 4;
                        RouteLenght = 8;
                    }else{

                    }
                    if (ckbxLong.isChecked()){
                        typeOfRouteFilter = "long";
                        RouteLenghtMin = 8;
                        RouteLenght = 13;
                    }
                }

                NameFilter = edtFilterName.getText().toString();

                bd.filterConfigUpdate("routes", assessmentFilter, typeOfRouteFilter, cityOfRouteFilter, cityOfRouteFilterPosition);
                clearData();
                addDBRoutes();
                exampleRoutes();

                text = "Se han aplicado los filtros.";
                duration = 3;

                mensaje = Toast.makeText(context, text, duration);
                mensaje.show();

                dialog.cancel();
            }
        });

    }

    private void actionMyRoutes() {
        Intent i = new Intent(this, myRoutesActivity.class );
        startActivity(i);
    }
    private void intentMain() {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
    private void intentLocal() {
         Intent i = new Intent(this, localActivity.class );
         startActivity(i);
    }
    private void intentRoutes() {
        Intent i = new Intent(this, routesActivity.class );
        startActivity(i);
    }
    private void intentUserProfile() {
        Bundle bundle = new Bundle();
        bundle.putString("type","me");
        bundle.putString("userName","user");
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
    private void exampleRoutes() {

        Cursor cursor;
        Long id;
        String measure = "", name, description, creator, city, rute_locals, route_date;
        Double assessment, entrance_price;
        int route_lenght;

        if (typeOfRouteFilter.equals("short")) {
            RouteLenghtMin = 0;
            RouteLenght = 4;
        }
        else{
            if (typeOfRouteFilter.equals("halfways")) {
                RouteLenghtMin = 4;
                RouteLenght = 8;
            }
            else{
                if (typeOfRouteFilter.equals("long")) {
                    RouteLenghtMin = 8;
                    RouteLenght = 13;
                }
            }
        }

        cursor = bd.filterRoutes(cityOfRouteFilter, assessmentFilter, NameFilter, RouteLenghtMin, RouteLenght);
        while(cursor.moveToNext()){
            id = cursor.getLong(0);
            route_lenght = cursor.getInt(1);
            name = cursor.getString(2);
            description = cursor.getString(3);
            assessment = cursor.getDouble(4);
            creator = cursor.getString(5);
            city = cursor.getString(6);
            rute_locals = cursor.getString(7);
            route_date = cursor.getString(8);

            if (route_lenght <= 4){
                measure = "short";
            }else{
                if (route_lenght <= 8){
                    measure = "halfways";
                }else{
                    if (route_lenght <= 13){
                        measure = "long";
                    }
                }
            }

            listRoutes.add(new routes(id, measure, name, description, creator, assessment, 0));
        }
    }

    public void onRestart()
    {
        super.onRestart();
        linearLayoutMenu.setVisibility(View.GONE);

    }
}

