package com.example.laffayeh.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laffayeh.activity.LoginActivity;
import com.example.laffayeh.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends Fragment {


    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_logout, container, false);

        SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor pen = daftar.edit();
        pen.putString("pic","");
        pen.putString("email","");
        pen.putString("phone","");
        pen.putString("name","");
        pen.putString("type","");
        pen.apply();

        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        return v;
    }

}
