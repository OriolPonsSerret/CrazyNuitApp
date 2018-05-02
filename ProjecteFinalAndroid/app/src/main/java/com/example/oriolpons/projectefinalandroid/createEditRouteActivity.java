package com.example.oriolpons.projectefinalandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.oriolpons.projectefinalandroid.Models.local;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalAdd;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalList;

import java.util.ArrayList;

public class createEditRouteActivity extends Activity implements View.OnClickListener{

    private ImageButton btnBack, btnDelete;
    private Button btnAccept;
    private ArrayList<local> listLocal, listLocalAdd;
    private RecyclerView RecyclerList, RecyclerAdd;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalList adapterLocalList;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterLocalAdd adapterLocalAdd;
    private String localName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_route);

        listLocal = new ArrayList<>();
        listLocalAdd = new ArrayList<>();

        RecyclerList = (RecyclerView) findViewById(R.id.RecyclerList);
        RecyclerList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdd = (RecyclerView) findViewById(R.id.RecyclerAdd);
        RecyclerAdd.setLayoutManager(new LinearLayoutManager(this));

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(this);

        btnDelete = (ImageButton) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

     adapterLocalAdd = new adapterLocalAdd(listLocal);
        adapterLocalAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localName = listLocal.get(RecyclerList.getChildAdapterPosition(view)).getName();
                listLocal.remove(view);
                addLocal();
                clearDataList();
                clearDataAdd();
            }
        });
        RecyclerList.setAdapter(adapterLocalAdd);

        adapterLocalList = new adapterLocalList(listLocalAdd);
        adapterLocalList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localName = listLocalAdd.get(RecyclerAdd.getChildAdapterPosition(view)).getName();
                listLocalAdd.remove(view);
                removeLocal();

                clearDataAdd();
            }
        });
        RecyclerAdd.setAdapter(adapterLocalList);

        exampleRoutes();
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
        finish();
    }
    private void actionBack() {
        finish();
    }

    private void actionDelete() {
        finish();
    }


    public void clearDataList() {
       // listLocal.clear(); //clear list
        adapterLocalList.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }

    public void clearDataAdd() {
      //  listLocalAdd.clear(); //clear list
        adapterLocalAdd.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }

    private void exampleRoutes() {
        for(int index = 0; index<= 3; index++){
            listLocal.add(new local(index,"Local " + index+ ".", "En este local puedes jugar con gatos.", 4.0));
        }
    }

    private void addLocal() {
        listLocalAdd.add(new local(0,localName, "En este local puedes jugar con gatos.", 4.0));
    }
    private void removeLocal() {
        listLocal.add(new local(0,localName, "En este local puedes jugar con gatos.", 4.0));
    }
}
