package com.example.laffayeh.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laffayeh.general.LocaleHelper;
import com.example.laffayeh.R;
import com.example.laffayeh.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
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

import mumayank.com.airlocationlibrary.AirLocation;

public class SignUpOfficeActivity extends AppCompatActivity {
    Uri downloadUrl;
    Button submit;
    EditText uname ;
    DatabaseReference dbrf;
    ImageView imageView3;
    private AirLocation airLocation;
    private StorageReference mStorageRef;
    String officeLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_office);
        submit = findViewById(R.id.submit);
        uname = findViewById(R.id.oname);
        imageView3 = findViewById(R.id.imageView3);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        dbrf = FirebaseDatabase.getInstance().getReference().child("User");

        airLocation = new AirLocation(this, true, true, new AirLocation.Callbacks() {
            @Override
            public void onSuccess(@NotNull Location location) {
                // do something
                officeLocation = location.getLatitude() + " , " + location.getLongitude();
            }

            @Override
            public void onFailed(@NotNull AirLocation.LocationFailedEnum locationFailedEnum) {
                // do something
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {

                                imageView3.setImageBitmap(r.getBitmap());


                                Bitmap bitmap = ((BitmapDrawable) imageView3.getDrawable()).getBitmap();
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
                        }).show(getSupportFragmentManager());





            }
        });

    }







    public void Submit(View view) {


        User user = new User();
        user.setName(uname.getText().toString());
        SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(SignUpOfficeActivity.this);
        int a = daftar.getInt("phoneToVali",0);
        user.setPhone(a);
        user.setType("office");
        user.setPic(downloadUrl.toString());
        user.setOfficeLocation(officeLocation);
        dbrf.push().setValue(user);
        Toast.makeText(SignUpOfficeActivity.this,"SignUp was successfully",Toast.LENGTH_LONG).show();
        daftar = PreferenceManager.getDefaultSharedPreferences(SignUpOfficeActivity.this);
        SharedPreferences.Editor pen = daftar.edit();
        pen.putString("pic",downloadUrl.toString());
        pen.putString("phone", String.valueOf(a));
        pen.putString("name",uname.getText().toString());
        pen.putString("type","office");

        pen.apply();
        Toast.makeText(this, "You are Signed up Login Now", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this,OfficeHomeActivity.class);
        startActivity(i);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        airLocation.onActivityResult(requestCode, resultCode, data);
    }

    // override and call airLocation object's method by the same name
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
}
