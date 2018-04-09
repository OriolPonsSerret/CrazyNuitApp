package com.example.oriolpons.projectefinalandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class editProfileActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnBack;
    Button btnAcceptChanges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnAcceptChanges = (Button) findViewById(R.id.btnAcceptChanges);
        btnAcceptChanges.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnAcceptChanges: actionAcceptChanges(); break;
            case R.id.btnBack: actionBack(); break;
        }
    }
    private void actionAcceptChanges() {
        finish();
    }
    private void actionBack() {
        finish();
    }

}
