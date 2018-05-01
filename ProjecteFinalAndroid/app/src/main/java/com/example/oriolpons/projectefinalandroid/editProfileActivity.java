package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class editProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btnBack;
    private Button btnDeleteAccount, btnUserName, btnDescription, btnPassword, btnDate;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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


                    text = "Se han guardado los cambios.";
                    duration = 3;

                    mensaje = Toast.makeText(context, text, duration);
                    mensaje.show();
                }
                else{
                    text = "El campo no puede estar vacio.";
                    duration = 3;

                    mensaje = Toast.makeText(context, text, duration);
                    mensaje.show();
                }

                dialog.cancel();
            }
        });

    }

    private void actionEditDescription() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_edit_description,null);

        final EditText edtDescription = (EditText) view.findViewById(R.id.edtDescription);
        Button btnAcceptDescription = (Button) view.findViewById(R.id.btnAcceptDescription);

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

}
