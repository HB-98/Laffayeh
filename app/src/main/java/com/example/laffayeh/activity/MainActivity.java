package com.example.laffayeh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.laffayeh.general.LocaleHelper;
import com.example.laffayeh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.Logo);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.blink);
        logo.startAnimation(a);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    //  Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();
                Log.d("token", token);
                //ADd the token to shared preference

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                String type = daftar.getString("type" , "11");
                if(type.equals("11") || type.equals(""))
                {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else if(type.equals("user"))
                {
                    Intent i = new Intent(MainActivity.this, UserHomeActivity.class);
                    startActivity(i);
                    finish();
                }
                else if(type.equals("office"))
                {
                    Intent i = new Intent(MainActivity.this, OfficeHomeActivity.class);
                    startActivity(i);
                    finish();
                }
                else if(type.equals("freeLance"))
                {
                    Intent i = new Intent(MainActivity.this, OfficeHomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 4000);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

}