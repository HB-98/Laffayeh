package com.example.laffayeh.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.laffayeh.general.LocaleHelper;
import com.example.laffayeh.R;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class OfficeHomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_my_profile_office, R.id.nav_add_adv, R.id.nav_edit_adv,
                R.id.nav_setting, R.id.nav_contact_us, R.id.nav_about_us,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);
        navGraph.setStartDestination(R.id.nav_add_adv);
        navController.setGraph(navGraph);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NavigationView navigationView1 =  findViewById(R.id.nav_view);
                View header = navigationView1.getHeaderView(0);
                TextView tvName = header.findViewById(R.id.tvUserName);
                TextView tvPhone = header.findViewById(R.id.tvUserPhone);
                ImageView imgUser = header.findViewById(R.id.imgUser);
                SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(OfficeHomeActivity.this);
                tvName.setText(daftar.getString("name" , ""));
                tvPhone.setText("0" + daftar.getString("phone" , ""));
                Glide.with(OfficeHomeActivity.this).load(daftar.getString("pic" , "")).into(imgUser);
            }
        }, 1500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.office_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

}
