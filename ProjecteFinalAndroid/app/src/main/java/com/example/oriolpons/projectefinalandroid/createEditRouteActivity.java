package com.example.oriolpons.projectefinalandroid;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class createEditRouteActivity extends Activity implements View.OnClickListener{

    ImageButton btnBack, btnDelete;
    Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_edit_route);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(this);

        btnDelete = (ImageButton) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnAccept: actionAcceptChanges(); break;
            case R.id.btnBack: actionBack(); break;
            case R.id.btnDelete: actionDelete(); break;
        }
    }
    private void actionAcceptChanges() {
        finish();
    }
    private void actionBack() {
        finish();
    }

    private void actionDelete() {
        finish();
    }
}
