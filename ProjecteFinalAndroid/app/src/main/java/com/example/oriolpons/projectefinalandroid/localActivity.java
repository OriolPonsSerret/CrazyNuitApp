package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oriolpons.projectefinalandroid.adapter.adapterLocal;

import java.util.ArrayList;

public class localActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnHome, btnLocal, btnRoutes, btnUserProfile, btnBack;
    Button btnMenu, btnFilterLocal;
    ArrayList<local> listLocals;
    private com.example.oriolpons.projectefinalandroid.adapter.adapterLocal AdapterLocal;
    RecyclerView recyclerLocals;
    LinearLayout linearLayoutMenu;
    Spinner spCity;

    private String localName, localDescription, localAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

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

        exampleLocal();

        btnLocal.setEnabled(false);
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

    private void actionFilterLocal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_filter_local,null);

        final EditText edtDescription = (EditText) view.findViewById(R.id.edtFilterLocalName);
        final RadioButton rbtnAsc = (RadioButton) view.findViewById(R.id.rbtnAsc);
        final RadioButton rbtnDes = (RadioButton) view.findViewById(R.id.rbtnDes);
        final CheckBox ckbxRestaurant = (CheckBox) view.findViewById(R.id.ckbxRestaurant);
        final CheckBox ckbxPub = (CheckBox) view.findViewById(R.id.ckbxPub);
        final CheckBox ckbxDisco = (CheckBox) view.findViewById(R.id.ckbxDisco);
        Button btnFilterLocal = (Button) view.findViewById(R.id.btnFilterLocal);

        btnFilterLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration;
                Toast mensaje;





                text = "Se han aplicado los filtros.";
                duration = 3;

                mensaje = Toast.makeText(context, text, duration);
                mensaje.show();
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
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
