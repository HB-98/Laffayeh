package com.example.laffayeh.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditAdvFragment extends Fragment {
    RecyclerView recMyAds;
    DatabaseReference dbrf;


    public EditAdvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_edit_adv, container, false);
        final ArrayList<Adv> allAdvs = new ArrayList<>();

        recMyAds = v.findViewById(R.id.recMyAds);
        recMyAds.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        dbrf= FirebaseDatabase.getInstance().getReference().child("Adv");

        dbrf.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(getActivity());

                if(dataSnapshot.child("phone").getValue().toString().equals(daftar.getString("phone" , ""))) {
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
                    adv.setAge(dataSnapshot.child("age").getValue().toString());
                    adv.setKey(dataSnapshot.getKey());
                    allAdvs.add(adv);
                    RecycleAdapterHome a = new RecycleAdapterHome(getActivity(), allAdvs, "office");
                    recMyAds.setAdapter(a);
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




        return v;
    }

}
