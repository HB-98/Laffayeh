package com.example.laffayeh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.laffayeh.R;
import com.example.laffayeh.model.Adv;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdvEditActivity extends AppCompatActivity {
    Spinner spLanguage ,spNationality,spReligion,spWorkHours,spExperience;
    Button btnAdd;
    CheckBox checkBox,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7;
    ArrayList<String> workDay = new ArrayList<>();
    DatabaseReference dbrf,dbrf1;
    EditText etAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_edit);

        spLanguage = findViewById(R.id.spLanguage);
        spNationality = findViewById(R.id.spNationality);
        spReligion = findViewById(R.id.spReligion);
        spWorkHours = findViewById(R.id.spWorkHours);
        spExperience =findViewById(R.id.spExperience);
        btnAdd = findViewById(R.id.btnAdd);
        etAge = findViewById(R.id.etAge);

        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);

        Intent intent = getIntent();
        final String key = intent.getStringExtra("key");



        dbrf= FirebaseDatabase.getInstance().getReference().child("Adv");
        dbrf1= FirebaseDatabase.getInstance().getReference().child("User");


        ArrayAdapter<CharSequence> adaptersLanguage = ArrayAdapter.createFromResource(this,
                R.array.Language, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adaptersNationality = ArrayAdapter.createFromResource(this,
                R.array.Nationality, android.R.layout.simple_spinner_item);


        ArrayAdapter<CharSequence> adaptersReligion = ArrayAdapter.createFromResource(this,
                R.array.Religion, android.R.layout.simple_spinner_item);


        ArrayAdapter<CharSequence> adaptersWorkHours = ArrayAdapter.createFromResource(this,
                R.array.Work_hours, android.R.layout.simple_spinner_item);


        ArrayAdapter<CharSequence> adaptersExperience = ArrayAdapter.createFromResource(this,
                R.array.exp, android.R.layout.simple_spinner_item);

        spLanguage.setAdapter(adaptersLanguage);
        spNationality.setAdapter(adaptersNationality);
        spReligion.setAdapter(adaptersReligion);
        spWorkHours.setAdapter(adaptersWorkHours);
        spExperience.setAdapter(adaptersExperience);



        final Adv adv = new Adv();
        final Adv my_add = new Adv();

        dbrf.addValueEventListener(new ValueEventListener() {
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
                for (int i = 0 ; i <adv.getWorksDay().size() ; i++)
                {
                    if (adv.getWorksDay().get(i).equals("Sunday"))
                    {
                        checkBox3.setChecked(true);
                    }

                   else if(adv.getWorksDay().get(i).equals("Monday"))
                    {
                        checkBox.setChecked(true);
                    }

                    else if(adv.getWorksDay().get(i).equals("Tuesday"))
                    {
                        checkBox2.setChecked(true);
                    }


                    else  if(adv.getWorksDay().get(i).equals("Wednesday"))
                    {
                        checkBox4.setChecked(true);
                    }

                    else if(adv.getWorksDay().get(i).equals("Thursday"))
                    {
                        checkBox5.setChecked(true);
                    }

                    else  if(adv.getWorksDay().get(i).equals("Friday"))
                    {
                        checkBox6.setChecked(true);
                    }

                    else if(adv.getWorksDay().get(i).equals("Saturday"))
                    {
                        checkBox7.setChecked(true);
                    }
                }

                etAge.setText(adv.getAge());


                final String[] exp={"taking care of children","taking care of old people","housekeeping","house cleaning"};
                final String[] lan={"Arabic","English"};
                final String[] re={"Islam","Christianity","secularism","Hinduism","Chinese folk religion","Buddhism","Traditional African Religions","Sikhism","spirituality","Judaism","Bahaai","jain","Shinto","Zoroastrianism","paganism","Rastafari"};
                final String[] workH={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
                final String[] nat={"Afghan","American","Albanian","Algerian","Argentine","Australian","Austrian","Bangladeshi","Belgian","Bolivian","Batswana","Brazilian","Bulgarian","Cambodian","Cameroonian","Canadian","Chilean","Chinese","Colombian","CostaRican","Croatian","Cuban","Czech","Danish","Dominican","Ecuadorian","Egyptian","Salvadorian","English","Estonian","Ethiopian","Fijian","Finnish","French","German","Ghanaian","Greek","Guatemalan","Haitian","Honduran","Hungarian","Icelandic","Indian","Indonesian","Iranian","Iraqi","Irish","Italian","Jamaican","Japanese","Jordanian","Kenyan","Kuwaiti","Lao","Latvian","Lebanese","Libyan","Lithuanian","Malaysian","Malian","Maltese","Mexican","Mongolian","Moroccan","Mozambican","Namibian","Nepalese","Dutch","NewZealand","Nicaraguan","Nigerian","Norwegian","Pakistani","Panamanian","Paraguayan","Peruvian","Philippine","Polish","Portuguese","Romanian","Russian","Saudi","Scottish","Senegalese","Serbian","Singaporean","Slovak","SouthAfrican","Korean","Spanish","SriLankan","Sudanese","Swedish","Swiss","Syrian","Taiwanese","Tajikistani","Thai","Tongan","Tunisian","Turkish","Ukrainian","Emirati","British","Uruguayan","Venezuelan","Vietnamese","Welsh","Zambian","Zimbabwean"};

                for(int i = 0 ; i < exp.length ; i++)
                {
                    if (exp[i].equals(adv.getExperience()))
                    {
                         spExperience.setSelection(i);
                    }
                }

                for(int i = 0 ; i < lan.length ; i++)
                {
                    if (lan[i].equals(adv.getLanguage()))
                    {
                         spLanguage.setSelection(i);
                    }
                }


                for(int i = 0 ; i < nat.length ; i++)
                {
                    if (nat[i].equals(adv.getNationality()))
                    {
                        spNationality.setSelection(i);
                    }
                }


                for(int i = 0 ; i < workH.length ; i++)
                {
                    if (workH[i].equals(adv.getWorkHours()))
                    {
                        spWorkHours.setSelection(i);
                    }
                }

                for(int i = 0 ; i < re.length ; i++)
                {
                    if (re[i].equals(adv.getReligion()))
                    {
                        spReligion.setSelection(i);
                    }




                }
                my_add.setPhone(adv.getPhone());
                my_add.setName(adv.getName());
                my_add.setPic(adv.getPic());

            }
        }, 500);
        final SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(this);




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


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String type = daftar.getString("type" , "");
                final Boolean[] used = {false};

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {



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
                            my_add.setType(type);
                            my_add.setWorksDay(workDay);
                            my_add.setAge(etAge.getText().toString());
                            dbrf.child(key).setValue(my_add);
                            used[0] = false;

                            Toast.makeText(AdvEditActivity.this, "Your advertisement was Edited", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(AdvEditActivity.this, OfficeHomeActivity.class);
                            startActivity(i);
                    }
                }, 1000);}
        });


    }
}
