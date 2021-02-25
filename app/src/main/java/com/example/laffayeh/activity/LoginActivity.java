package com.example.laffayeh.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laffayeh.general.CountryData;
import com.example.laffayeh.general.LocaleHelper;
import com.example.laffayeh.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Spinner spCountries;
    TextInputEditText etPhone;
    TextView signup;
    DatabaseReference dbrf;
    Boolean used = false;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spCountries = findViewById(R.id.spCountries);
        etPhone = findViewById(R.id.etCode);
        signup = findViewById(R.id.signup);
        btnAdd = findViewById(R.id.btnAdd);
        dbrf= FirebaseDatabase.getInstance().getReference().child("User");

        spCountries.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signup.setVisibility(View.INVISIBLE);
                signup.setText("");
            }
        });
    }


    public void con(View view) {
        btnAdd.setEnabled(false);

        if(signup.getText().toString().equals(""))
        {

            dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot datas : dataSnapshot.getChildren()) {

                        String myApproveValueID = datas.child("phone").getValue().toString();
                        Integer myPhone = Integer.parseInt( etPhone.getText().toString());
                        if (myApproveValueID.equals(myPhone.toString())) {
                            used = true;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnAdd.setEnabled(true);

                    if (used) {
                        Toast.makeText(LoginActivity.this, "This Phone was Used", Toast.LENGTH_SHORT).show();
                    } else {
                        String code = CountryData.countryAreaCodes[spCountries.getSelectedItemPosition()];

                        if(TextUtils.isEmpty(etPhone.getText().toString()))
                        {
                            etPhone.setError("Required Field");
                            return;
                        }
                        if (etPhone.getText().toString().length() < 10) {
                            etPhone.setError("Valid number is required");
                            etPhone.requestFocus();
                            return;
                        }

                        int phone =Integer.parseInt( etPhone.getText().toString());
                        SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor pen = daftar.edit();
                        pen.putInt("phoneToVali",phone);
                        pen.apply();
                        String si = "login";
                        if (signup.getText().toString().equals(""))
                        {
                            si = "signup";
                        }
                        String phoneNumber = "+" + code + phone;
                        Intent i = new Intent(LoginActivity.this, ConLoginActivity.class);
                        i.putExtra("phonenumber", phoneNumber);
                        i.putExtra("si",si);
                        startActivity(i);

                    }
                }
            }, 3000);
        }
        else
        {
            String code = CountryData.countryAreaCodes[spCountries.getSelectedItemPosition()];
            int phone =Integer.parseInt( etPhone.getText().toString());
            SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            SharedPreferences.Editor pen = daftar.edit();
            pen.putInt("phoneToVali",phone);
            pen.apply();
            String si = "login";
            if (signup.getText().toString().equals(""))
            {
                si = "signup";
            }
            String phoneNumber = "+" + code + phone;
            Intent i = new Intent(LoginActivity.this,ConLoginActivity.class);
            i.putExtra("phonenumber", phoneNumber);
            i.putExtra("phone", phone);
            i.putExtra("si",si);
            startActivity(i);


    }}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

}
