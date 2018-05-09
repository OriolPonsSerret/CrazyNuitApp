package com.example.oriolpons.projectefinalandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oriolpons.projectefinalandroid.Database.Datasource;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin, btnRegister;
    private EditText edtEmail, edtPassword;
    private CheckBox chbxRemember;
    private Datasource bd;
    private int quantity = 0, id = 0;
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bd = new Datasource(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        edtEmail  = (EditText) findViewById(R.id.edtEmail);
        edtPassword  = (EditText) findViewById(R.id.edtPassword);
        chbxRemember  = (CheckBox) findViewById(R.id.chbxRemember);


        I_have_a_remember_me();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin:
                loginData();
                break;
            case R.id.btnRegister:
                registerUser();
                break;
        }
    }

    private void I_have_a_remember_me() {
        Cursor cursor  = bd.remembermeCount();

        while(cursor.moveToNext()){
            quantity = cursor.getInt(0);
        }
        cursor = bd.remembermeGetUser();
        if (quantity > 0){
            while(cursor.moveToNext()){
                id = cursor.getInt(0);
                email = cursor.getString(1);
            }
        }
        edtEmail.setText(email);
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

        if(chbxRemember.isChecked()){
            if (quantity == 0){
                bd.userRememberAdd(id, email);
            }
            else{
                bd.userRememberUpdate(id, email);
            }
        }

        //if (bd.userExist(Email, Password)){

        // }
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, 1);
        //startActivity(i);
    }

    private void registerUser() {
        Intent i = new Intent(this, registerActivity.class);
        startActivity(i);
    }

    // public void onRestart()
    // {
    //}
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onRestart();

       AlertDialog.Builder builder = new AlertDialog.Builder(this);

       builder.setMessage("¿Estás seguro de volver a la pantalla de Iniciar Sesión?");
       builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
           }
       });

       builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               goBack();
           }
       });
       builder.show();


    }

    private void goBack() {
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, 1);
    }
}
