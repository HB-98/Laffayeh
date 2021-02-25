package com.example.laffayeh.fragment;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileOfficeFragment extends Fragment {

TextView etOfficeName;
Button btnEdit,btnSave;
ImageView imgOfficeImage;
    DatabaseReference dbrf;
    String key;
    Boolean update = false;

    private StorageReference mStorageRef;
    Uri downloadUrl;

    public MyProfileOfficeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_my_profile_office, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        dbrf = FirebaseDatabase.getInstance().getReference().child("User");

        etOfficeName = v.findViewById(R.id.etOfficeName);
        btnEdit = v.findViewById(R.id.btnEdit);
        btnSave = v.findViewById(R.id.btnSave);
        imgOfficeImage = v.findViewById(R.id.imgOfficeImage);

        SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(getActivity());
        etOfficeName.setText(daftar.getString("name" , ""));
        Glide.with(getActivity()).load(daftar.getString("pic" , "")).into(imgOfficeImage);



        imgOfficeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {

                                imgOfficeImage.setImageBitmap(r.getBitmap());


                                Bitmap bitmap = ((BitmapDrawable) imgOfficeImage.getDrawable()).getBitmap();
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
                                                SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                                SharedPreferences.Editor pen = daftar.edit();
                                                pen.putString("pic",downloadUrl.toString());
                                                pen.apply();

                                            }

                                        });


                                    }
                                });


                            }
                        }).show(getFragmentManager());





            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setVisibility(View.VISIBLE);
                etOfficeName.setEnabled(true);
                btnEdit.setVisibility(View.INVISIBLE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setVisibility(View.INVISIBLE);
                etOfficeName.setEnabled(false);
                btnEdit.setVisibility(View.VISIBLE);
                final SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor pen = daftar.edit();
                pen.putString("name",etOfficeName.getText().toString());
                pen.apply();

                dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot datas: dataSnapshot.getChildren()){

                            String myApproveValueID=datas.child("phone").getValue().toString();
                            if (myApproveValueID.equals(daftar.getString("phone" , "") ))
                            {
                                update = true;
                                key = datas.getKey();

                            }
                        }

                        if (update)
                        {
                            dbrf.child(key).child("name").setValue(daftar.getString("name" , ""));
                            dbrf.child(key).child("pic").setValue(daftar.getString("pic" , ""));
                            Toast.makeText(getActivity(), "Update Done", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        return v;
    }

}
