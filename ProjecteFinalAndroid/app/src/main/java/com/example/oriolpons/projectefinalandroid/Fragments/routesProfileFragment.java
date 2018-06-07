package com.example.oriolpons.projectefinalandroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;

import com.example.oriolpons.projectefinalandroid.R;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterRoutesProfile;
import com.example.oriolpons.projectefinalandroid.Models.routes;
import com.example.oriolpons.projectefinalandroid.profileActivity;
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
    private TextView txtRoutesNumber;
    private int routeId;

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

        listRoutes = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        txtRoutesNumber = view.findViewById(R.id.txtRoutesNumber);

        databaseToRouteList();

        adapterRoutesProfile Adapter = new adapterRoutesProfile(listRoutes);
        Adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                routeId = listRoutes.get(recyclerView.getChildAdapterPosition(view)).getId();
                routeName = listRoutes.get(recyclerView.getChildAdapterPosition(view)).getName();
                routeDescription = listRoutes.get(recyclerView.getChildAdapterPosition(view)).getDescription();
                routeAssessment = listRoutes.get(recyclerView.getChildAdapterPosition(view)).getAssessment() + "/5";
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
        bundle.putInt("id",routeId);
        bundle.putString("name",routeName);
        bundle.putString("description",routeDescription);
        bundle.putString("assessment",routeAssessment);
        bundle.putString("creator",routeCreator);
        bundle.putString("user_email", profileActivity.userEmail);
        Intent intent = new Intent(getActivity(), routesContentActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }



    private void databaseToRouteList() {

        Cursor cursor;
        int id;
        String measure = "", name, description, creator = "", city, rute_locals, route_date;
        Double assessment, entrance_price;
        int route_lenght, idCreator = 0;

        if (profileActivity.type.equals("another")){
            cursor = profileActivity.bd.getUserInformationByName(profileActivity.anotherUserName);
        }
        else{
            cursor = profileActivity.bd.getUserInformationByName(profileActivity.userName);
        }

        while(cursor.moveToNext()){
            idCreator = cursor.getInt(0);
        }


        cursor = profileActivity.bd.showAllRoutesUser(idCreator);
        while(cursor.moveToNext()){
            id = cursor.getInt(0);
            route_lenght = cursor.getInt(1);
            name = cursor.getString(2);
            description = cursor.getString(3);
            assessment = cursor.getDouble(4);
            idCreator = cursor.getInt(5);
            city = cursor.getString(6);
            rute_locals = cursor.getString(7);
            route_date = cursor.getString(8);

            if (route_lenght <= 3){
                measure = "short";
            }else{
                if (route_lenght <= 6){
                    measure = "halfways";
                }else{
                    if (route_lenght <= 10){
                        measure = "long";
                    }
                }
            }

            Cursor cursor2 = profileActivity.bd.getUserInformationById(idCreator);
            while(cursor2.moveToNext()){
                creator = cursor2.getString(1);
            }

            listRoutes.add(new routes(id, measure, name, description, creator, assessment, city, 0));
        }
        txtRoutesNumber.setText(listRoutes.size() + "");
    }
}
