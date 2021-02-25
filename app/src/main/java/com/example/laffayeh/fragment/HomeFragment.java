package com.example.laffayeh.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.laffayeh.R;
import com.example.laffayeh.adapter.RecycleAdapterHome;
import com.example.laffayeh.model.Adv;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    RecyclerView recAllAdv;
    DatabaseReference dbrf;
    ImageView imgfillter,imgfilter2;
    String filterRes ="  All                                                    ";
    String type;
    ArrayList<Adv> allAdvs = new ArrayList<>();
    String selecedCateg , selecedSubCateg;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_home, container, false);

        recAllAdv = v.findViewById(R.id.recAllAdv);
        imgfillter = v.findViewById(R.id.imgfillter);
        imgfilter2 = v.findViewById(R.id.imgfilter2);


        imgfilter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRadioButtonDialog2();
            }
        });


        imgfillter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRadioButtonDialog();
            }
        });

        recAllAdv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        dbrf= FirebaseDatabase.getInstance().getReference().child("Adv");


        show();

        return v;
    }

    private void showRadioButtonDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_dialog);
        List<String> stringList=new ArrayList<>();  // here is list
        stringList.add("  FreeLance                                              ");

        stringList.add("  Office                                                 ");

        stringList.add("  All                                                    ");

        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

        for(int i=0;i<stringList.size();i++){
            RadioButton rb=new RadioButton(getActivity()); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(stringList.get(i));
            rg.addView(rb);
        }

        dialog.show();


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {

                        filterRes= btn.getText().toString();
                        dialog.dismiss();
                        show();

                        }
                }
            }
        });

    }


    private void showRadioButtonDialog2() {


        final String[] option={"experience","language","nationality","religion","workHours","age"};

        final AlertDialog.Builder[] alt_bld = {new AlertDialog.Builder(getActivity() ,R.style.MyAlertDialogTheme)};

        alt_bld[0].setTitle("Please Select Category :");
        alt_bld[0].setCancelable(true);



        final String[] selectedOption = new String[1];

        final String[] selectedItem = {""};

        alt_bld[0].setSingleChoiceItems(option, -1, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                selectedItem[0] =option[item];
                selecedCateg = selectedItem[0];
                dialog.dismiss();

                if(selecedCateg.equals("experience"))
                {
                    final String[] option2={"taking care of children","taking care of old people","housekeeping","house cleaning"};

                    final AlertDialog.Builder[] alt_bld2 = {new AlertDialog.Builder(getActivity() ,R.style.MyAlertDialogTheme)};

                    alt_bld2[0].setTitle("Please Select :");
                    alt_bld2[0].setCancelable(true);



                    final String[] selectedOption2 = new String[1];

                    final String[] selectedItem2 = {""};

                    alt_bld2[0].setSingleChoiceItems(option2, -1, new DialogInterface
                            .OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            selectedItem2[0] =option2[item];
                            selecedSubCateg = selectedItem2[0];
                            dialog.dismiss();
                            show2(selecedCateg,selecedSubCateg);

                        }
                    });

                    AlertDialog alert2 = alt_bld2[0].create();
                    alert2.show();

                }
                else if(selecedCateg.equals("language"))

                {
                    final String[] option2={"Arabic","English"};

                    final AlertDialog.Builder[] alt_bld2 = {new AlertDialog.Builder(getActivity() ,R.style.MyAlertDialogTheme)};

                    alt_bld2[0].setTitle("Please Select :");
                    alt_bld2[0].setCancelable(true);



                    final String[] selectedOption2 = new String[1];

                    final String[] selectedItem2 = {""};

                    alt_bld2[0].setSingleChoiceItems(option2, -1, new DialogInterface
                            .OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            selectedItem2[0] =option2[item];
                            selecedSubCateg = selectedItem2[0];
                            dialog.dismiss();
                            show2(selecedCateg,selecedSubCateg);

                        }
                    });

                    AlertDialog alert2 = alt_bld2[0].create();
                    alert2.show();

                }
                else if(selecedCateg.equals("religion"))
                {
                    final String[] option2={"Islam","Christianity","secularism","Hinduism","Chinese folk religion","Buddhism","Traditional African Religions","Sikhism","spirituality","Judaism","Bahaai","jain","Shinto","Zoroastrianism","paganism","Rastafari"};

                    final AlertDialog.Builder[] alt_bld2 = {new AlertDialog.Builder(getActivity() ,R.style.MyAlertDialogTheme)};

                    alt_bld2[0].setTitle("Please Select :");
                    alt_bld2[0].setCancelable(true);



                    final String[] selectedOption2 = new String[1];

                    final String[] selectedItem2 = {""};

                    alt_bld2[0].setSingleChoiceItems(option2, -1, new DialogInterface
                            .OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            selectedItem2[0] =option2[item];
                            selecedSubCateg = selectedItem2[0];
                            dialog.dismiss();
                            show2(selecedCateg,selecedSubCateg);

                        }
                    });

                    AlertDialog alert2 = alt_bld2[0].create();
                    alert2.show();

                }

                else if(selecedCateg.equals("workHours"))
                {
                    final String[] option2={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};

                    final AlertDialog.Builder[] alt_bld2 = {new AlertDialog.Builder(getActivity() ,R.style.MyAlertDialogTheme)};

                    alt_bld2[0].setTitle("Please Select :");
                    alt_bld2[0].setCancelable(true);



                    final String[] selectedOption2 = new String[1];

                    final String[] selectedItem2 = {""};

                    alt_bld2[0].setSingleChoiceItems(option2, -1, new DialogInterface
                            .OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            selectedItem2[0] =option2[item];
                            selecedSubCateg = selectedItem2[0];
                            dialog.dismiss();
                            show2(selecedCateg,selecedSubCateg);

                        }
                    });

                    AlertDialog alert2 = alt_bld2[0].create();
                    alert2.show();

                }
                else if(selecedCateg.equals("nationality"))
                {
                    final String[] option2={"Afghan","American","Albanian","Algerian","Argentine","Australian","Austrian","Bangladeshi","Belgian","Bolivian","Batswana","Brazilian","Bulgarian","Cambodian","Cameroonian","Canadian","Chilean","Chinese","Colombian","CostaRican","Croatian","Cuban","Czech","Danish","Dominican","Ecuadorian","Egyptian","Salvadorian","English","Estonian","Ethiopian","Fijian","Finnish","French","German","Ghanaian","Greek","Guatemalan","Haitian","Honduran","Hungarian","Icelandic","Indian","Indonesian","Iranian","Iraqi","Irish","Italian","Jamaican","Japanese","Jordanian","Kenyan","Kuwaiti","Lao","Latvian","Lebanese","Libyan","Lithuanian","Malaysian","Malian","Maltese","Mexican","Mongolian","Moroccan","Mozambican","Namibian","Nepalese","Dutch","NewZealand","Nicaraguan","Nigerian","Norwegian","Pakistani","Panamanian","Paraguayan","Peruvian","Philippine","Polish","Portuguese","Romanian","Russian","Saudi","Scottish","Senegalese","Serbian","Singaporean","Slovak","SouthAfrican","Korean","Spanish","SriLankan","Sudanese","Swedish","Swiss","Syrian","Taiwanese","Tajikistani","Thai","Tongan","Tunisian","Turkish","Ukrainian","Emirati","British","Uruguayan","Venezuelan","Vietnamese","Welsh","Zambian","Zimbabwean"};

                    final AlertDialog.Builder[] alt_bld2 = {new AlertDialog.Builder(getActivity() ,R.style.MyAlertDialogTheme)};

                    alt_bld2[0].setTitle("Please Select :");
                    alt_bld2[0].setCancelable(true);



                    final String[] selectedOption2 = new String[1];

                    final String[] selectedItem2 = {""};

                    alt_bld2[0].setSingleChoiceItems(option2, -1, new DialogInterface
                            .OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            selectedItem2[0] =option2[item];
                            selecedSubCateg = selectedItem2[0];
                            dialog.dismiss();
                            show2(selecedCateg,selecedSubCateg);

                        }
                    });

                    AlertDialog alert2 = alt_bld2[0].create();
                    alert2.show();

                }
                else if(selecedCateg.equals("age"))

                {
                    final String[] option2={"18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40"};

                    final AlertDialog.Builder[] alt_bld2 = {new AlertDialog.Builder(getActivity() ,R.style.MyAlertDialogTheme)};

                    alt_bld2[0].setTitle("Please Select :");
                    alt_bld2[0].setCancelable(true);



                    final String[] selectedOption2 = new String[1];

                    final String[] selectedItem2 = {""};

                    alt_bld2[0].setSingleChoiceItems(option2, -1, new DialogInterface
                            .OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            selectedItem2[0] =option2[item];
                            selecedSubCateg = selectedItem2[0];
                            dialog.dismiss();
                            show2(selecedCateg,selecedSubCateg);

                        }
                    });

                    AlertDialog alert2 = alt_bld2[0].create();
                    alert2.show();

                }

            }
        });

        AlertDialog alert = alt_bld[0].create();
        alert.show();

    }



    public  void show ()
    {
        recAllAdv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        allAdvs = new ArrayList<>();

        dbrf= FirebaseDatabase.getInstance().getReference().child("Adv");

        dbrf.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                final String phonee = dataSnapshot.child("phone").getValue().toString();
                if(filterRes.equals("  All                                                    "))
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
                    allAdvs.add(adv);
                    RecycleAdapterHome a=new RecycleAdapterHome(getActivity(),allAdvs ,"user");
                    recAllAdv.setAdapter(a);

                }else if(filterRes.equals("  FreeLance                                              "))
                {

                    if(dataSnapshot.child("type").getValue().toString().equals("freeLance"))
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
                        allAdvs.add(adv);
                        RecycleAdapterHome a=new RecycleAdapterHome(getActivity(),allAdvs ,"user");
                        recAllAdv.setAdapter(a);

                    }


                }
                else if(filterRes.equals("  Office                                                 "))
                {
                    if(dataSnapshot.child("type").getValue().toString().equals("office"))
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
                        allAdvs.add(adv);
                        RecycleAdapterHome a=new RecycleAdapterHome(getActivity(),allAdvs ,"user");
                        recAllAdv.setAdapter(a);

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



    public  void show2 (final String selecedCateg , final String selecedSubCateg ) {


        recAllAdv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        allAdvs = new ArrayList<>();

        dbrf= FirebaseDatabase.getInstance().getReference().child("Adv");

        dbrf.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.child(selecedCateg).getValue().toString().equals(selecedSubCateg))
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
                    adv.setAge(dataSnapshot.child("age").getValue().toString());
                    adv.setKey(dataSnapshot.getKey());
                    allAdvs.add(adv);
                    RecycleAdapterHome a=new RecycleAdapterHome(getActivity(),allAdvs ,"user");
                    recAllAdv.setAdapter(a);


                }
                else
                {
                    RecycleAdapterHome a=new RecycleAdapterHome(getActivity(),allAdvs ,"user");
                    recAllAdv.setAdapter(a);
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (allAdvs.size() == 0)
                {
                    Toast.makeText(getActivity(), "No result by your filter", Toast.LENGTH_SHORT).show();
                }

            }
        }, 600);
    }

}
