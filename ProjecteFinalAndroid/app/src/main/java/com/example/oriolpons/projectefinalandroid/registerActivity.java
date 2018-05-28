package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oriolpons.projectefinalandroid.Database.Datasource;

import java.util.regex.Pattern;

public class registerActivity extends AppCompatActivity{

    private Button btnRegister;
    private EditText edtUserName, edtEmail, edtPassword, edtPasswordR;
    private String username = "",email = "", password = "", rePassword = "";
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


            if (!bd.userAskExist(email)){
                bd.userAdd(username, email);
            }

            text = "¡Te has registrado correctamente!";
            duration = 4;

            mensaje = Toast.makeText(context, text, duration);
            mensaje.show();

            finish();
        }
    }

    private boolean ValidationEmail() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
