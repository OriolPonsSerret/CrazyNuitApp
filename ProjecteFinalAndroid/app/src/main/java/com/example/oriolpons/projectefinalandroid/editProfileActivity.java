package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oriolpons.projectefinalandroid.fragment.routesProfileFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class editProfileActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnBack;
    Button btnDeleteAccount, btnUserName, btnDescription, btnPassword, btnDate;

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

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration;
                Toast mensaje;

                if(!edtUserName.getText().toString().isEmpty()){

                }
                else{
                    text = "El campo no puede estar vacio.";
                    duration = 3;

                    mensaje = Toast.makeText(context, text, duration);
                    mensaje.show();
                }
            }
        });
        AlertDialog dialog = builder.create();
        builder.setView(view);
        dialog.show();
    }

    private void actionEditDescription() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_edit_description,null);

        final EditText edtDescription = (EditText) view.findViewById(R.id.edtDescription);
        Button btnAcceptDescription = (Button) view.findViewById(R.id.btnAcceptDescription);

        btnAcceptDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void actionEditPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_edit_password,null);

        final EditText edtPasswordOld = (EditText) view.findViewById(R.id.edtPasswordOld);
        final EditText edtPasswordNew = (EditText) view.findViewById(R.id.edtPasswordNew);
        final EditText edtPasswordNewR = (EditText) view.findViewById(R.id.edtPasswordNewR);
        Button btnAcceptPassword = (Button) view.findViewById(R.id.btnAcceptPassword);

        btnAcceptPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration;
                Toast mensaje;

                if(!edtPasswordOld.getText().toString().isEmpty()&&!edtPasswordNew.getText().toString().isEmpty()&&!edtPasswordNewR.getText().toString().isEmpty()){

                }
                else{
                    text = "Todos los campos son obligatorios.";
                    duration = 3;

                    mensaje = Toast.makeText(context, text, duration);
                    mensaje.show();
                }
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void actionEditDate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_edit_date,null);

        final DatePicker Calendar = (DatePicker) view.findViewById(R.id.dateCalendar);
        Button btnAcceptPassword = (Button) view.findViewById(R.id.btnAcceptDate);

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
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void actionDeleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_edit_deleteaccount,null);

        final EditText edtPasswordDelete = (EditText) view.findViewById(R.id.edtPasswordDelete);
        Button btnAcceptPassword = (Button) view.findViewById(R.id.btnAcceptDelete);

        btnAcceptPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv;
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration;
                Toast mensaje;

                if(!edtPasswordDelete.getText().toString().isEmpty()){

                }
                else{
                    text = "Debes confirmar tu contraseña para eliminar la cuenta.";
                    duration = 3;

                    mensaje = Toast.makeText(context, text, duration);
                    mensaje.show();
                }
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void actionBack() {
        finish();
    }

}
