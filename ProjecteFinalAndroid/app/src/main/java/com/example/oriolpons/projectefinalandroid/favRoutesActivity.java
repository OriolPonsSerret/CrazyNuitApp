package com.example.oriolpons.projectefinalandroid;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.oriolpons.projectefinalandroid.adapter.adapterRoutes;

import java.util.ArrayList;

public class favRoutesActivity extends AppCompatActivity implements View.OnClickListener{

    FloatingActionButton fltBtnGlobal;
    ImageButton btnHome, btnLocal, btnRoutes, btnUserProfile, btnBack;
    Button btnMenu, btnMyRoutes, btnShort, btnHalfways, btnLong;
    ArrayList<routes> listRoutes;
    RecyclerView recyclerRoutes;
    private com.example.oriolpons.projectefinalandroid.adapter.adapterRoutes adapterRoutes;
    LinearLayout linearLayoutMenu;
    Spinner spCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_routes);


        listRoutes = new ArrayList<>();

        recyclerRoutes = (RecyclerView) findViewById(R.id.RecyclerRoutes);
        recyclerRoutes.setLayoutManager(new LinearLayoutManager(this));

        linearLayoutMenu = (LinearLayout) findViewById(R.id.linearLayoutMenu);

        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnMyRoutes = (Button) findViewById(R.id.btnMyRoutes);
        btnMyRoutes.setOnClickListener(this);

        btnShort = (Button) findViewById(R.id.btnShort);
        btnShort.setOnClickListener(this);
        btnHalfways = (Button) findViewById(R.id.btnHalfways);
        btnHalfways.setOnClickListener(this);
        btnLong = (Button) findViewById(R.id.btnLong);
        btnLong.setOnClickListener(this);

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arraySpinnerCity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity.setAdapter(adapter);

        exampleRoutes();
        btnRoutes.setEnabled(false);
    }

    private void loadGlobalRoutes() {
        Intent i = new Intent(this, routesActivity.class );
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
            case R.id.btnShort: actionPressedShort(); break;
            case R.id.btnHalfways: actionPressedHalfways(); break;
            case R.id.btnLong: actionPressedLong(); break;
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

    private void actionPressedShort() {
        btnShort.setEnabled(false);
        btnHalfways.setEnabled(true);
        btnLong.setEnabled(true);
    }
    private void actionPressedHalfways() {
        btnShort.setEnabled(true);
        btnHalfways.setEnabled(false);
        btnLong.setEnabled(true);
    }
    private void actionPressedLong() {
        btnShort.setEnabled(true);
        btnHalfways.setEnabled(true);
        btnLong.setEnabled(false);
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
        Intent i = new Intent(this, profileActivity.class );
        startActivity(i);
    }




    private void exampleRoutes() {

        for(int index = 0; index<= 2; index++){

            listRoutes.add(new routes(index,"Ruta " + index+ ".", "Una ruta muy entretenida.", "Persona " + index+ ".", index * 1.2));
        }
    }

    public void onRestart()
    {
        super.onRestart();
        linearLayoutMenu.setVisibility(View.GONE);

    }
}

