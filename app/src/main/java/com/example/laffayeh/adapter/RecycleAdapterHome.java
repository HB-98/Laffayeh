package com.example.laffayeh.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laffayeh.R;
import com.example.laffayeh.activity.AdvEditActivity;
import com.example.laffayeh.activity.AdvShowActivity;
import com.example.laffayeh.model.AddToFav;
import com.example.laffayeh.model.Adv;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RecycleAdapterHome extends RecyclerView.Adapter<RecycleAdapterHome.viewitem> {



    ArrayList<Adv> advs;
    Context context;
    DatabaseReference dbrf,dbrf1,dbrf2;
    String types;
    String type;
    String loc;
    public RecycleAdapterHome(Context c, ArrayList<Adv> adv , String type)
    {
        advs=adv;
        context=c;
        types = type;
    }

    class  viewitem extends RecyclerView.ViewHolder
    {

        //Declare
        TextView tvAdvName , tvAdvLang ,tvAdvExp ;
        ImageView imgAdv ,imgAddToFav ,imgCall ,imgSeeMore ,imgDelete,imgLocation;

        //initialize
        public viewitem(View itemView) {
            super(itemView);
            tvAdvName=  itemView.findViewById(R.id.tvAdvName);
            tvAdvLang=  itemView.findViewById(R.id.tvAdvLang);
            tvAdvExp=  itemView.findViewById(R.id.tvAdvExp);
            imgAdv=  itemView.findViewById(R.id.imgAdv);
            imgAddToFav=  itemView.findViewById(R.id.imgAddToFav);
            imgCall=  itemView.findViewById(R.id.imgCall);
            imgSeeMore=  itemView.findViewById(R.id.imgSeeMore);
            imgLocation=  itemView.findViewById(R.id.imgLocation);
            imgDelete=  itemView.findViewById(R.id.imgDelete);

            if (types.equals("user"))
            {
                imgDelete.setVisibility(View.INVISIBLE);
            }
            else
            {
                imgCall.setVisibility(View.INVISIBLE);
                imgAddToFav.setVisibility(View.INVISIBLE);
                imgLocation.setVisibility(View.INVISIBLE);
            }
        }
    }



    @Override
    public viewitem onCreateViewHolder(final ViewGroup parent, int viewType) {




        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custome_adv, parent, false);



        return new viewitem(itemView);
    }


    @Override
    public void onBindViewHolder(final viewitem holder, final int position) {
        dbrf= FirebaseDatabase.getInstance().getReference().child("fav");
        dbrf1= FirebaseDatabase.getInstance().getReference().child("Adv");
        dbrf2= FirebaseDatabase.getInstance().getReference().child("User");

        final ArrayList<String> allFavAdvsKey = new ArrayList<>();

        dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(context);

                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    if(datas.child("phone").getValue().toString().equals(daftar.getString("phone" , ""))) {
                        allFavAdvsKey.add(datas.child("key").getValue().toString());
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        holder.imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbrf2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            if(datas.child("phone").getValue().toString().equals(advs.get(position).getPhone())) {

                                type =datas.child("type").getValue().toString();
                                loc = datas.child("officeLocation").getValue().toString();


                                int pos = loc.indexOf(",");
                                double lat = Double.parseDouble(loc.substring(0,pos-1));
                                double longe = Double.parseDouble(loc.substring(pos+1));

                                Uri go = Uri.parse("google.navigation:q=" + lat + ","+longe);
                                Intent i = new Intent(Intent.ACTION_VIEW,go);
                                i.setPackage("com.google.android.apps.maps");
                                context.startActivity(i);

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }
        });
        final SharedPreferences daftar = PreferenceManager.getDefaultSharedPreferences(context);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i<allFavAdvsKey.size();i++)
                {
                    if(advs.get(position).getKey().equals(allFavAdvsKey.get(i)))
                    {
                        holder.imgAddToFav.setImageResource(R.drawable.ic_fav);
                    }
                }

            }
        }, 300);

        holder.tvAdvName.setText(advs.get(position).getName());
        holder.tvAdvLang.setText(advs.get(position).getLanguage());
        holder.tvAdvExp.setText(advs.get(position).getExperience());
        holder.imgAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imgAddToFav.setImageResource(R.drawable.ic_fav);
                AddToFav addToFav = new AddToFav();
                addToFav.setKey(advs.get(position).getKey());
                addToFav.setPhone(daftar.getString("phone" , ""));
                dbrf.push().setValue(addToFav);
                Toast.makeText(context, "Added To Fav", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (types.equals("user"))
                {
                    Intent i = new Intent(context, AdvShowActivity.class);
                    i.putExtra("key",advs.get(position).getKey());
                    context.startActivity(i);

                }
                else
                {
                    Intent i = new Intent(context, AdvEditActivity.class);
                    i.putExtra("key",advs.get(position).getKey());
                    context.startActivity(i);

                }
            }
        });
        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel","0" + advs.get(position).getPhone() , null)));

            }
        });
        Glide.with(context).load(advs.get(position).getPic()).into(holder.imgAdv);


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbrf1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            if(datas.getKey().equals(advs.get(position).getKey())) {
                                dbrf1.child(advs.get(position).getKey()).removeValue();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }




    @Override
    public int getItemCount() {
        return advs.size();
    }


}

