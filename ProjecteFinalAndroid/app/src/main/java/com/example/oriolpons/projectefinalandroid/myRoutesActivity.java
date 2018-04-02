package com.example.oriolpons.projectefinalandroid;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.oriolpons.projectefinalandroid.adapter.adapterRoutes;

import java.util.ArrayList;

public class myRoutesActivity extends AppCompatActivity implements View.OnClickListener{

    FloatingActionButton fltBtnAdd;
    ImageButton btnHome, btnTopUsers, btnRoutes, btnUserProfile, btnBack;
    Button btnMenu, btnPublicRoutes, btnShort, btnHalfways, btnLong;
    ArrayList<routes> listRoutes;
    RecyclerView recyclerRoutes;
    private com.example.oriolpons.projectefinalandroid.adapter.adapterRoutes adapterRoutes;
    LinearLayout linearLayoutMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_routes);

        listRoutes = new ArrayList<>();

        recyclerRoutes = (RecyclerView) findViewById(R.id.RecyclerRoutes);
        recyclerRoutes.setLayoutManager(new LinearLayoutManager(this));

        linearLayoutMenu = (LinearLayout) findViewById(R.id.linearLayoutMenu);

        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnPublicRoutes = (Button) findViewById(R.id.btnPublicRoutes);
        btnPublicRoutes.setOnClickListener(this);


        btnShort = (Button) findViewById(R.id.btnShort);
        btnShort.setOnClickListener(this);
        btnHalfways = (Button) findViewById(R.id.btnHalfways);
        btnHalfways.setOnClickListener(this);
        btnLong = (Button) findViewById(R.id.btnLong);
        btnLong.setOnClickListener(this);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
        btnTopUsers = (ImageButton) findViewById(R.id.btnTopUsers);
        btnTopUsers.setOnClickListener(this);
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

        fltBtnAdd = (FloatingActionButton) findViewById(R.id.fltBtnAdd);
        fltBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            loadCreateRoutes();
            }
        });

        exampleRoutes();
        btnRoutes.setEnabled(false);
    }

    private void loadCreateRoutes() {
        Intent i = new Intent(this, createEditRouteActivity.class );
        startActivity(i);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnHome: intentMain(); break;
            case R.id.btnTopUsers: intentTopUsers(); break;
            case R.id.btnRoutes: intentRoutes(); break;
            case R.id.btnUserProfile: intentUserProfile(); break;
            case R.id.btnMenu: actionShowHideMenu(); break;
            case R.id.btnBack: actionBack(); break;
            case R.id.btnPublicRoutes: finish(); break;
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

    private void intentMain() {
       Intent i = new Intent(this, MainActivity.class );
       startActivity(i);
    }
    private void intentTopUsers() {
        // Intent i = new Intent(this, favouriteActivity.class );
        // startActivity(i);
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

        for(int index = 0; index<= 15; index++){

            listRoutes.add(new routes(index,"Ruta " + index+ ".", "Una ruta muy entretenida.", "Persona " + index+ ".", index * 1.2));
        }
    }

    public void onRestart()
    {
        super.onRestart();
        linearLayoutMenu.setVisibility(View.GONE);

    }
}
