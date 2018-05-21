package com.example.oriolpons.projectefinalandroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oriolpons.projectefinalandroid.R;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterRoutesProfile;
import com.example.oriolpons.projectefinalandroid.Models.routes;
import com.example.oriolpons.projectefinalandroid.routesContentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link routesProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link routesProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class routesProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<routes> listRoutes;
    private RecyclerView recyclerView;
    private String routeName, routeDescription, routeAssessment, routeCreator;
    private JSONObject data = null;
    private StringBuffer json;
    private String URL;

    public routesProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment routesProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static routesProfileFragment newInstance(String param1, String param2) {
        routesProfileFragment fragment = new routesProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_routes_profile,container,false);

        listRoutes=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        exampleRoutes();

        adapterRoutesProfile Adapter = new adapterRoutesProfile(listRoutes);
        Adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                routeName = listRoutes.get(recyclerView.getChildAdapterPosition(view)).getName();
                routeDescription = listRoutes.get(recyclerView.getChildAdapterPosition(view)).getDescription();
                routeAssessment = listRoutes.get(recyclerView.getChildAdapterPosition(view)).getAssessment() + "/5 - 1 votos";
                routeCreator = listRoutes.get(recyclerView.getChildAdapterPosition(view)).getCreator();
                intentRouteContent();
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







    private void intentRouteContent() {
        Bundle bundle = new Bundle();
        bundle.putString("name",routeName);
        bundle.putString("description",routeDescription);
        bundle.putString("assessment",routeAssessment);
        bundle.putString("creator",routeCreator);
        Intent intent = new Intent(getActivity(), routesContentActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
    private void exampleRoutes() {

        for(int index = 0; index<= 3; index++){

            listRoutes.add(new routes(index, "long","local " + index+ ".", "Una ruta muy entretenida.", "Creador " + index,  index + 0.0, 0));
        }
    }



    private void getJSON() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL(URL);//------------------------------------------------------------<<<<<<<<<<<<<< URL <<<<<<

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    json = new StringBuffer(1024);
                    String tmp = "";

                    while ((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());

                    if (data.getInt("cod") != 200) {
                        System.out.println("Cancelled");
                        return null;
                    }


                } catch (Exception e) {

                    //System.out.println("Exception " + e.getMessage());
                    // mostrarOpcion();
                    return null;
                }

                return null;
            }
            @Override
            protected void onPostExecute(Void Void) {
                if (data != null) {
                    Log.d("my weather received", data.toString());

                    try {
                        readData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.execute();
    }

    private void readData() throws JSONException {
        int id;
        String name = "", description = "", creator = "";
        double puntuation = 0.0;

        for (int i = 0; i < data.length(); i++) {
            id = (int) data.get("idusuaris");
           // name = (String) data.get("Nom");
           // description = (String) data.get("description");
           // creator = (String) data.get("creador");
           // assessmepuntuationnt = (String) data.get("puntuación");

            listRoutes.add(new routes(id, "long", name, description, creator, puntuation, 0));
        }
    }

}
