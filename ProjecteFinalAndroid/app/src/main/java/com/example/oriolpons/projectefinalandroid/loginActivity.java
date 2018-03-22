package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        //edtUserName  = (EditText) findViewById(R.id.edtUserName);
        //edtEmail  = (EditText) findViewById(R.id.edtEmail);
        //edtPassword  = (EditText) findViewById(R.id.edtPassword);
        //edtPasswordR  = (EditText) findViewById(R.id.edtPasswordR);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnLogin: loginData(); break;
            case R.id.btnRegister: registerUser(); break;
        }
    }

    private void loginData() {
        String Email, Password;

        TextView tv;
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration;
        Toast mensaje;


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

        //if (bd.userExist(Email, Password)){

       // }
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
    private void registerUser() {
        Intent i = new Intent(this, registerActivity.class );
        startActivity(i);
    }


}
