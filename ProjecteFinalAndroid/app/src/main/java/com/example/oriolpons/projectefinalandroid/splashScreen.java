package com.example.oriolpons.projectefinalandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.oriolpons.projectefinalandroid.Database.Datasource;

public class splashScreen extends Activity {
    private ProgressBar progressBar;
    int progressStatus = 0;
    private Datasource bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        bd = new Datasource(this);

        progressBar=(ProgressBar)findViewById(R.id.progressBar1);

        if (bd.filterConfigVerification()) {
        }
        else{
            bd.DefaultFilterConfigAdd();

        }

        loading();
    }

    private void loading() {

        Thread logoTimer = new Thread(){
            public void run(){
                try{
                    int logoTimer = 0;
                    while (logoTimer<5000){
                        sleep(100);
                        if (progressStatus < 50) {
                            progressStatus += 1;
                            progressBar.setProgress(progressStatus);
                        }
                        logoTimer=logoTimer+100;
                    }
                    //finish();
                    loginIntent();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    finish();
                }
            }


        };
        logoTimer.start();
    }
    private void loginIntent() {
        Intent i = new Intent(this, loginActivity.class );
        startActivity(i);
    }
}
