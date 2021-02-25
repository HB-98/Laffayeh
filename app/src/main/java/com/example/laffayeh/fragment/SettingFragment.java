package com.example.laffayeh.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.laffayeh.general.LocaleHelper;
import com.example.laffayeh.R;
import com.example.laffayeh.general.Constants;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    TextView change_language,change_phone;
    ConstraintLayout clan ;
    RadioGroup rgLan;
    RadioButton en , ar;
    Button btnChange;


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_setting, container, false);
        change_language=v.findViewById(R.id.tvChangeLanguage);
        clan = v.findViewById(R.id.contLan);
        rgLan = v.findViewById(R.id.rgLan);
        ar = v.findViewById(R.id.rbAr);
        en = v.findViewById(R.id.rbEn);
        change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clan.setVisibility(View.VISIBLE);
            }
        });

        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateViews("en");

                        getActivity().finish();
                        //startActivity(new Intent(this,GeneralSplashActivity.class));
                        startActivity(getActivity().getIntent());
                        SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor pen = daftar.edit();
                        pen.putInt("lang", Constants.ENGLISH_LANG);
                        pen.apply();
                        clan.setVisibility(View.INVISIBLE);
                    }
                }, 400);


            }
        });


        ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateViews("ar");

                        getActivity().finish();
                        //startActivity(new Intent(this,GeneralSplashActivity.class));
                        startActivity(getActivity().getIntent());
                        SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor pen = daftar.edit();
                        pen.putInt("lang", Constants.ARABIC_LANG);
                        pen.apply();
                        clan.setVisibility(View.INVISIBLE);
                    }
                }, 400);

            }
        });




        return v;
    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(getActivity(), languageCode);
        Resources resources = context.getResources();
    }

}
