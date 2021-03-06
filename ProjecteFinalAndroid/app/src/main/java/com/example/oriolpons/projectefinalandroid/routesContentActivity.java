package com.example.oriolpons.projectefinalandroid;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.oriolpons.projectefinalandroid.Database.Datasource;
import com.example.oriolpons.projectefinalandroid.Models.local;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterLocal;

import java.util.ArrayList;

public class routesContentActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton btnAddFav, btnRemoveFav;
    private TextView txtTitle, txtDescription, txtAssessment, txtCreator;
    private ImageButton btnBack;
    private ArrayList<local> listLocals;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterLocal AdapterLocal;
    private RecyclerView recyclerLocals;

    private String localName, localDescription, localAssessment, city, rute_locals, route_date, favourite;
    private int id, route_lenght;
    private Datasource bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_content);
        bd = new Datasource(this);

        listLocals = new ArrayList<>();
        recyclerLocals = (RecyclerView) findViewById(R.id.RecyclerLocal);
        recyclerLocals.setLayoutManager(new LinearLayoutManager(this));

        txtTitle = (TextView) findViewById(R.id.tvTitle);
        txtDescription = (TextView) findViewById(R.id.tvDescription);
        txtAssessment = (TextView) findViewById(R.id.tvAssessment);
        txtCreator = (TextView) findViewById(R.id.tvCreator);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnAddFav = (FloatingActionButton) findViewById(R.id.btnAddFav);
        btnAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrRemoveFav();
            }
        });

        btnRemoveFav = (FloatingActionButton) findViewById(R.id.btnRemoveFav);
        btnRemoveFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrRemoveFav();
            }
        });

        id = this.getIntent().getExtras().getInt("id");
        Cursor cursor = bd.getRouteInformation(id);
        while(cursor.moveToNext()) {
            route_lenght = cursor.getInt(0);
            txtTitle.setText(cursor.getString(1));
            txtDescription.setText(cursor.getString(2));
            txtAssessment.setText(cursor.getString(3));
            txtCreator.setText(cursor.getString(4));
            city = cursor.getString(5);
            rute_locals = cursor.getString(6);
            route_date = cursor.getString(7);
            favourite = cursor.getString(8);
        }

        /*
        String routeName = this.getIntent().getExtras().getString("name");
        String routeDescription = this.getIntent().getExtras().getString("description");
        String routeAssessment = this.getIntent().getExtras().getString("assessment");
        String routeCreator = this.getIntent().getExtras().getString("creator");

        txtTitle.setText(routeName);
        txtDescription.setText(routeDescription);
        txtAssessment.setText(routeAssessment);
        txtCreator.setText(routeCreator);
        */
        AdapterLocal = new adapterLocal(listLocals);
        AdapterLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localName = listLocals.get(recyclerLocals.getChildAdapterPosition(view)).getName();
                localDescription = listLocals.get(recyclerLocals.getChildAdapterPosition(view)).getDescription();
                localAssessment = listLocals.get(recyclerLocals.getChildAdapterPosition(view)).getAssessment() + "/5 - 1 votos";
                intentLocalContent();
            }
        });
        recyclerLocals.setAdapter(AdapterLocal);



        if (favourite.equals("FALSE")|| favourite == null){
            btnAddFav.setVisibility(View.VISIBLE);
            btnRemoveFav.setVisibility(View.GONE);
        }else{
            btnAddFav.setVisibility(View.GONE);
            btnRemoveFav.setVisibility(View.VISIBLE);

        }
        exampleLocal();
        getSupportActionBar().setTitle("Contenido de la ruta");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnBack: finish(); break;
        }
    }

    private void addOrRemoveFav() {
        if (btnRemoveFav.getVisibility() == View.GONE){
            btnAddFav.setVisibility(View.GONE);
            btnRemoveFav.setVisibility(View.VISIBLE);
            bd.routesAddOrRemoveFavourite(id, "TRUE");
        }
        else{
            btnAddFav.setVisibility(View.VISIBLE);
            btnRemoveFav.setVisibility(View.GONE);
            bd.routesAddOrRemoveFavourite(id, "FALSE");
        }
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

    private void exampleLocal() {

        for(int index = 0; index<= 3; index++){

            listLocals.add(new local(index,"restaurant","local " + index+ ".", "Un local muy entretenido.",  index + 0.0, null, null, null, null,0, null,0));
        }
    }
}
