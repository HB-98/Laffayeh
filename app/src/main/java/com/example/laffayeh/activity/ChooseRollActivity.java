package com.example.laffayeh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laffayeh.general.LocaleHelper;
import com.example.laffayeh.R;

public class ChooseRollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_roll);
    }

    public void user(View view) {
        Intent i = new Intent(this, SignUpUserActivity.class);
        startActivity(i);

    }

    public void office(View view) {
        Intent i = new Intent(this, SignUpOfficeActivity.class);
        startActivity(i);

    }

    public void freelancer(View view) {
        Intent i = new Intent(this, SignUpFreelancerActivity.class);
        startActivity(i);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

}
