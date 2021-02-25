package com.example.laffayeh.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laffayeh.R;
import com.example.laffayeh.activity.MapsActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View v =inflater.inflate(R.layout.fragment_map, container, false);
        Intent i = new Intent(getActivity(), MapsActivity.class);
        startActivity(i);

        return v;
    }

}
