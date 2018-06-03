package com.example.oriolpons.projectefinalandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.oriolpons.projectefinalandroid.Database.Datasource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
// import com.google.android.gms.auth.api.signin.GoogleSignIn;
// import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
// import com.google.android.gms.auth.api.signin.GoogleSignInApi;
// import com.google.android.gms.auth.api.signin.GoogleSignInClient;
// import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
// import com.google.android.gms.common.SignInButton;
// import com.google.android.gms.common.api.ApiException;
// import com.google.android.gms.tasks.Task;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin, btnRegister;
    private EditText edtEmail, edtPassword;
    private CheckBox chbxRemember;
    private Datasource bd;
    private int quantity = 0, id = 0;
    private String email = "", TAG = "LoginActivity", url = "http://10.0.2.2/ApiCrazyNuit/public/api/login";;
    private ProgressDialog progressDialog;

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
        getSupportActionBar().setTitle("Iniciar sesión");
    }

    @Override
    public void onStart() {
        super.onStart();

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
        Cursor cursor = bd.remembermeGetUser();
        while(cursor.moveToNext()){
            email = cursor.getString(0);
        }

        edtEmail.setText(email);
    }


    private void loginData() {
        String Password;

        TextView tv;

        tv = (TextView) findViewById(R.id.edtEmail);
        email = tv.getText().toString();
        if (email.trim().equals("")) {
            edtEmail.setError("El campo no puede estar vacío.");
            return;
        }

        tv = (TextView) findViewById(R.id.edtPassword);
        Password = tv.getText().toString();
        if (Password.trim().equals("")) {
            edtPassword.setError("La Contraseña és obligatoria.");
            return;
        }

        if (!ValidationEmail()){
            edtEmail.setError("Formato del Correo no és válido.");
            return;
        }
        else {

            if (chbxRemember.isChecked()) {
                bd.UserRemembermeAdd(email);
            }

            Bundle bundle = new Bundle();
            bundle.putString("user_email", email);
            Intent i = new Intent(this, MainActivity.class);
            i.putExtras(bundle);
            startActivityForResult(i, 1);
        }
    }

    private void registerUser() {
        Intent i = new Intent(this, registerActivity.class);
        startActivity(i);
    }


   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onRestart();
       edtEmail.setText("");
       edtPassword.setText("");

       AlertDialog.Builder builder = new AlertDialog.Builder(this);

       builder.setMessage("¿Estás seguro de volver a la pantalla de Iniciar Sesión?");
       builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               bd.RemembermeRemove();
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
        Bundle bundle = new Bundle();
        bundle.putString("user_email", email);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtras(bundle);
        startActivityForResult(i, 1);
    }

    private boolean ValidationEmail() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }




/*
    private void loginUser( final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        progressDialog.setMessage("Logging you in...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        // Launch User activity

                        loginComplete();
                        //finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
    }



    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
*/

    private void loginComplete() {
        Bundle bundle = new Bundle();
        bundle.putString("user_email", email);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtras(bundle);
        startActivityForResult(i, 1);
    }
}
