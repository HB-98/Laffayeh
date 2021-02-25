package com.example.laffayeh.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laffayeh.R;
import com.example.laffayeh.adapter.RecycleAdapterHome;
import com.example.laffayeh.model.Adv;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {
    RecyclerView recFav;
    DatabaseReference dbrf,dbrf1;


    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fav, container, false);

        final ArrayList<String> allFavAdvsKey = new ArrayList<>();
        final ArrayList<Adv> allFavAdvs = new ArrayList<>();

        recFav = v.findViewById(R.id.recFav);
        recFav.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        dbrf= FirebaseDatabase.getInstance().getReference().child("fav");
        dbrf1= FirebaseDatabase.getInstance().getReference().child("Adv");

     //   dbrf.addChildEventListener(new ChildEventListener() {
     //       @Override
     //       public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
     //           final SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
     //           if(dataSnapshot.child("phone").getValue().toString().equals(daftar.getString("phone" , "")))
     //           {
     //               allFavAdvsKey.add(dataSnapshot.child("key").getValue().toString());
     //           }
//
     //       }
//
     //       @Override
     //       public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
     //       }
//
     //       @Override
     //       public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
     //       }
//
     //       @Override
     //       public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
     //       }
//
     //       @Override
     //       public void onCancelled(@NonNull DatabaseError databaseError) {
//
     //       }
     //   });


        dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(getActivity());

                for(DataSnapshot datas: dataSnapshot.getChildren()){
        if(datas.child("phone").getValue().toString().equals(daftar.getString("phone" , ""))) {
            allFavAdvsKey.add(datas.child("key").getValue().toString());
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


                dbrf1.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        for (int i = 0 ; i<allFavAdvsKey.size();i++)
                        {
                            if (dataSnapshot.getKey().equals(allFavAdvsKey.get(i)))
                            {
                                Adv adv = new Adv();
                                adv.setPic(dataSnapshot.child("pic").getValue().toString());
                                adv.setName(dataSnapshot.child("name").getValue().toString());
                                adv.setWorksDay((ArrayList<String>) dataSnapshot.child("worksDay").getValue());
                                adv.setReligion(dataSnapshot.child("religion").getValue().toString());
                                adv.setNationality(dataSnapshot.child("nationality").getValue().toString());
                                adv.setExperience(dataSnapshot.child("experience").getValue().toString());
                                adv.setPhone(dataSnapshot.child("phone").getValue().toString());
                                adv.setWorkHours(dataSnapshot.child("workHours").getValue().toString());
                                adv.setLanguage(dataSnapshot.child("language").getValue().toString());
                                adv.setKey(dataSnapshot.getKey());
                                allFavAdvs.add(adv);
                                RecycleAdapterHome a=new RecycleAdapterHome(getActivity(),allFavAdvs , "user");
                                recFav.setAdapter(a);
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

            }
        }, 1500);


        return  v;

    }

}
