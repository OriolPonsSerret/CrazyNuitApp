package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oriolpons.projectefinalandroid.Models.local;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterLocal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class localActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btnHome, btnLocal, btnRoutes, btnUserProfile, btnBack;
    private Button btnMenu, btnFilterLocal;
    private ArrayList<local> listLocals;
    private com.example.oriolpons.projectefinalandroid.Adapters.adapterLocal AdapterLocal;
    private RecyclerView recyclerLocals;
    private LinearLayout linearLayoutMenu;
    private  Spinner spCity;
    private int duration;
    private JSONObject data = null;
    private StringBuffer json;
    private AlertDialog dialog;

    private String localName, localDescription, localAssessment, typeOfLocalFilter, URL;

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

        final EditText edtFilterName = (EditText) view.findViewById(R.id.edtFilterLocalName);
        final RadioButton rbtnAsc = (RadioButton) view.findViewById(R.id.rbtnAsc);
        final RadioButton rbtnDes = (RadioButton) view.findViewById(R.id.rbtnDes);
        final RadioButton ckbxRestaurant = (RadioButton) view.findViewById(R.id.ckbxRestaurant);
        final RadioButton ckbxPub = (RadioButton) view.findViewById(R.id.ckbxPub);
        final RadioButton ckbxDisco = (RadioButton) view.findViewById(R.id.ckbxDisco);
        Button btnFilterLocal = (Button) view.findViewById(R.id.btnFilterLocal);

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

                if (ckbxRestaurant.isChecked()){
                    typeOfLocalFilter = "bar";
                }
                else{
                    if (ckbxPub.isChecked()){
                        typeOfLocalFilter = "pub";
                    }else{

                    }
                    if (ckbxDisco.isChecked()){
                        typeOfLocalFilter = "disco";
                    }
                }


                text = "Se han aplicado los filtros.";
                duration = 3;

                mensaje = Toast.makeText(context, text, duration);
                mensaje.show();
                dialog.cancel();
            }
        });


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





    private void getJSON() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL(URL);//------------------------------------------------------------<<<<<<<<<<<<<< URL <<<<<<

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

                    //System.out.println("Exception " + e.getMessage());
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
                        readData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.execute();
    }

    private void readData() throws JSONException {
        long id = 0;
        int categoria = 0;
        double valoracio = 0.0;
        String nom = "", descripcio = "", gastronomia = "";

        if (typeOfLocalFilter.equals("bar")){

            for (int i = 0; i < data.length(); i++) {
                id = (long) data.get("idBar-Restaurant");
                nom = (String) data.get("Nom");
                descripcio = (String) data.get("Descripcio");
                valoracio = (int) data.get("Valoracio");
                categoria = (int) data.get("Categoria");
                listLocals.add(new local(id,nom, descripcio, valoracio));
            }
        }

        if (typeOfLocalFilter.equals("pub")){
            for (int i = 0; i < data.length(); i++) {
                id = (long) data.get("idPub");
                nom = (String) data.get("Nom");
                descripcio = (String) data.get("Descripcio");
                valoracio = (int) data.get("Valoracio");
                gastronomia = (String) data.get("TipusGastronomic");
                categoria = (int) data.get("Categoria");
                listLocals.add(new local(id,nom, descripcio, valoracio));
            }
        }

        if (typeOfLocalFilter.equals("disco")){
            for (int i = 0; i < data.length(); i++) {
                id = (long) data.get("idDiscoteca");
                nom = (String) data.get("Nom");
                descripcio = (String) data.get("Descripcio");
                valoracio = (int) data.get("Valoracio");
                categoria = (int) data.get("Categoria");
                listLocals.add(new local(id,nom, descripcio, valoracio));
            }
        }
        // JSONObject jsonObjectMain = (JSONObject) data.get("main");
    }
}
