package com.example.oriolpons.projectefinalandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class localContentActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtTitle, txtDescription, txtAssessment;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_content);

        txtTitle = (TextView) findViewById(R.id.tvTitle);
        txtDescription = (TextView) findViewById(R.id.tvDescription);
        txtAssessment = (TextView) findViewById(R.id.tvAssessment);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        String localName = this.getIntent().getExtras().getString("name");
        String localDescription = this.getIntent().getExtras().getString("description");
        String localAssessment = this.getIntent().getExtras().getString("assessment");

        txtTitle.setText(localName);
        txtDescription.setText(localDescription);
        txtAssessment.setText(localAssessment);

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
