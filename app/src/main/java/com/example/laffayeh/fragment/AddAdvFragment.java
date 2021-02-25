package com.example.laffayeh.fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laffayeh.model.Adv;
import com.example.laffayeh.activity.OfficeHomeActivity;
import com.example.laffayeh.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddAdvFragment extends Fragment {
    Spinner spLanguage ,spNationality,spReligion,spWorkHours,spExperience;
    Button btnAdd;
    CheckBox checkBox,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7;
    ArrayList<String> workDay = new ArrayList<>();
    DatabaseReference dbrf,dbrf1;
    Integer used = 0;
    ImageView imgAdv;
    EditText etAdvName , etAdvAge;
    StorageReference mStorageRef;
    TextView textView11,pic;
    Uri downloadUrl;

    public AddAdvFragment() {
        // Required empty public constructor
    }
    public String selected_Language = "";
    public String selected_Nationality= "";
    public  String selected_Religion= "";
    public   String selectedWorkHours= "";
    public  String selectedExperience= "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_add_adv, container, false);
        spLanguage = v.findViewById(R.id.spLanguage);
        spNationality = v.findViewById(R.id.spNationality);
        spReligion = v.findViewById(R.id.spReligion);
        spWorkHours = v.findViewById(R.id.spWorkHours);
        spExperience = v.findViewById(R.id.spExperience);
        btnAdd = v.findViewById(R.id.btnAdd);
        textView11 = v.findViewById(R.id.textView11);
        pic = v.findViewById(R.id.pic);

        imgAdv = v.findViewById(R.id.imgAdv);
        etAdvAge = v.findViewById(R.id.etAdvAge);
        etAdvName = v.findViewById(R.id.etAdvName);

        checkBox = v.findViewById(R.id.checkBox);
        checkBox2 = v.findViewById(R.id.checkBox2);
        checkBox3 = v.findViewById(R.id.checkBox3);
        checkBox4 = v.findViewById(R.id.checkBox4);
        checkBox5 = v.findViewById(R.id.checkBox5);
        checkBox6 = v.findViewById(R.id.checkBox6);
        checkBox7 = v.findViewById(R.id.checkBox7);


        dbrf= FirebaseDatabase.getInstance().getReference().child("Adv");
        dbrf1= FirebaseDatabase.getInstance().getReference().child("User");

        mStorageRef = FirebaseStorage.getInstance().getReference();



        imgAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {

                                imgAdv.setImageBitmap(r.getBitmap());


                                Bitmap bitmap = ((BitmapDrawable) imgAdv.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();
                                SimpleDateFormat dateFormat;
                                String date;
                                Calendar calendar;
                                calendar = Calendar.getInstance();
                                dateFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
                                date = dateFormat.format(calendar.getTime());
                                final StorageReference mountainsRef = mStorageRef.child(date);
                                final StorageReference mountainImagesRef = mStorageRef.child("images/"+date);
                                mountainsRef.getName().equals(mountainImagesRef.getName());    // true
                                mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

                                UploadTask uploadTask = mountainsRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                downloadUrl = uri;

                                            }

                                        });


                                    }
                                });


                            }
                        }).show(getFragmentManager());





            }
        });


        ArrayAdapter<CharSequence> adaptersLanguage = ArrayAdapter.createFromResource(getActivity(),
                R.array.Language, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adaptersNationality = ArrayAdapter.createFromResource(getActivity(),
                R.array.Nationality, android.R.layout.simple_spinner_item);


        ArrayAdapter<CharSequence> adaptersReligion = ArrayAdapter.createFromResource(getActivity(),
                R.array.Religion, android.R.layout.simple_spinner_item);


        ArrayAdapter<CharSequence> adaptersWorkHours = ArrayAdapter.createFromResource(getActivity(),
                R.array.Work_hours, android.R.layout.simple_spinner_item);


        ArrayAdapter<CharSequence> adaptersExperience = ArrayAdapter.createFromResource(getActivity(),
                R.array.exp, android.R.layout.simple_spinner_item);

        spLanguage.setAdapter(adaptersLanguage);
        spNationality.setAdapter(adaptersNationality);
        spReligion.setAdapter(adaptersReligion);
        spWorkHours.setAdapter(adaptersWorkHours);
        spExperience.setAdapter(adaptersExperience);
        final Adv my_add = new Adv();
        final SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(getActivity());

        my_add.setPhone(daftar.getString("phone" , ""));
        my_add.setType(daftar.getString("type" , ""));

        spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    
                my_add.setLanguage(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spExperience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                my_add.setExperience(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                my_add.setNationality(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                my_add.setReligion(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spWorkHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                my_add.setWorkHours(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String type = daftar.getString("type" , "");
        if(type.equals("freeLance") ) {
            imgAdv.setVisibility(View.GONE);
            etAdvName.setVisibility(View.GONE);
            textView11.setVisibility(View.GONE);
            pic.setVisibility(View.GONE);
        }

            btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = daftar.getString("type" , "");
                if(type.equals("freeLance") )
                {
                    dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String myApproveValueID = daftar.getString("phone" , "");
                            for(DataSnapshot datas: dataSnapshot.getChildren()){

                                if (myApproveValueID.equals(datas.child("phone").getValue().toString() ))
                                {
                                    used = 1;
                                }
                            }




                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if (used == 1)
                                    {
                                        Toast.makeText(getActivity(), "You Shared an advertisement last time", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        if(checkBox.isChecked())
                                        {
                                            workDay.add("Monday");
                                        }

                                        if(checkBox2.isChecked())
                                        {
                                            workDay.add("Tuesday");
                                        }

                                        if(checkBox3.isChecked())
                                        {
                                            workDay.add("Sunday");
                                        }

                                        if(checkBox4.isChecked())
                                        {
                                            workDay.add("Wednesday");
                                        }

                                        if(checkBox5.isChecked())
                                        {
                                            workDay.add("Thursday");
                                        }

                                        if(checkBox6.isChecked())
                                        {
                                            workDay.add("Friday");
                                        }

                                        if(checkBox7.isChecked())
                                        {
                                            workDay.add("Saturday");
                                        }
                                        my_add.setWorksDay(workDay);
                                        my_add.setName(daftar.getString("name" , ""));
                                        my_add.setAge(etAdvAge.getText().toString());
                                        my_add.setPic(daftar.getString("pic" , ""));

                                        dbrf.push().setValue(my_add);

                                        getActivity().finish();
                                        Intent i = new Intent(getActivity(),OfficeHomeActivity.class);
                                        startActivity(i);


                                    }


                                }
                            }, 1500);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    if(checkBox.isChecked())
                    {
                        workDay.add("Monday");
                    }

                    if(checkBox2.isChecked())
                    {
                        workDay.add("Tuesday");
                    }

                    if(checkBox3.isChecked())
                    {
                        workDay.add("Sunday");
                    }

                    if(checkBox4.isChecked())
                    {
                        workDay.add("Wednesday");
                    }

                    if(checkBox5.isChecked())
                    {
                        workDay.add("Thursday");
                    }

                    if(checkBox6.isChecked())
                    {
                        workDay.add("Friday");
                    }

                    if(checkBox7.isChecked())
                    {
                        workDay.add("Saturday");
                    }
                    my_add.setWorksDay(workDay);
                    SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(getActivity());

                    my_add.setName(etAdvName.getText().toString() + " , " + daftar.getString("name" , ""));
                    my_add.setAge(etAdvAge.getText().toString());
                    my_add.setPic(downloadUrl.toString());

                    dbrf.push().setValue(my_add);

                    getActivity().finish();
                   Intent i = new Intent(getActivity(),OfficeHomeActivity.class);
                    startActivity(i);


                }

                Toast.makeText(getActivity(), "Your advertisement was shared", Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }



}
