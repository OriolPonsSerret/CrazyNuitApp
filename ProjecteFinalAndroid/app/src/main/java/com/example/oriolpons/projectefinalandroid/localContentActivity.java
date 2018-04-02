package com.example.oriolpons.projectefinalandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class localContentActivity extends AppCompatActivity {

    TextView txtTitle, txtDescription, txtAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_content);

        txtTitle = (TextView) findViewById(R.id.tvTitle);
        txtDescription = (TextView) findViewById(R.id.tvDescription);
        txtAssessment = (TextView) findViewById(R.id.tvAssessment);

        String localName = this.getIntent().getExtras().getString("name");
        String localDescription = this.getIntent().getExtras().getString("description");
        String localAssessment = this.getIntent().getExtras().getString("assessment");

        txtTitle.setText(localName);
        txtDescription.setText(localDescription);
        txtAssessment.setText(localAssessment);
    }
}
