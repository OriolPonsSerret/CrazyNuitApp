package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oriolpons.projectefinalandroid.Database.Datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class registerActivity extends AppCompatActivity{

    private Button btnRegister;
    private EditText edtUserName, edtEmail, edtPassword, edtPasswordR;
    private static String username = "",email = "", password = "", rePassword = "", url = "http://10.0.2.2/ApiCrazyNuit/public/api/register";
    private Datasource bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bd = new Datasource(this);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkData();
            }
        });

        edtUserName  = (EditText) findViewById(R.id.edtUserName);
        edtEmail  = (EditText) findViewById(R.id.edtEmail);
        edtPassword  = (EditText) findViewById(R.id.edtPassword);
        edtPasswordR  = (EditText) findViewById(R.id.edtPasswordR);

        getSupportActionBar().setTitle("Registro de usuario");
    }

    private void checkData() {

        TextView tv;
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration;
        Toast mensaje;

        tv = (TextView) findViewById(R.id.edtUserName);
        username = tv.getText().toString();
        if (username.trim().equals("")) {
            edtUserName.setError("El Nombre de Usuario és obligatorio.");
            return;
        }

        tv = (TextView) findViewById(R.id.edtEmail);
        email = tv.getText().toString();
        if (email.trim().equals("")) {
            edtEmail.setError("El Correo és obligatoria.");
            return;
        }

        tv = (TextView) findViewById(R.id.edtPassword);
        password = tv.getText().toString();
        if (password.trim().equals("")) {
            edtPassword.setError("La Contraseña és obligatoria.");
            return;
        }

        tv = (TextView) findViewById(R.id.edtPasswordR);
        rePassword = tv.getText().toString();

        if (password.length() < 6) {
            edtPasswordR.setError("Mínimo 6 carácteres");
            return;
        }

        if (rePassword.trim().equals("")) {
            edtPasswordR.setError("Repite la Contraseña.");
            return;
        }

        if (password.trim().equals(rePassword)) {
        }
        else{
            edtPasswordR.setError("Las Contraseñas no coinciden.");
            return;
        }

        if (!ValidationEmail()){
            edtEmail.setError("Correo no válido.");
            return;
        }
        else {

        //Falta: Comprovar que ni el nombre de usuario ni el correo existan en la base de datos.
        //       También enviarle los datos para que lo registre.


            if (bd.userAskExist(email)){
                edtEmail.setError("Ya existe un usuario con este correo.");
                return;
            }
            else{
                //postNewUser(context);
                RequestQueue RequestQueue = Volley.newRequestQueue(this);
                RequestQueue.add(MyPostRequest);

                text = "¡Te has registrado correctamente!";
                duration = 4;

                mensaje = Toast.makeText(context, text, duration);
                mensaje.show();

                finish();
            }

        }
    }

    private boolean ValidationEmail() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    StringRequest MyPostRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            //This code is executed if the server responds, whether or not the response contains data.
            //The String 'response' contains the server's response.
        }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name", username);
                MyData.put("email", email);
                MyData.put("password",password);
                MyData.put("password_confirmation", rePassword);
                return MyData;
            }
        };
    }


/*
    public static void postNewUser(Context context){
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,"http://10.0.2.2/ApiCrazyNuit/public/api/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //mPostCommentResponse.requestCompleted();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // mPostCommentResponse.requestEndedWithError(error);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name", username);
                //params.put("DataNaixement",null);
                params.put("email", email);
               // params.put("telefon",null);
               // params.put("dataAlta",null);
               // params.put("dataBaixa",null);
               // params.put("descripcio","");
                params.put("password",password);
                params.put("password_confirmation", rePassword);
               // password, rePassword;
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }
        };
        queue.add(sr);
    }

}
*/