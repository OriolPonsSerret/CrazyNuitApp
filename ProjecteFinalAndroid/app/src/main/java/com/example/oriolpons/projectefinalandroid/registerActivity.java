package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity{

    Button btnRegister;
   // EditText edtUserName, edtEmail, edtPassword, edtPasswordR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkData();
            }
        });

        //edtUserName  = (EditText) findViewById(R.id.edtUserName);
        //edtEmail  = (EditText) findViewById(R.id.edtEmail);
        //edtPassword  = (EditText) findViewById(R.id.edtPassword);
        //edtPasswordR  = (EditText) findViewById(R.id.edtPasswordR);

    }

    private void checkData() {
        String UserName, Email, Password, PasswordRepeat;

        TextView tv;
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration;
        Toast mensaje;

        tv = (TextView) findViewById(R.id.edtUserName);
        UserName = tv.getText().toString();
        if (UserName.trim().equals("")) {
            text = "El nombre de usuario es obligatorio.";
            duration = 3;

            mensaje = Toast.makeText(context, text, duration);
            mensaje.show();
            return;
        }

        tv = (TextView) findViewById(R.id.edtEmail);
        Email = tv.getText().toString();
        if (Email.trim().equals("")) {
            text = "El email es obligatorio.";
            duration = 3;

            mensaje = Toast.makeText(context, text, duration);
            mensaje.show();
            return;
        }

        tv = (TextView) findViewById(R.id.edtPassword);
        Password = tv.getText().toString();
        if (Password.trim().equals("")) {
            text = "La contraseña es obligatoria.";
            duration = 3;

            mensaje = Toast.makeText(context, text, duration);
            mensaje.show();
            return;
        }

        tv = (TextView) findViewById(R.id.edtPasswordR);
        PasswordRepeat = tv.getText().toString();
        if (PasswordRepeat.trim().equals("")) {
            text = "Tienes que volver a repetir la contraseña.";
            duration = 3;

            mensaje = Toast.makeText(context, text, duration);
            mensaje.show();
            return;
        }

        if (Password.trim().equals(PasswordRepeat)) {
        }
        else{
            text = "Las contraseñas no coinciden.";
            duration = 3;

            mensaje = Toast.makeText(context, text, duration);
            mensaje.show();
            return;
        }

        //bd.existUserName(UserName);
        //bd.existEmail(Email);
        //bd.userAdd(UserName, Email, Password, PasswordRepeat);
        text = "¡Te has registrado correctamente! Ahora puedes inicar sesión.";
        duration = 4;

        mensaje = Toast.makeText(context, text, duration);
        mensaje.show();


        finish();
    }
}
