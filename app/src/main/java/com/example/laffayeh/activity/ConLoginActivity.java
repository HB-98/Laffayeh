package com.example.laffayeh.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laffayeh.general.LocaleHelper;
import com.example.laffayeh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ConLoginActivity extends AppCompatActivity {
    TextInputEditText etCode;
    FirebaseAuth myAuth;
    String codeSent;
    DatabaseReference dbrf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_login);

        etCode = findViewById(R.id.etCode);
        myAuth = FirebaseAuth.getInstance();
        dbrf= FirebaseDatabase.getInstance().getReference().child("User");
        String phonenumber = getIntent().getStringExtra("phonenumber");
        sendTheCode(phonenumber);
    }

    public void conAndVer(View view) {

        if(TextUtils.isEmpty(etCode.getText().toString())){
            etCode.setError("Required Field");
            return;
        }

        if (etCode.getText().toString().length() < 6) {
            etCode.setError("Please enter the right code");
            etCode.requestFocus();
            return;
        }


        verifyTheCode();
    }
    private void verifyTheCode(){
        String code = etCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);

        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        myAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String si = getIntent().getStringExtra("si");
                            if (si.equals("login")) {
                                final String[] type = {""};
                                dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String phonenumber = getIntent().getStringExtra("phonenumber");
                                        String phone = phonenumber.substring(4,13);
                                        Toast.makeText(ConLoginActivity.this, ""+phonenumber, Toast.LENGTH_SHORT).show();
                                        for (DataSnapshot datas : dataSnapshot.getChildren()) {

                                            String myApproveValueID = datas.child("phone").getValue().toString();
                                            if (myApproveValueID.equals(phone)) {
                                                type[0] =datas.child("type").getValue().toString() ;
                                                SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(ConLoginActivity.this);
                                                SharedPreferences.Editor pen = daftar.edit();
                                                pen.putString("pic",datas.child("pic").getValue().toString());
                                                pen.putString("phone",datas.child("phone").getValue().toString());
                                                pen.putString("name",datas.child("name").getValue().toString());
                                                pen.putString("type",datas.child("type").getValue().toString());
                                                pen.apply();
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
                                        Toast.makeText(ConLoginActivity.this, ""+type[0], Toast.LENGTH_SHORT).show();

                                        if(type[0].equals("user"))
                                        {
                                             Intent i = new Intent(ConLoginActivity.this, UserHomeActivity.class);
                                             startActivity(i);
                                        }else if(type[0].equals("freeLance"))
                                        {
                                            Intent i = new Intent(ConLoginActivity.this, OfficeHomeActivity.class);
                                            startActivity(i);

                                        }else if(type[0].equals("office"))
                                        {
                                            Intent i = new Intent(ConLoginActivity.this, OfficeHomeActivity.class);
                                            startActivity(i);

                                        }

                                    }
                                }, 3000);
                            }
                            else
                            {
                                Toast.makeText(ConLoginActivity.this, "Phone Validated and sign up", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ConLoginActivity.this, ChooseRollActivity.class);
                                startActivity(i);
                            }
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(ConLoginActivity.this, "The code is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void sendTheCode(String phoneNumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s ;
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
}
