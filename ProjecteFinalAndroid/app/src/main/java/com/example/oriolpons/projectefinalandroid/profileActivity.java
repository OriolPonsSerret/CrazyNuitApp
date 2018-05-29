package com.example.oriolpons.projectefinalandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oriolpons.projectefinalandroid.Database.Datasource;
import com.example.oriolpons.projectefinalandroid.Fragments.achievementProfileFragment;
import com.example.oriolpons.projectefinalandroid.Fragments.followerProfileFragment;
import com.example.oriolpons.projectefinalandroid.Fragments.followingProfileFragment;
import com.example.oriolpons.projectefinalandroid.Fragments.routesProfileFragment;
import com.example.oriolpons.projectefinalandroid.Models.local;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class profileActivity extends AppCompatActivity implements View.OnClickListener, routesProfileFragment.OnFragmentInteractionListener
                                                                                      , followerProfileFragment.OnFragmentInteractionListener
                                                                                      , followingProfileFragment.OnFragmentInteractionListener
                                                                                      , achievementProfileFragment.OnFragmentInteractionListener{

    private FloatingActionButton btnEditProfile, btnFollow, btnUnfollow;
    private ImageButton btnBack, btnHome, btnLocal, btnRoutes, btnUserProfile;
    private Button btnMenu, btnLogOut, btnRoutesProfile, btnFollowersProfile, btnFollowingProfile, btnAchievementProfile;
    private LinearLayout linearLayoutMenu;
    private routesProfileFragment routesProfile;
    private followerProfileFragment followerProfile;
    private followingProfileFragment followingProfile;
    private achievementProfileFragment achievementProfile;
    private TextView tvUserName, tvUserDescription;
    private JSONObject data = null;
    private StringBuffer json;
    private String URL;
    public static Datasource bd;
    private Cursor cursor;

    private long id = 0;
    private String name = "", description = "", bornDate = "", email = "", telephone = "";
    public static String type = "", userEmail = "", userName = "", anotherUserName = "", anotherDescription = "";
    public static int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bd = new Datasource(this);

        routesProfile = new routesProfileFragment();
        followerProfile = new followerProfileFragment();
        followingProfile = new followingProfileFragment();
        achievementProfile = new achievementProfileFragment();


        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);

        linearLayoutMenu = (LinearLayout) findViewById(R.id.linearLayoutMenu);
        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
        btnLocal = (ImageButton) findViewById(R.id.btnLocal);
        btnLocal.setOnClickListener(this);
        btnRoutes = (ImageButton) findViewById(R.id.btnRoutes);
        btnRoutes.setOnClickListener(this);
        btnUserProfile = (ImageButton) findViewById(R.id.btnUserProfile);
        btnUserProfile.setOnClickListener(this);


        btnEditProfile = (FloatingActionButton) findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEditProfile();
            }
        });

        btnFollow = (FloatingActionButton) findViewById(R.id.btnFollow);
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followOrUnfollow();
            }
        });

        btnUnfollow = (FloatingActionButton) findViewById(R.id.btnUnfollow);
        btnUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followOrUnfollow();
            }
        });


        btnRoutesProfile = (Button) findViewById(R.id.btnRoutesProfile);
        btnRoutesProfile.setOnClickListener(this);
        btnFollowersProfile = (Button) findViewById(R.id.btnFollowersProfile);
        btnFollowersProfile.setOnClickListener(this);
        btnFollowingProfile = (Button) findViewById(R.id.btnFollowingProfile);
        btnFollowingProfile.setOnClickListener(this);
        btnAchievementProfile = (Button) findViewById(R.id.btnAchievementProfile);
        btnAchievementProfile.setOnClickListener(this);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvUserDescription = (TextView) findViewById(R.id.tvUserDescription);

        type = this.getIntent().getExtras().getString("type");

        userEmail = this.getIntent().getExtras().getString("user_email");



        if (type.equals("another")){
            anotherUserName = this.getIntent().getExtras().getString("userName");
            setValuesAnotherProfile();
            btnUserProfile.setEnabled(true);
            btnLogOut.setVisibility(View.GONE);
            btnEditProfile.setVisibility(View.GONE);
            btnFollow.setVisibility(View.VISIBLE);
        }
        else{
            userName = this.getIntent().getExtras().getString("userName");
            setValuesMyProfile();
            btnUserProfile.setEnabled(false);
        }

        btnRoutesProfile.setEnabled(false);
        buttonSelected();
        getSupportActionBar().setTitle("Perfil de usuario");
    }

    public void onRestart()
    {
        super.onRestart();
        linearLayoutMenu.setVisibility(View.GONE);

        setValuesMyProfile();


    }

    private void setValuesMyProfile() {
        cursor = bd.getUserInformationByName(userName);
        while(cursor.moveToNext()){
            userId = cursor.getInt(0);
            userName = cursor.getString(1);
            description = cursor.getString(2);
            if (userEmail.equals(cursor.getString(3))){
                type = "me";
            }else{type = "another";}
        }

        tvUserName.setText(userName);
        tvUserDescription.setText(description);
    }

    private void setValuesAnotherProfile() {
        cursor = bd.getUserInformationByName(anotherUserName);
        while(cursor.moveToNext()){
            anotherUserName = cursor.getString(1);
            anotherDescription = cursor.getString(2);
        }

        tvUserName.setText(anotherUserName);
        tvUserDescription.setText(anotherDescription);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnLogOut: intentLogOut(); break;
            case R.id.btnHome: intentMain(); break;
            case R.id.btnLocal: intentLocal(); break;
            case R.id.btnRoutes: intentRoutes(); break;
            case R.id.btnUserProfile: intentUserProfile(); break;
            case R.id.btnMenu: actionShowHideMenu(); break;
            case R.id.btnBack: actionBack(); break;
            case R.id.btnRoutesProfile: actionSelectedRoutes(); break;
            case R.id.btnFollowersProfile: actionSelectedFollowers(); break;
            case R.id.btnFollowingProfile: actionSelectedFollowing(); break;
            case R.id.btnAchievementProfile: actionSelectedAchievement(); break;
        }
    }
    private void followOrUnfollow() {
        if (btnUnfollow.getVisibility() == View.GONE){
            btnFollow.setVisibility(View.GONE);
            btnUnfollow.setVisibility(View.VISIBLE);
        }
        else{
            btnFollow.setVisibility(View.VISIBLE);
            btnUnfollow.setVisibility(View.GONE);
        }
    }

    private void buttonSelected() {
        if (!btnRoutesProfile.isEnabled()){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, routesProfile,null).commit();
        }
        if (!btnFollowersProfile.isEnabled()){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, followerProfile,null).commit();
        }
        if (!btnFollowingProfile.isEnabled()){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, followingProfile,null).commit();
        }
        if (!btnAchievementProfile.isEnabled()){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, achievementProfile,null).commit();
        }
    }

    private void actionSelectedRoutes() {
        btnRoutesProfile.setEnabled(false);
        btnFollowersProfile.setEnabled(true);
        btnFollowingProfile.setEnabled(true);
        btnAchievementProfile.setEnabled(true);
        buttonSelected();

    }
    private void actionSelectedFollowers() {
        btnRoutesProfile.setEnabled(true);
        btnFollowersProfile.setEnabled(false);
        btnFollowingProfile.setEnabled(true);
        btnAchievementProfile.setEnabled(true);
        buttonSelected();
    }
    private void actionSelectedFollowing() {
        btnRoutesProfile.setEnabled(true);
        btnFollowersProfile.setEnabled(true);
        btnFollowingProfile.setEnabled(false);
        btnAchievementProfile.setEnabled(true);
        buttonSelected();
    }
    private void actionSelectedAchievement() {
        btnRoutesProfile.setEnabled(true);
        btnFollowersProfile.setEnabled(true);
        btnFollowingProfile.setEnabled(true);
        btnAchievementProfile.setEnabled(false);
        buttonSelected();
    }

    private void intentLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Estás seguro de que quieres cerrar sesión?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { LogOut();
            }
        });

        builder.setNegativeButton("No", null);
        builder.show();

    }

    private void LogOut() {
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration;
        Toast mensaje;

        bd.RemembermeRemove();

        text = "Se ha cerrado la sesión correctamente.";
        duration = 4;

        mensaje = Toast.makeText(context, text, duration);
        mensaje.show();

        Intent i = new Intent(this, loginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void actionShowHideMenu() {

        if (linearLayoutMenu.getVisibility() == View.VISIBLE) {
            linearLayoutMenu.setVisibility(View.GONE);
        } else {
            linearLayoutMenu.setVisibility(View.VISIBLE);
        }
    }
    private void actionBack() {

        if (linearLayoutMenu.getVisibility() == View.VISIBLE) {
            linearLayoutMenu.setVisibility(View.GONE);
        } else {
            finish();
        }

    }

    private void loadEditProfile() {
        Bundle bundle = new Bundle();
        bundle.putString("user_email", userEmail);
        Intent i = new Intent(this, editProfileActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }
    private void intentMain() {
        Bundle bundle = new Bundle();
        bundle.putString("user_email", userEmail);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }
    private void intentLocal() {
        Bundle bundle = new Bundle();
        bundle.putString("user_email", userEmail);
        Intent i = new Intent(this, localActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }
    private void intentRoutes() {
        Bundle bundle = new Bundle();
        bundle.putString("user_email", userEmail);
        Intent i = new Intent(this, routesActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }
    private void intentUserProfile() {
        String userName = "";

        cursor = bd.getUserInformationByEmail(userEmail);
        while(cursor.moveToNext()){
            userName = cursor.getString(1);
        }
        Bundle bundle = new Bundle();
        bundle.putString("type","me");
        bundle.putString("userName",userName);
        bundle.putString("user_email", userEmail);
        Intent i = new Intent(this, profileActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
