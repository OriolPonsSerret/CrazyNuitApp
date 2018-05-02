package com.example.oriolpons.projectefinalandroid;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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

    private String localName, localDescription, localAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_content);

        listLocals = new ArrayList<>();
        recyclerLocals = (RecyclerView) findViewById(R.id.RecyclerLocal);
        recyclerLocals.setLayoutManager(new LinearLayoutManager(this));

        txtTitle = (TextView) findViewById(R.id.tvTitle);
        txtDescription = (TextView) findViewById(R.id.tvDescription);
        txtAssessment = (TextView) findViewById(R.id.tvAssessment);
        txtCreator = (TextView) findViewById(R.id.tvCreator);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        String routeName = this.getIntent().getExtras().getString("name");
        String routeDescription = this.getIntent().getExtras().getString("description");
        String routeAssessment = this.getIntent().getExtras().getString("assessment");
        String routeCreator = this.getIntent().getExtras().getString("creator");

        txtTitle.setText(routeName);
        txtDescription.setText(routeDescription);
        txtAssessment.setText(routeAssessment);
        txtCreator.setText(routeCreator);

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

        exampleLocal();
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
        }
        else{
            btnRemoveFav.setVisibility(View.GONE);
            btnAddFav.setVisibility(View.VISIBLE);
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

            listLocals.add(new local(index,"local " + index+ ".", "Un local muy entretenido.",  index + 0.0));
        }
    }
}
