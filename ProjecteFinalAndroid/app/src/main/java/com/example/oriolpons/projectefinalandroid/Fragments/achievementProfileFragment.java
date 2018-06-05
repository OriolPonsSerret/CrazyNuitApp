package com.example.oriolpons.projectefinalandroid.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oriolpons.projectefinalandroid.R;
import com.example.oriolpons.projectefinalandroid.Models.achievement;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterAchievementProfile;
import com.example.oriolpons.projectefinalandroid.profileActivity;
import com.example.oriolpons.projectefinalandroid.splashScreen;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link achievementProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link achievementProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class achievementProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<achievement> listAchievements;
    private RecyclerView recyclerView;
    private TextView txtAchievementNumber;
    private int userId;
    private Cursor cursor;

    public achievementProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment achievementProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static achievementProfileFragment newInstance(String param1, String param2) {
        achievementProfileFragment fragment = new achievementProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_achievement_profile,container,false);

        listAchievements = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        txtAchievementNumber = view.findViewById(R.id.txtAchievementNumber);

        if (profileActivity.type.equals("another")){
            cursor = profileActivity.bd.getUserInformationByName(profileActivity.anotherUserName);
        }
        else{
            cursor = profileActivity.bd.getUserInformationByName(profileActivity.userName);
        }
        while(cursor.moveToNext()){
            userId = cursor.getInt(0);
        }

        getJsonData getJson = new getJsonData();
        getJson.execute();


        adapterAchievementProfile Adapter = new adapterAchievementProfile(listAchievements);
        Adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        recyclerView.setAdapter(Adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





    private class getJsonData extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... argumentos) {

            StringBuffer bufferCadena = new StringBuffer("");

            try {
                HttpClient cliente = new DefaultHttpClient();
                HttpGet peticion = new HttpGet("http://10.0.2.2/ApiCrazyNuit/public/api/usuaris/" + userId);
                // ejecuta una petición get
                HttpResponse respuesta = cliente.execute(peticion);

                //lee el resultado
                BufferedReader entrada = new BufferedReader(new InputStreamReader(
                        respuesta.getEntity().getContent()));
                // Log.i("ResponseObject: ", respuesta.toString());

                String separador = "";
                String NL = System.getProperty("line.separator");
                //almacena el resultado en bufferCadena

                while ((separador = entrada.readLine()) != null) {
                    bufferCadena.append(separador + NL);
                }
                entrada.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                Log.i("ResponseObject: ", e.toString());
            }

            return bufferCadena.toString();

        }

        protected void onPostExecute(String data) {


            try {

                readDataFromJson(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(data);
            // Toast.makeText(localActivity.this, data, Toast.LENGTH_SHORT).show();

        }
    }


    private void readDataFromJson(String data) throws JSONException {
        int id = 0, achievementId = 0;
        String entrance_price = "", city = "Mataró", username = "", email = "", phonenumber = "", birthdate = "", name = "", description= "", address= "Mataró", opening_hours= "", schedule_close= "", gastronomy= "", locals = "", date = "";
        Double assessment = 1.0;
        int category = 4, route_lenght = 2, idCreator = 0;

        JSONArray jArray = new JSONArray(data);
        JSONObject jObject = jArray.getJSONObject(0);
        JSONArray jArrayAchi = jArray.getJSONArray(0);
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObjectAchi = jArrayAchi.getJSONObject(i);

            if (!jObjectAchi.get("idrutes").equals(null)){
                id = (int) jObjectAchi.get("idrutes");
            }
            else{id = 0;}

        }
        if(profileActivity.bd.achievementAskExistById(id)){
            cursor = profileActivity.bd.getAchievementById(id);
            while(cursor.moveToNext()){
                achievementId = cursor.getInt(0);
                name = cursor.getString(1);
                description = cursor.getString(2);

                listAchievements.add(new achievement(achievementId, name, description, date));
            }
        }

        txtAchievementNumber.setText(listAchievements.size() + "");
    }

}
