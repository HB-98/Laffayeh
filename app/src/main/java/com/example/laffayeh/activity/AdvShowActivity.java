package com.example.laffayeh.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.laffayeh.model.Comments;
import com.example.laffayeh.general.LocaleHelper;
import com.example.laffayeh.R;
import com.example.laffayeh.adapter.RecycleAdapterComments;
import com.example.laffayeh.model.Adv;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdvShowActivity extends AppCompatActivity {
    DatabaseReference dbrf , dbrf1;
    TextView tvName , tvNationality , tvLanguage , tvPhone , tvReligion,tvWorkHours,tvWorkDays , tvExperience, tvAge;
    ImageView imgAdvDet;
    ImageButton btnSendComment;
    RecyclerView recComments;
    EditText etComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_show);
        Intent intent = getIntent();
        final String key = intent.getStringExtra("key");

        tvName = findViewById(R.id.tvName);
        tvNationality = findViewById(R.id.tvNationality);
        tvLanguage = findViewById(R.id.tvLanguage);
        tvPhone = findViewById(R.id.tvPhone);
        tvReligion = findViewById(R.id.tvReligion);
        tvWorkHours = findViewById(R.id.tvWorkHours);
        tvWorkDays = findViewById(R.id.tvWorkDays);
        tvExperience = findViewById(R.id.tvExperience);
        imgAdvDet = findViewById(R.id.imgAdvDet);
        tvAge = findViewById(R.id.tvAge);
        btnSendComment = findViewById(R.id.btnSendComment);
        recComments = findViewById(R.id.recComments);
        recComments.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        etComment = findViewById(R.id.etComment);
        final ArrayList<Comments> allComments = new ArrayList<>();



        dbrf= FirebaseDatabase.getInstance().getReference().child("Adv");
        dbrf1= FirebaseDatabase.getInstance().getReference().child("comments");
        dbrf1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.child("key").getValue().toString().equals(key))
                {
                    if (Integer.parseInt(dataSnapshot.child("report").getValue().toString()) <5)
                    {
                    Comments comments = new Comments();
                    comments.setName(dataSnapshot.child("name").getValue().toString());
                    comments.setComment(dataSnapshot.child("comment").getValue().toString());
                    comments.setCommentKey(dataSnapshot.getKey());
                    allComments.add(comments);
                    RecycleAdapterComments a=new RecycleAdapterComments(AdvShowActivity.this,allComments);
                    recComments.setAdapter(a);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Adv adv = new Adv();
        dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    if(datas.getKey().equals(key)) {

                        adv.setPic(datas.child("pic").getValue().toString());
                        adv.setName(datas.child("name").getValue().toString());
                        adv.setWorksDay((ArrayList<String>) datas.child("worksDay").getValue());
                        adv.setReligion(datas.child("religion").getValue().toString());
                        adv.setNationality(datas.child("nationality").getValue().toString());
                        adv.setExperience(datas.child("experience").getValue().toString());
                        adv.setPhone(datas.child("phone").getValue().toString());
                        adv.setWorkHours(datas.child("workHours").getValue().toString());
                        adv.setAge(datas.child("age").getValue().toString());
                        adv.setLanguage(datas.child("language").getValue().toString());
                        Glide.with(AdvShowActivity.this).load(adv.getPic()).into(imgAdvDet);

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
                String AllDaysWork = "";
                for (int i = 0 ; i <adv.getWorksDay().size() ; i++ )
                {
                    AllDaysWork = AllDaysWork +adv.getWorksDay().get(i) + " , ";
                }
                tvName.setText("  " +adv.getName());
                tvNationality.setText("  " +adv.getNationality());
                tvLanguage.setText("  " +adv.getLanguage());
                tvPhone.setText("  0" +adv.getPhone());
                tvReligion.setText("  " +adv.getReligion());
                tvWorkHours.setText("  " +adv.getWorkHours());
                tvExperience.setText("  " +adv.getExperience());
                tvWorkDays.setText("  " +AllDaysWork);
                tvAge.setText("  " + adv.getAge());



            }
        },1500);


        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(AdvShowActivity.this);

                Comments comments = new Comments();
                comments.setComment(etComment.getText().toString());
                comments.setName(daftar.getString("name" , ""));
                comments.setKey(key);
                comments.setReport(0);
                etComment.setText("");

                dbrf1.push().setValue(comments);
            }
        });



    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

}
