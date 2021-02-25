package com.example.laffayeh.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.laffayeh.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference dbrf;
     ArrayList<String> allLocation = new ArrayList<>();
     ArrayList<String> name = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        dbrf = FirebaseDatabase.getInstance().getReference().child("User");
        dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    if(datas.child("type").equals("office")) {
                        allLocation.add(datas.child("officeLocation").getValue().toString());
                        name.add(datas.child("name").getValue().toString());
                        Toast.makeText(MapsActivity.this, "" + datas.child("officeLocation").getValue().toString(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i =  0 ; i<allLocation.size() ; i++)

                {
                    String loc =allLocation.get(i);
                    int pos = loc.indexOf(",");
                    double lat = Double.parseDouble(loc.substring(0,pos-1));
                    double longe = Double.parseDouble(loc.substring(pos+1,allLocation.size()));
                    LatLng office = new LatLng(lat, longe);
                    mMap.addMarker(new MarkerOptions().position(office).title(name.get(i)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(office));

                }

            }
        }, 1500);
        // Add a marker in Sydney and move the camera
    }
}
