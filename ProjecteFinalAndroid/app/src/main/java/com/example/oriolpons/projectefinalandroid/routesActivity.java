package com.example.oriolpons.projectefinalandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class routesActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnHome, btnFavourite, btnRoutes, btnUserProfile, btnBack;
    Button btnMenu, btnBar, btnPub, btnDisco;
    ArrayList<routes> listRoutes;
    RecyclerView recyclerRoutes;
    private adapterRoutes adapterRoutes;
    LinearLayout linearLayoutMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        listRoutes = new ArrayList<>();

        recyclerRoutes = (RecyclerView) findViewById(R.id.RecyclerRoutes);
        recyclerRoutes.setLayoutManager(new LinearLayoutManager(this));

        linearLayoutMenu = (LinearLayout) findViewById(R.id.linearLayoutMenu);

        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);


        btnBar = (Button) findViewById(R.id.btnBar);
        btnBar.setOnClickListener(this);
        btnPub = (Button) findViewById(R.id.btnPub);
        btnPub.setOnClickListener(this);
        btnDisco = (Button) findViewById(R.id.btnDisco);
        btnDisco.setOnClickListener(this);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
        btnFavourite = (ImageButton) findViewById(R.id.btnFavourite);
        btnFavourite.setOnClickListener(this);
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

        btnRoutes.setEnabled(false);
        exampleRoutes();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnHome: intentMain(); break;
            case R.id.btnFavourite: intentFavourite(); break;
            case R.id.btnRoutes: intentRoutes(); break;
            case R.id.btnUserProfile: intentUserProfile(); break;
            case R.id.btnMenu: actionShowHideMenu(); break;
            case R.id.btnBack: actionBack(); break;
            case R.id.btnBar: actionPressedBar(); break;
            case R.id.btnPub: actionPressedPub(); break;
            case R.id.btnDisco: actionPressedDisco(); break;
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

    private void actionPressedBar() {

    }

    private void actionPressedPub() {


    }
    private void actionPressedDisco() {

    }
    private void intentMain() {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
    private void intentFavourite() {
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
        String colorequipo = "Sin equipo";

        for(int index = 0; index<= 15; index++){

            listRoutes.add(new routes(index,"Ruta " + index+ ".", "Una ruta muy entretenida.", "Calle " + index+ "."));
        }
    }

    public void onRestart()
    {
        super.onRestart();
        linearLayoutMenu.setVisibility(View.GONE);

    }
}
