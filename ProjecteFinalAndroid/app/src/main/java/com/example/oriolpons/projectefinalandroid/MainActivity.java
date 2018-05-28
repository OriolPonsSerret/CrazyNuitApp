package com.example.oriolpons.projectefinalandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.oriolpons.projectefinalandroid.Database.Datasource;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btnHome, btnLocal, btnRoutes, btnUserProfile;
    private String userEmail = "", userName = "";
    private Datasource bd;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = new Datasource(this);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
        btnLocal = (ImageButton) findViewById(R.id.btnLocal);
        btnLocal.setOnClickListener(this);
        btnRoutes = (ImageButton) findViewById(R.id.btnRoutes);
        btnRoutes.setOnClickListener(this);
        btnUserProfile = (ImageButton) findViewById(R.id.btnUserProfile);
        btnUserProfile.setOnClickListener(this);

        btnHome.setEnabled(false);

        userEmail = this.getIntent().getExtras().getString("user_email");

        getSupportActionBar().setTitle("");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnHome: intentMain(); break;
            case R.id.btnLocal: intentLocal(); break;
            case R.id.btnRoutes: intentRoutes(); break;
            case R.id.btnUserProfile: intentUserProfile(); break;
        }
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
        cursor = bd.getUserInformationByEmail(userEmail);
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

    public void OnStart()
    {super.onStart();

    }
    public void onRestart()
    {
        super.onRestart();


    }

    public void onResume()
    {
        super.onResume();


    }

    public void onPause()
    {
        super.onPause();


    }

    public void onStop()
    {
        super.onStop();

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        //finish();
    }

    public void OnDestroy()
    {
        super.onDestroy();


    }
}
