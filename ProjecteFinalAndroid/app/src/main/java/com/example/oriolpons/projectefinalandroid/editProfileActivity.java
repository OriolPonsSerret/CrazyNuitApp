package com.example.oriolpons.projectefinalandroid;

import android.content.Context;
import android.content.Intent;
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
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
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

                    new HttpAsyncTaskDelete().execute("http://10.0.2.2/ApiCrazyNuit/public/api/usuaris/" + userId);

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






    public static String DELETE (String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make DELETE request to the given URL
            HttpDelete httpDelete = new HttpDelete(url);

            // 3. Set some headers to inform server about the type of the content
            httpDelete.setHeader("Accept", "application/json");
            httpDelete.setHeader("Content-type", "application/json");

            // 4. Execute DELETE request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpDelete);

            // 5. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 6. convert inputstream to string
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
    private class HttpAsyncTaskDelete extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return DELETE(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "¡Se ha eliminado tu cuenta!", Toast.LENGTH_LONG).show();
            bd.userDelete(userId);
            bd.RemembermeRemove();
            startActivity(new Intent(getBaseContext(), splashScreen.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
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







    //PUT
    private class HttpAsyncTaskUpdate extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return PUT(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "¡Se han aplicado los cambios a la ruta!", Toast.LENGTH_LONG).show();
        }
    }

    public String PUT (String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make PUT request to the given URL
            HttpPut httpPut = new HttpPut(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            //jsonObject.accumulate("idrutes", routeId);
            //jsonObject.put("rutmida", route_lenght);
            jsonObject.put("name", "");
            jsonObject.put("descripcio", "");
            jsonObject.put("telefon", "");
            jsonObject.put("DataNaixement", "");
            // jsonObject.put("rutvaloracio", routeAssessment);
            //jsonObject.put("rutcreador", userId);
            //jsonObject.put("rutlocals", listLocalNameInRoute);
            //jsonObject.put("rutdata", null);//routeDate


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPut Entity
            httpPut.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");

            // 8. Execute PUT request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPut);
            //ResponseObject:: org.apache.http.conn.HttpHostConnectException: Connection to http://localhost refused

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
        Log.d("InputStream", result.toString());
        // 11. return result
        return result;
    }
}
