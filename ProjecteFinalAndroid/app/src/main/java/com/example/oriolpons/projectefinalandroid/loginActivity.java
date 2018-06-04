package com.example.oriolpons.projectefinalandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private String TAG = "LoginActivity", url = "http://10.0.2.2/ApiCrazyNuit/public/api/login";
    private ProgressDialog progressDialog;
    private static String email = "", password = "";
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

        TextView tv;

        tv = (TextView) findViewById(R.id.edtEmail);
        email = tv.getText().toString();
        if (email.trim().equals("")) {
            edtEmail.setError("El campo no puede estar vacío.");
            return;
        }

        tv = (TextView) findViewById(R.id.edtPassword);
        password = tv.getText().toString();
        if (password.trim().equals("")) {
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
            new HttpAsyncTask().execute("http://10.0.2.2/ApiCrazyNuit/public/api/login");
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




    public static String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("email", email);
            jsonObject.accumulate("password", password);

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (result.length() < 150){
                edtPassword.setError("El correo o la contraseña es incorrecta.");
            }
            else{
                loginComplete();
            }
            //Log.i("Resultado: ", result.length() + "");
            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            //Toast.makeText(getBaseContext(), "¡Se ha creado la ruta correctamente!", Toast.LENGTH_LONG).show();

        }
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

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
