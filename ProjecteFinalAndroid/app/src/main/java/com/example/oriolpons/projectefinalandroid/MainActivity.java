package com.example.oriolpons.projectefinalandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnStart();
    }
    public void OnStart()
    {super.onStart();
    //    Intent i = new Intent(this, SplashScreen.class );
    //    startActivity(i);
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

    }

    public void OnDestroy()
    {
        super.onDestroy();

    }
}
