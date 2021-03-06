package com.example.oriolpons.projectefinalandroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oriolpons.projectefinalandroid.R;
import com.example.oriolpons.projectefinalandroid.Adapters.adapterUser;
import com.example.oriolpons.projectefinalandroid.profileActivity;
import com.example.oriolpons.projectefinalandroid.Models.user;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link followingProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link followingProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class followingProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<user> listUsers;
    private RecyclerView recyclerView;
    private String userName;
    private TextView txtFollowingNumber;

    public followingProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment followingProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static followingProfileFragment newInstance(String param1, String param2) {
        followingProfileFragment fragment = new followingProfileFragment();
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

        View view = inflater.inflate(R.layout.fragment_following_profile,container,false);

        listUsers = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        txtFollowingNumber = view.findViewById(R.id.txtFollowingNumber);

        exampleUsers();

        adapterUser Adapter = new adapterUser(listUsers);
        Adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = listUsers.get(recyclerView.getChildAdapterPosition(view)).getName();
                intentUserProfile();
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







    private void intentUserProfile() {
        Bundle bundle = new Bundle();
        bundle.putString("type","another");
        bundle.putString("userName",userName);
        bundle.putString("user_email", profileActivity.userEmail);
        Intent intent = new Intent(getActivity(), profileActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    private void exampleUsers() {

        for(int index = 0; index<= 3; index++){

            listUsers.add(new user(index,"Usuario nº" + index+ ".", "El mejor usuario de esta app."));
        }

        txtFollowingNumber.setText(listUsers.size() + "");
    }
}
