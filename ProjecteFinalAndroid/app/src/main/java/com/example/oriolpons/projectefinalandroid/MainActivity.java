package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnHome, btnTopUsers, btnRoutes, btnUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
        btnTopUsers = (ImageButton) findViewById(R.id.btnTopUsers);
        btnTopUsers.setOnClickListener(this);
        btnRoutes = (ImageButton) findViewById(R.id.btnRoutes);
        btnRoutes.setOnClickListener(this);
        btnUserProfile = (ImageButton) findViewById(R.id.btnUserProfile);
        btnUserProfile.setOnClickListener(this);

        btnHome.setEnabled(false);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnHome: intentMain(); break;
            case R.id.btnTopUsers: intentTopUsers(); break;
            case R.id.btnRoutes: intentRoutes(); break;
            case R.id.btnUserProfile: intentUserProfile(); break;
        }
    }

    private void intentMain() {
       Intent i = new Intent(this, MainActivity.class );
       startActivity(i);

       // finish();
    }
    private void intentTopUsers() {
       // Intent i = new Intent(this, topUsersActivity.class );
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
