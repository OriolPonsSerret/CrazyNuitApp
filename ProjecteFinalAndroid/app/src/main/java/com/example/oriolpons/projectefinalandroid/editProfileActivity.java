package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.oriolpons.projectefinalandroid.Database.Datasource;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class editProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btnBack;
    private Button btnDeleteAccount, btnUserName, btnDescription, btnPassword, btnDate;
    private AlertDialog dialog;
    private String userEmail = "", userName = "";
    public Datasource bd;
    private int userId = 0;
    private String url = "http://10.0.2.2/ApiCrazyNuit/public/api/usuaris/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        bd = new Datasource(this);

        btnUserName = (Button) findViewById(R.id.btnUserName);
        btnUserName.setOnClickListener(this);
        btnDescription = (Button) findViewById(R.id.btnDescription);
        btnDescription.setOnClickListener(this);
        btnPassword = (Button) findViewById(R.id.btnPassword);
        btnPassword.setOnClickListener(this);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(this);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnDeleteAccount = (Button) findViewById(R.id.btnDeleteAccount);
        btnDeleteAccount.setOnClickListener(this);

        userEmail = this.getIntent().getExtras().getString("user_email");

        Cursor cursor = bd.getUserInformationByEmail(userEmail);
        while(cursor.moveToNext()){
            userId = cursor.getInt(0);
        }
        getSupportActionBar().setTitle("Editar perfil de usuario");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnUserName: actionEditUserName(); break;
            case R.id.btnDescription: actionEditDescription(); break;
            case R.id.btnPassword: actionEditPassword(); break;
            case R.id.btnDate: actionEditDate(); break;
            case R.id.btnDeleteAccount: actionDeleteAccount(); break;
            case R.id.btnBack: actionBack(); break;
        }
    }

    private void actionEditUserName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_edit_username,null);
        final EditText edtUserName = (EditText) view.findViewById(R.id.edtUserName);
        Button btnAccept = (Button) view.findViewById(R.id.btnAccept);


        Cursor cursor = bd.getUserInformationByEmail(userEmail);
        while(cursor.moveToNext()){
            userName = cursor.getString(1);
        }

        edtUserName.setText(userName);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration;
                Toast mensaje;

                if(!edtUserName.getText().toString().isEmpty()){

                    if(!bd.usernameAskExist(edtUserName.getText().toString(), userId)){
                        //bd.updateRoutesCreatorWhereNameChanged(userName, edtUserName.getText().toString());//No funciona
                        bd.userUpdateName(userEmail, edtUserName.getText().toString());


                        text = "Se han guardado los cambios.";
                        duration = 3;

                        mensaje = Toast.makeText(context, text, duration);
                        mensaje.show();

                        dialog.cancel();
                    }
                    else{
                        edtUserName.setError("Ya existe un usuario con ese nombre.");
                        return;
                    }


                }
                else{
                    edtUserName.setError("El campo no puede estar vacio.");
                    return;
                }


            }
        });

    }

    private void actionEditDescription() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_edit_description,null);
        final EditText edtDescription = (EditText) view.findViewById(R.id.edtDescription);
        Button btnAcceptDescription = (Button) view.findViewById(R.id.btnAcceptDescription);

        String userDescription = "";

        Cursor cursor = bd.getUserInformationByEmail(userEmail);
        while(cursor.moveToNext()){
            userDescription = cursor.getString(2);
        }

        edtDescription.setText(userDescription);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        btnAcceptDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration;
                Toast mensaje;

                bd.userUpdateDescription(userEmail, edtDescription.getText().toString());


                text = "Se han guardado los cambios.";
                duration = 3;

                mensaje = Toast.makeText(context, text, duration);
                mensaje.show();

                dialog.cancel();
            }
        });

    }

    private void actionEditPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_edit_password,null);

        final EditText edtPasswordOld = (EditText) view.findViewById(R.id.edtPasswordOld);
        final EditText edtPasswordNew = (EditText) view.findViewById(R.id.edtPasswordNew);
        final EditText edtPasswordNewR = (EditText) view.findViewById(R.id.edtPasswordNewR);
        Button btnAcceptPassword = (Button) view.findViewById(R.id.btnAcceptPassword);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        btnAcceptPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration;
                Toast mensaje;

                if(!edtPasswordOld.getText().toString().isEmpty()&&!edtPasswordNew.getText().toString().isEmpty()&&!edtPasswordNewR.getText().toString().isEmpty()){

                    text = "Se han guardado los cambios.";
                    duration = 3;

                    mensaje = Toast.makeText(context, text, duration);
                    mensaje.show();
                }
                else{
                    text = "Todos los campos son obligatorios.";
                    duration = 3;

                    mensaje = Toast.makeText(context, text, duration);
                    mensaje.show();
                }

                dialog.cancel();
            }
        });

    }

    private void actionEditDate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_edit_date,null);

        final DatePicker Calendar = (DatePicker) view.findViewById(R.id.dateCalendar);
        Button btnAcceptPassword = (Button) view.findViewById(R.id.btnAcceptDate);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        btnAcceptPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv;
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration;
                Toast mensaje;

                int day = Calendar.getDayOfMonth();
                int month = Calendar.getMonth() + 1;
                int year = Calendar.getYear();

                text = "Se han guardado los cambios.";
                duration = 3;

                mensaje = Toast.makeText(context, text, duration);
                mensaje.show();

                dialog.cancel();
            }
        });

    }

    private void actionDeleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_edit_deleteaccount,null);

        final EditText edtPasswordDelete = (EditText) view.findViewById(R.id.edtPasswordDelete);
        Button btnAcceptPassword = (Button) view.findViewById(R.id.btnAcceptDelete);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        btnAcceptPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv;
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration;
                Toast mensaje;

                if(!edtPasswordDelete.getText().toString().isEmpty()){


                    text = "Se han guardado los cambios.";
                    duration = 3;

                    mensaje = Toast.makeText(context, text, duration);
                    mensaje.show();
                }
                else{
                    text = "Debes confirmar tu contraseña para eliminar la cuenta.";
                    duration = 3;

                    mensaje = Toast.makeText(context, text, duration);
                    mensaje.show();
                }

                dialog.cancel();
            }
        });
    }

    private void actionBack() {
        finish();
    }

/*
    private class putJsonData extends AsyncTask<String, String, Void> {

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", response);
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");

                return params;
            }

        };

            queue.add(putRequest);
    }*/
}
