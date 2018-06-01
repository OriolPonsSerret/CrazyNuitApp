package com.example.oriolpons.projectefinalandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class registerActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText edtUserName, edtEmail, edtPassword, edtPasswordR;
    private static String username = "", email = "", password = "", rePassword = "", url = "http://10.0.2.2/ApiCrazyNuit/public/api/register";
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

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPasswordR = (EditText) findViewById(R.id.edtPasswordR);


        if(isConnected()){
        }
        else{
        }

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
        } else {
            edtPasswordR.setError("Las Contraseñas no coinciden.");
            return;
        }

        if (!ValidationEmail()) {
            edtEmail.setError("Correo no válido.");
            return;
        } else {

            if (bd.userAskExist(email)) {
                edtEmail.setError("Ya existe un usuario con este correo.");
                return;
            } else {
                //Start to Register user
                new HttpAsyncTask().execute("http://10.0.2.2/ApiCrazyNuit/public/api/register");

                //postNewUser(context);
                //RequestQueue RequestQueue = Volley.newRequestQueue(this);
                //RequestQueue.add(MyPostRequest);

/*
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    String URL = "http://10.0.2.2/ApiCrazyNuit/public/api/register";
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("name", username);
                    jsonBody.put("email", email);
                    jsonBody.put("password", password);
                    jsonBody.put("password_confirmation", rePassword);
                    final String requestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                                // can get more details such as response.headers
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
*/

                text = "¡Te has registrado correctamente!";

                mensaje = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                mensaje.show();

                finish();
            }

        }
    }

    private boolean ValidationEmail() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }



    //Post method
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
            jsonObject.accumulate("name", username);
            jsonObject.accumulate("email", email);
            jsonObject.accumulate("password", password);
            jsonObject.accumulate("password_confirmation", rePassword);


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

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "¡Te has registrado correctamente!", Toast.LENGTH_LONG).show();
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
}




    /*
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
*/

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