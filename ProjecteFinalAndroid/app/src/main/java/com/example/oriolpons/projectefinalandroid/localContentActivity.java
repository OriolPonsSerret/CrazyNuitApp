package com.example.oriolpons.projectefinalandroid;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.oriolpons.projectefinalandroid.Models.local;

public class localContentActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtTitle, txtDescription, txtAssessment, txtAddress;
    private ImageButton btnBack;
    private int localId = 0;
    private RatingBar rbAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_content);

        txtTitle = (TextView) findViewById(R.id.tvTitle);
        txtDescription = (TextView) findViewById(R.id.tvDescription);
        txtAssessment = (TextView) findViewById(R.id.tvAssessment);
        txtAddress = (TextView) findViewById(R.id.txtAddress);

        rbAssessment = (RatingBar) findViewById(R.id.rbAssessment);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        localId = this.getIntent().getExtras().getInt("id");
        String localName = this.getIntent().getExtras().getString("name");
        String localDescription = this.getIntent().getExtras().getString("description");
        Double localAssessment = this.getIntent().getExtras().getDouble("assessment");
        String localAddress = this.getIntent().getExtras().getString("address");

        txtTitle.setText(localName);
        txtDescription.setText(localDescription);
        txtAssessment.setText(localAssessment.toString());
        txtAddress.setText(localAddress);

        if (localAssessment > 1){
            if (localAssessment > 2){
                if (localAssessment > 3){
                    if (localAssessment > 4){
                        if (localAssessment == 5){
                            rbAssessment.setRating(5);
                        }
                        else{
                            if (localAssessment > 4 && localAssessment < 5){
                                rbAssessment.setRating((float)4.5);
                            }
                        }
                    }else{
                        if (localAssessment == 4){
                            rbAssessment.setRating(4);
                        }
                        else{
                            if (localAssessment > 3 && localAssessment < 4){
                                rbAssessment.setRating((float)3.5);
                            }
                        }
                    }
                }else{
                    if (localAssessment == 3){
                        rbAssessment.setRating(3);
                    }
                    else{
                        if (localAssessment > 2 && localAssessment < 3){
                            rbAssessment.setRating((float)2.5);
                        }
                    }
                }
            }else{
                if (localAssessment == 2){
                    rbAssessment.setRating(2);
                }
                else{
                    if (localAssessment > 1 && localAssessment < 2){
                        rbAssessment.setRating((float)1.5);
                    }
                }
            }

        }
        else{
            if (localAssessment == 1){
                rbAssessment.setRating(1);
            }
            else{
                if (localAssessment > 0 && localAssessment < 1){
                    rbAssessment.setRating((float)0.5);
                }
                else{

                }
                rbAssessment.setRating(0);
            }
        }

        getSupportActionBar().setTitle("Información del local");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnBack: finish(); break;
        }
    }
}
