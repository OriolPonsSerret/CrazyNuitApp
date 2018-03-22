package com.example.oriolpons.projectefinalandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class splashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar=(ProgressBar)findViewById(R.id.progressBar1);

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
