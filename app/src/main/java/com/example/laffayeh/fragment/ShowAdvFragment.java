
package com.example.laffayeh.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laffayeh.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAdvFragment extends Fragment {
TextView tvTest;

    public ShowAdvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_adv, container, false);
        tvTest = v.findViewById(R.id.tvTest);
        Bundle b = getArguments();
        String phone = b.getInt("phone")+"";
        tvTest.setText(phone + " ");
        return v ;
    }

}
