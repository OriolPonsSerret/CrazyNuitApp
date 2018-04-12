package com.example.oriolpons.projectefinalandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.oriolpons.projectefinalandroid.adapter.adapterRoutesProfile;
import com.example.oriolpons.projectefinalandroid.fragment.achievementProfileFragment;
import com.example.oriolpons.projectefinalandroid.fragment.followerProfileFragment;
import com.example.oriolpons.projectefinalandroid.fragment.followingProfileFragment;
import com.example.oriolpons.projectefinalandroid.fragment.routesProfileFragment;

import java.util.ArrayList;

public class profileActivity extends AppCompatActivity implements View.OnClickListener, routesProfileFragment.OnFragmentInteractionListener
                                                                                      , followerProfileFragment.OnFragmentInteractionListener
                                                                                      , followingProfileFragment.OnFragmentInteractionListener
                                                                                      , achievementProfileFragment.OnFragmentInteractionListener{

    FloatingActionButton btnEditProfile;
    ImageButton btnBack, btnHome, btnLocal, btnRoutes, btnUserProfile;
    Button btnMenu, btnLogOut, btnRoutesProfile, btnFollowersProfile, btnFollowingProfile, btnAchievementProfile;
    LinearLayout linearLayoutMenu;
    routesProfileFragment routesProfile;
    followerProfileFragment followerProfile;
    followingProfileFragment followingProfile;
    achievementProfileFragment achievementProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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


        //Si me encuentro en el perfil de otro usuario, el botón no se bloquea.
        btnUserProfile.setEnabled(false);

        btnRoutesProfile = (Button) findViewById(R.id.btnRoutesProfile);
        btnRoutesProfile.setOnClickListener(this);
        btnFollowersProfile = (Button) findViewById(R.id.btnFollowersProfile);
        btnFollowersProfile.setOnClickListener(this);
        btnFollowingProfile = (Button) findViewById(R.id.btnFollowingProfile);
        btnFollowingProfile.setOnClickListener(this);
        btnAchievementProfile = (Button) findViewById(R.id.btnAchievementProfile);
        btnAchievementProfile.setOnClickListener(this);

        btnRoutesProfile.setEnabled(false);
        buttonSelected();
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
            public void onClick(DialogInterface dialog, int id) {
                LogOut();
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
        Intent i = new Intent(this, editProfileActivity.class );
        startActivity(i);
    }
    private void intentMain() {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
    private void intentLocal() {
         Intent i = new Intent(this, localActivity.class );
         startActivity(i);
    }
    private void intentRoutes() {
        Intent i = new Intent(this, routesActivity.class );
        startActivity(i);
    }
    private void intentUserProfile() {
      //  Intent i = new Intent(this, profileActivity.class );
      //  startActivity(i);
    }



    public void onRestart()
    {
        super.onRestart();
        linearLayoutMenu.setVisibility(View.GONE);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
